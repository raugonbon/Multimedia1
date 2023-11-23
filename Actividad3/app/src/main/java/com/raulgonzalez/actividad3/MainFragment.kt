package com.raulgonzalez.actividad3
// (c) Raúl Enrique González Bondarchuk
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.raulgonzalez.actividad3.databinding.FragmentMainBinding
import java.io.IOException
import java.io.OutputStreamWriter

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    // Guardamos las variables para pasarles al nuestro SecondActivity
    companion object {
        // Para pasar a la primer actividad
        const val EXTRA_NOMBRE = "myNombre"
        // Para obtener datos de la segunda actividad
        const val EXTRA_DIA = "myDia"
        const val EXTRA_MES = "myMes"
        const val EXTRA_ANYO = "myAnyo"
        const val EXTRA_TIPOCICLO = "myTipoCiclo"
        const val EXTRA_CICLO = "myCiclo"
    }
    // Creamos un objeto para manipular con los datos de alumnos
    private var alumno = Alumno("", "", "", "", "", "")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        Log.d("Inicio", "onCreateView")
        // CREAMOS NUESTRO BINDING EN MainFragment * * * * * * * * * * * * * * * * * * * *
        //return inflater.inflate(R.layout.fragment_main, container, false)
        binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root

        // AQUÍ nos añadimos la lógica de nuestro Fragment -- -- -- --
        // Leer Datos Second Activity button
        binding.btnLeerDatos.setOnClickListener {
            ocultarTeclado(it)
            val nombreAlumno = binding.edNombre.text.toString() // Guardamos el nombre del alumno
            // Comprobamos que el nombre del alumno no está vacío
            if(nombreAlumno.isNotEmpty()){

                lanzarSegundaActividad()

                binding.txtResult.text = ""
                binding.btnGuardarHistorico.isEnabled = false
            }
            // Indicamos que el campo nombre no tiene que estar vacio
            else {
                Toast.makeText(context, "El campo \"Nombre\" no puede ser vácio", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnObtenerDatos.setOnClickListener {
            ocultarTeclado(it)
            val edad = alumno.calculateAge()
            val grupoAula = alumno.groupAssign()

            val message = "Edad: $edad\n$grupoAula"

            binding.txtResult.text = message
            binding.btnObtenerDatos.isEnabled = false
            binding.btnGuardarHistorico.isEnabled = true
        }

        binding.btnGuardarHistorico.setOnClickListener {
            ocultarTeclado(it)
            // Abrimos un AlertDialog para guardar datos o no
            showSaveConfirmationDialog()
        }



        // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
        return view
    }

    // Metodo para mostrar AlertDialog
    private fun showSaveConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Guardar alumno")
        builder.setMessage("Estás seguro que quieres guardar el alumno a la lista de alumnos?")

        // Botón Si
        builder.setPositiveButton("Si") { _, _ ->
            // Si usuario dice si, pues guardamos los datos al archivo nuestro
            saveDataToHistory()
            // No hacemos nada y mostramos snackbar
            showSnackbar("La información se ha registrado en histórico")
        }
        // Si usuario dice no
        builder.setNegativeButton("No") { _, _ ->
            // No hacemos nada y mostramos snackbar
            showSnackbar("La información no ha quedado registrada")
        }
        // Mostramos AlertDialog
        builder.create().show()
    }
    // SnackBar (Alternativa a toast)
    private fun showSnackbar(message: String) {
        val snackbar = view?.let {
            Snackbar.make( it,message, Snackbar.LENGTH_SHORT )
        }
        snackbar?.show()
    }


    // Metodos
    private fun ocultarTeclado(view: View) {
        val context = context
        if (context != null) {
            val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun lanzarSegundaActividad() {
        // Se crea un objeto de tipo Intent
        val myIntent = Intent(context, SecondActivityLeerDatos::class.java).apply {
            // Se añade la información a pasar por clave-valor
            putExtra(EXTRA_NOMBRE, binding.edNombre.text.toString())
        }
        getResult.launch(myIntent)
    }
    @SuppressLint("SetTextI18n")
    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {

        if (it.resultCode == Activity.RESULT_OK) {
            val dia = it.data?.getStringExtra(EXTRA_DIA)
            val mes = it.data?.getStringExtra(EXTRA_MES)
            val anyo = it.data?.getStringExtra(EXTRA_ANYO)
            val tipoCiclo = it.data?.getStringExtra(EXTRA_TIPOCICLO)
            val ciclo = it.data?.getStringExtra(EXTRA_CICLO)


            // Método para modificar la apariencia visual y mostrar datos
            modificarAparienciaDatosObtenidos(dia, mes, anyo, tipoCiclo, ciclo)
        }
        if (it.resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(context, "Datos no quedan registrados", Toast.LENGTH_SHORT).show()
        }

    }
    // Método para modificar la apariencia visual
    @SuppressLint("SetTextI18n")
    private fun modificarAparienciaDatosObtenidos(dia: String?, mes: String?, anyo: String?,
                                                  tipoCiclo: String?, ciclo: String?) {
        binding.txtFechaNac.text = "Fecha Nac: $dia / $mes / $anyo"
        binding.txtCiclo.text = "Modalidad: $tipoCiclo Ciclo: $ciclo"

        binding.btnObtenerDatos.isEnabled = true
        binding.btnGuardarHistorico.isEnabled = false

        alumno.actualizarNombre(binding.edNombre.text.toString())
        alumno.actualizarDatos(dia, mes, anyo, tipoCiclo, ciclo)

    }

    // PARTE PARA GUARDAR LOS DATOS EN EL ARCHIVO -- -- -- -- -- *
    // PARTE PARA GUARDAR LOS DATOS EN EL ARCHIVO -- -- -- -- -- * *
    // PARTE PARA GUARDAR LOS DATOS EN EL ARCHIVO -- -- -- -- -- *

    // Guardar datos al archivo
    private fun saveDataToHistory() {
        val txtHistoricoToSave = "${alumno.dia};${alumno.mes};${alumno.anyo};${alumno.nombre};" +
                "${alumno.tipoCiclo};${alumno.ciclo}"
        escribirEnFichero(txtHistoricoToSave)

        binding.btnGuardarHistorico.isEnabled = false
        binding.edNombre.text.clear()
        binding.txtFechaNac.text = resources.getString(R.string.fecha_nac)
        binding.txtCiclo.text = resources.getString(R.string.modalidad_ciclo)
        binding.txtResult.text = null
    }

    // Función para escribir un string en el fichero.
    private fun escribirEnFichero(datos: String) {
        try {
            val salida: OutputStreamWriter
            // Si el fichero no existe se crea,
            // si existe se añade la información.
            // Si quisieramos sobreescribir el fichero
            // utilizaríamos MODE_PRIVATE en lugar de MODE_APPEND
            salida = OutputStreamWriter(
                // requireActivity() porque tiene que llamar a MainActivity, y no a Fragment
                requireActivity().openFileOutput(getString(R.string.filename), AppCompatActivity.MODE_APPEND)
            )
            // Se escribe en el fichero línea a línea.
            salida.write(datos + '\n')
            // Se confirma la escritura.
            salida.flush()
            salida.close()
            //Toast.makeText(context, "Guardado correcto", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }

}
