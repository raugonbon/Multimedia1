package com.raulgonzalez.actividad3
// (c) Raúl Enrique González Bondarchuk
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.raulgonzalez.actividad3.databinding.ActivitySecondLeerDatosBinding
import java.util.Calendar

class SecondActivityLeerDatos : AppCompatActivity() {

    var diaAlumno = ""
    var mesAlumno = ""
    var anyoAlumno = ""

    var rbTipoCiclo = ""
    var rbCiclo = ""

    var fechaValida = false

    private lateinit var binding: ActivitySecondLeerDatosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_second_leer_datos)
        binding = ActivitySecondLeerDatosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recuperamos los datos (Nombre) de MainFragment
        val nombreAlumno = intent.getStringExtra(MainFragment.EXTRA_NOMBRE)

        // Se recuperan los datos y se asignan al TextView.
        binding.txtNombreAlumno.text = nombreAlumno

        // Btn para introducir la fecha
        binding.btnIntroducirFecha.setOnClickListener {
            myDatePicker()
        }

        // Btn Aceptar
        binding.btnAceptar.setOnClickListener {
            if(diaAlumno.isEmpty() || mesAlumno.isEmpty() || anyoAlumno.isEmpty()){
                Toast.makeText(this , "Se debe introducir la fecha", Toast.LENGTH_SHORT).show()
            } else if (fechaValida){

                // Guardamos Radio btn pulsado del grupo rbgTipoCiclo
                val selectedRadioButtonId = binding.rbgTipoCiclo.checkedRadioButtonId
                rbTipoCiclo = findViewById<RadioButton>(selectedRadioButtonId).text.toString()
                // Guardamos Radio btn pulsado del grupo rbgCiclo
                val selectedRadioButton2Id = binding.rbgCiclo.checkedRadioButtonId
                rbCiclo = findViewById<RadioButton>(selectedRadioButton2Id).text.toString()

                // Introduzco los datos para devolver
                val intentResult: Intent = Intent().apply {
                    putExtra(MainFragment.EXTRA_DIA, diaAlumno)
                    putExtra(MainFragment.EXTRA_MES, mesAlumno)
                    putExtra(MainFragment.EXTRA_ANYO, anyoAlumno)

                    putExtra(MainFragment.EXTRA_TIPOCICLO, rbTipoCiclo)
                    putExtra(MainFragment.EXTRA_CICLO, rbCiclo)
                }
                // Devolvemos los datos
                setResult(Activity.RESULT_OK, intentResult)
                // Finalizamos la actividad
                ocultarTeclado(it)
                finish()
            } else{
                ocultarTeclado(it)
                Toast.makeText(this, "La fecha no es válida", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnCancelar.setOnClickListener {
            // Cancelamos el resultado
            setResult(Activity.RESULT_CANCELED)
            ocultarTeclado(it)
            // Finalizamos la actividad
            finish()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun myDatePicker() {
        val cal = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->

            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, day)

            // Obtenemos la validación de la fecha
            fechaValida = dateIsValid(cal)
            // Comprobamos que la fecha es valida
            if(fechaValida){
                diaAlumno = cal.get(Calendar.DAY_OF_MONTH).toString()
                mesAlumno = cal.get(Calendar.MONTH).toString()
                anyoAlumno = cal.get(Calendar.YEAR).toString()
                binding.txtFechaNac.text = "$diaAlumno / $mesAlumno  / $anyoAlumno"

                Toast.makeText(this , "La fecha es valida", Toast.LENGTH_SHORT).show()
            }
            // si la fecha no es válida
            else {
                Toast.makeText(this , "La fecha no es valida", Toast.LENGTH_SHORT).show()
            }
        }
        DatePickerDialog(
            this,
            dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun dateIsValid(cal: Calendar) : Boolean {
        var result = false
        // Los elementos de fecha Cal
        val diaInt = cal.get(Calendar.DAY_OF_MONTH)
        val mesInt = cal.get(Calendar.MONTH) + 1
        val anyoInt = cal.get(Calendar.YEAR)
        // Los elementos de fecha actual
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1
        val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        // Comprobamos que la fecha pasada por paramentro es anterior que la fecha actual
        if (anyoInt < currentYear) {
            result = true
        } else if (anyoInt == currentYear && (mesInt < currentMonth || (mesInt == currentMonth && diaInt < currentDay))) {
            result = true
        }
        return result
    }

    private fun ocultarTeclado(view: View) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}