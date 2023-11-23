package com.raulgonzalez.actividad3
// (c) Raúl Enrique González Bondarchuk
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.raulgonzalez.actividad3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Recogemos el componente ViewPager2
        val viewPager2 = binding.viewPager2
        // Se crea el adapter.
        val adapter = ViewPager2Adapter(supportFragmentManager, lifecycle)
        // Se añaden los fragments y los títulos de pestañas.
        adapter.addFragment(MainFragment(), "Inicio")
        adapter.addFragment(HistoryFragment(), "Historico")

        // Se asocia el adpater al viewPager2
        viewPager2.adapter = adapter
        // Carga de las pestañas en el TabLayout
        TabLayoutMediator(binding.tabLayout, viewPager2){tab, position ->
            tab.text = adapter.getPageTitle(position)
        }.attach()
    }


}