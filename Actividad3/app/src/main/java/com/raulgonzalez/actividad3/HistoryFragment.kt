package com.raulgonzalez.actividad3
// (c) Raúl Enrique González Bondarchuk
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.raulgonzalez.actividad3.databinding.FragmentHistoryBinding
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class HistoryFragment : Fragment() {
    private lateinit var myAdapter: HistoricoAdapter
    private lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        // Creamos nuestro binding
        binding = FragmentHistoryBinding.inflate(layoutInflater)
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        setUpRecyclerView()

        return binding.root
    }
    override fun onResume() {
        super.onResume()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        // Esta opción a TRUE significa que el RV tendrá
        // hijos del mismo tamaño, optimiza su creación.
        binding.rvAlumnos.setHasFixedSize(true)
        // Se indica el contexto para RV en forma de lista.
        binding.rvAlumnos.layoutManager = LinearLayoutManager(requireContext())
        // Se genera el adapter.
        myAdapter = HistoricoAdapter(leerFichero(), requireContext())
        // Se asigna el adapter al RV.
        binding.rvAlumnos.adapter = myAdapter
    }

    private fun leerFichero(): MutableList<AlumnoHistory> {
        // Container donde nos pegamos nuestro maquete de Layout
        val historicoList = mutableListOf<AlumnoHistory>()

        // Se comprueba si existe el fichero.
        if (requireActivity().fileList().contains(getString(R.string.filename))) { // requireActivity() Para llamar a Main
            try {
                val entrada = InputStreamReader(
                    requireActivity().openFileInput(getString(R.string.filename))) // requireActivity() Para llamar a Main
                val br = BufferedReader(entrada)
                // Leemos la primera línea
                var linea = br.readLine()
                while (!linea.isNullOrEmpty()) {
                    // Obtenemos los datos separandolo por el ;
                    val datos: List<String> = linea.split(";")

                    val dia = datos[0]
                    val mes = datos[1]
                    val anyo = datos[2]
                    val nombreAlumno = datos[3]
                    val tipoCiclo = datos[4]
                    val ciclo = datos[5]

                    val mesString = getMonthName(mes.toInt())

                    // Creamos un object
                    val myHistoricoObject = AlumnoHistory(dia,mesString,anyo,nombreAlumno,tipoCiclo,ciclo)
                    historicoList.add(myHistoricoObject) // Añadimos object a la lista

                    // Leemos la siguiente línea del fichero
                    linea = br.readLine()
                }
                br.close()
                entrada.close()
            } catch (e: IOException) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(context,
                R.string.no_existe_fichero,
                Toast.LENGTH_LONG).show()
        }
        return historicoList
    }

    private fun getMonthName(monthNumber: Int): String {
        return when (monthNumber) {
            1 -> "Enero"
            2 -> "Febrero"
            3 -> "Marzo"
            4 -> "Abril"
            5 -> "Mayo"
            6 -> "Junio"
            7 -> "Julio"
            8 -> "Agosto"
            9 -> "Septiembre"
            10 -> "Octubre"
            11 -> "Noviembre"
            12 -> "Diciembre"
            else -> "El número de mes incorrecto"
        }
    }
}