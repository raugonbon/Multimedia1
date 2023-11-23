package com.raulgonzalez.actividad3
// (c) Raúl Enrique González Bondarchuk
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoricoAdapter(historicoList: MutableList<AlumnoHistory>, context: Context) :
    RecyclerView.Adapter<HistoricoAdapter.HistoricoViewHolder>() {

    var myHistorico: MutableList<AlumnoHistory>
    var myContext: Context
    init {
        myHistorico = historicoList
        myContext = context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            HistoricoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return HistoricoViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoricoViewHolder, position: Int) {
        val item = myHistorico[position]
        holder.bind(item, myContext)
    }

    override fun getItemCount(): Int {
        return myHistorico.size
    }

    class HistoricoViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val dia =  view.findViewById(R.id.diaHistorico) as TextView
        private val mes =  view.findViewById(R.id.mesHistorico) as TextView
        private val anyo =  view.findViewById(R.id.anyoHistorico) as TextView
        private val nombre =  view.findViewById(R.id.nombreHistorico) as TextView
        private val tipoCiclo =  view.findViewById(R.id.tipoCicloHistorico) as TextView
        private val ciclo =  view.findViewById(R.id.cicloHistorico) as TextView

        @SuppressLint("SetTextI18n")
        fun bind(historico: AlumnoHistory, context: Context) {
            Log.d("bind", historico.dia)
            dia.text = historico.dia
            mes.text = historico.mes
            anyo.text = historico.anyo
            nombre.text = historico.nombre
            tipoCiclo.text ="(" + historico.tipoCiclo + ")"
            ciclo.text = historico.ciclo

            // Creamos String para los valores necesarios
            val tipoCicloSt =  tipoCiclo.text.toString()
            val cicloSt = ciclo.text.toString()

            val nombre = nombre.text.toString()
            val dia = dia.text.toString()
            val mes = mes.text.toString()
            val anyo = anyo.text.toString()

            // Creamos un alumno
            var alumno = Alumno("", "", "", "", "", "")
            alumno.actualizarNombre(nombre)
            alumno.actualizarDatos(dia, mes, anyo, tipoCicloSt, cicloSt)


            // Usamos el metodo de MainActivity
            val groupAndClassroom = alumno.groupAssign()

            // Definimos el código a ejecutar si se hace click en el item
            itemView.setOnClickListener {

                // Se crea un objeto de tipo Intent
                val myIntent = Intent(context, SecondActivityHistorico::class.java)

                // Add data to the Intent
                myIntent.putExtra("nombreAlumno", nombre)
                myIntent.putExtra("dia", dia)
                myIntent.putExtra("mes", mes)
                myIntent.putExtra("anyo", anyo)
                myIntent.putExtra("groupAndClassroom", groupAndClassroom)
                myIntent.putExtra("ciclo", cicloSt)

                // Abre mi activity
                context.startActivity(myIntent)
            }
        }
    }



}