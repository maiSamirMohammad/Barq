package iti.mobile.barq.alert.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import iti.mobile.barq.alert.viewmodel.AlertViewModel
import iti.mobile.barq.alert.viewmodel.AlertViewModelFactory
import iti.mobile.barq.databinding.FragmentAlertBinding
import iti.mobile.barq.db.LocalSource
import iti.mobile.barq.model.Alert
import iti.mobile.barq.model.Repository
import iti.mobile.barq.network.RemoteSource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class AlertFragment : Fragment(),OnItemCLickListener {

    private lateinit var binding:FragmentAlertBinding
    private lateinit var adapter: AlertAdapter
    private lateinit var alertViewModel: AlertViewModel
    private lateinit var alertViewModelFactory: AlertViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentAlertBinding.inflate(inflater,container,false)
        //getting viewModel ready
        alertViewModelFactory= AlertViewModelFactory(
            Repository.getInstance(
                LocalSource(requireContext()),
                RemoteSource.getInstance())
        )
        alertViewModel= ViewModelProvider(this,alertViewModelFactory).get(AlertViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter= AlertAdapter(requireContext(), emptyList(),this)
        binding.recyclerview.adapter=adapter
        alertViewModel.getAlerts()
        lifecycleScope.launch {
            alertViewModel.storedAlert.collect{
                adapter.setList(it)
                adapter.notifyDataSetChanged()
            }
        }

        binding.addAlertBtn.setOnClickListener {
           // AlertDialog.show(requireActivity().supportFragmentManager,"Alertdialog")
        }
    }

    override fun onClick(alert: Alert) {
        alertViewModel.deleteAlert(alert)
    }
}