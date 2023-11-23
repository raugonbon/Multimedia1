package com.raulgonzalez.actividad3
// (c) Raúl Enrique González Bondarchuk
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.raulgonzalez.actividad3.databinding.ActivitySecondHistoricoBinding

class SecondActivityHistorico : AppCompatActivity() {
    private lateinit var binding: ActivitySecondHistoricoBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_second_historico
        binding = ActivitySecondHistoricoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombreAlumno = intent.getStringExtra("nombreAlumno")
        val dia = intent.getStringExtra("dia")
        val mes = intent.getStringExtra("mes")
        val anyo = intent.getStringExtra("anyo")
        val groupAndClassroom = intent.getStringExtra("groupAndClassroom")
        val ciclo = intent.getStringExtra("ciclo")

        binding.sahNombreAlumno.text = nombreAlumno
        binding.sahFechaNac.text = "$dia de $mes de $anyo"
        binding.sahGrupoCiclo.text = groupAndClassroom
        binding.sahCiclo.text = ciclo

    }
}