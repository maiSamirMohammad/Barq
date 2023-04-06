package iti.mobile.barq

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import iti.mobile.barq.alert.view.AlertFragment
import iti.mobile.barq.databinding.ActivityMainBinding
import iti.mobile.barq.favorite.view.FavoriteFragment
import iti.mobile.barq.home.view.HomeFragment
import iti.mobile.barq.settings.view.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.home -> replaceFragment(HomeFragment())
                R.id.favorite -> replaceFragment(FavoriteFragment())
                R.id.alert -> replaceFragment(AlertFragment())
                R.id.settings -> replaceFragment(SettingsFragment())

                else ->{



                }

            }

            true

        }


    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()


    }
}