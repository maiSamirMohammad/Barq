package iti.mobile.barq.home.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import iti.mobile.barq.R
import iti.mobile.barq.databinding.FragmentHomeBinding
import iti.mobile.barq.db.LocalSource
import iti.mobile.barq.home.viewmodel.HomeViewModel
import iti.mobile.barq.home.viewmodel.HomeViewModelFactory
import iti.mobile.barq.model.Constants
import iti.mobile.barq.model.Repository
import iti.mobile.barq.network.APIState
import iti.mobile.barq.network.RemoteSource
import iti.mobile.barq.utilities.ConnectivityObserver
import iti.mobile.barq.utilities.NetworkConnectivityObserver
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

const val PERMISSION_ID=77
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeViewModelFactory: HomeViewModelFactory
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var connectivityObserver: ConnectivityObserver
    private val locationCallback: LocationCallback =object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            val currentLocation: Location? =p0.lastLocation
            Log.i("TAG", "lat  ${currentLocation?.latitude.toString()} .. long  ${currentLocation?.longitude.toString()}")
            homeViewModel.getCurrentWeather(
                latitude=currentLocation?.latitude.toString(),
                longitude = currentLocation?.longitude.toString(),
                language = Constants.LANGUAGE_ENGLISH,
                unitOfMeasurement = Constants.UNITS_DEFAULT
                )
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater,container,false)

        //getting viewModel ready
        homeViewModelFactory= HomeViewModelFactory(
            Repository.getInstance(
                LocalSource(requireContext()),
                RemoteSource.getInstance())
        )
        homeViewModel= ViewModelProvider(this,homeViewModelFactory).get(HomeViewModel::class.java)
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(requireContext())



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLastLocation()
        connectivityObserver = NetworkConnectivityObserver(requireActivity().applicationContext)
        connectivityObserver.observe().onEach {internetStatus ->
            when(internetStatus){
                ConnectivityObserver.Status.Available ->{
                    Log.i("TAG", "the internet status is:$internetStatus ")
                    lifecycleScope.launch {
                        homeViewModel.currentWeatherForecast.collectLatest {apiResult ->
                            when(apiResult)  {
                                is APIState.Loading ->{
                                    binding.progressBar.visibility= View.VISIBLE

                                }
                                is APIState.Success ->{
                                    binding.progressBar.visibility= View.GONE
                                    //get user address
                                    val geoCoder= Geocoder(requireContext())
                                    val currentArea= geoCoder.getFromLocation(apiResult.data.lat,apiResult.data.lon,1)
                                    apiResult.data.countryName=
                                        (currentArea?.get(0)?.countryName +", "+ currentArea?.get(0)?.adminArea)
                                    binding.region.text=apiResult.data.countryName


                                   val weatherIconUrl= resources.getString(
                                        R.string.weather_icon_url,
                                        apiResult.data.current.weather[0].icon
                                    )



                                    val imgUrl = context?.resources?.getString(R.string.weather_icon_url,
                                        apiResult.data.current.weather[0].icon?.toString())
                                    imgUrl.let {
                                        binding.iconWeather.load(imgUrl) {
                                            placeholder(R.drawable.loading_animation)
                                            error(R.drawable.ic_broken_image)
                                        }
                                    }
                                    binding.tvTemperature.text = resources.getString(
                                        R.string.temperature_kelvin,
                                        apiResult.data.current.temp.toString()
                                    )

                                    binding.tvDescription?.text =
                                        apiResult.data.current.weather[0].description ?: resources.getString(
                                            R.string.feels_like,
                                            apiResult.data.current.feels_like.toString()
                                        )



                                    val dateAndTime: Long = apiResult.data.current.dt.toString().toLong()
                                    val simpleDateAndTimeFormat = SimpleDateFormat("dd MMM yyyy HH:mm a")
                                    val convertedDateAndTime=simpleDateAndTimeFormat.format(dateAndTime*1000)
                                    binding.tvDateAndTime.text =convertedDateAndTime
                                    binding.rvHourlyForecast.adapter = HourlyAdapter(apiResult.data.hourly, requireContext())
                                    binding.rvDailyForecast.adapter = DailyAdapter(apiResult.data.daily, requireContext())
                                    binding.rvDailyForecast.hasFixedSize()

                                    binding.humidityValue.text =  apiResult.data.current.humidity.toString() + "%"
                                    binding.cloudsValue.text =  apiResult.data.current.clouds.toString() + "%"

                                    binding.windValue.text = apiResult.data.current.wind_speed.toString() + " metre/sec"
                                    binding.pressureValue.text = apiResult.data.current.pressure.toString() + " hPa"




                                }
                                is APIState.Failure ->{
                                    binding.progressBar.visibility= View.GONE
                                    Log.e("TAG", "error==========:${apiResult.msg.message} ")
                                    Snackbar.make(activity?.window?.decorView!!.rootView,
                                        "Try another time as ${apiResult.msg.message}",
                                        Snackbar.LENGTH_LONG)
                                        .setBackgroundTint(resources.getColor(android.R.color.holo_orange_light))
                                        .show()
                                }
                            }

                        }
                    }

                }
                else->{
                    Log.i("TAG", "the internet status is:$internetStatus ")


                }
            }

        }.launchIn(lifecycleScope)
    }



    private fun checkPermissions():Boolean{
        return ActivityCompat.checkSelfPermission(requireContext(),
            // Manifest.permission.ACCESS_COARSE_LOCATION
            Manifest.permission.ACCESS_COARSE_LOCATION
        )== PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )== PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions(){
        ActivityCompat.requestPermissions(requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            , PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun isLocationEnabled():Boolean{
        val locationManager: LocationManager =
            requireActivity().baseContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation(){
        if (checkPermissions()){
            if (isLocationEnabled()){
                requestNewLocationData()
            }else{
                Toast.makeText(requireContext(),"Please,Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }else{
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData(){

        val locationRequest= LocationRequest()
//        locationRequest.setPriority()
        locationRequest.priority= LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval=0

        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(requireContext())
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper())
    }
}