package com.raulgonzalez.actividad3
// (c) Raúl Enrique González Bondarchuk
import java.util.Calendar

class Alumno(var nombre: String,
             var dia: String,
             var mes: String,
             var anyo: String,
             var tipoCiclo: String,
             var ciclo: String ){




    fun actualizarNombre(nombre: String?){
        this.nombre = nombre ?: ""
    }

    fun actualizarDatos(dia: String?, mes: String?, anyo: String?, tipoCiclo: String?, ciclo: String?) {
        this.dia = dia ?: ""
        this.mes = mes ?: ""
        this.anyo = anyo ?: ""
        this.tipoCiclo = tipoCiclo ?: ""
        this.ciclo = ciclo ?: ""

    }

    fun groupAssign() : String {
        var result = ""

        if (tipoCiclo == "Presencial") {
            when (ciclo) {
                "ASIR" -> { result =  "Grupo A" + "\n" + "Aula 101" } // ASIR Presencial --> Grupo A Aula 101
                "DAM" -> { result =  "Grupo C" + "\n" + "Aula 201" } // DAW Presencial --> Grupo C Aula 201
                "DAW" -> { result =  "Grupo E" + "\n" + "Aula 301" } // DAM Presencial --> Grupo E Aula 301
            }
        } else {
            when (ciclo) {
                "ASIR" -> { result =  "Grupo B" + "\n" + "Aula 102" } // ASIR Semipresencial --> Grupo B Aula 102
                "DAM" -> { result =  "Grupo D" + "\n" + "Aula 202" } // DAW Semipresencial --> Grupo D Aula 202
                "DAW" -> { result =  "Grupo F" + "\n" + "Aula 302" } // DAM Semipresencial --> Grupo F Aula 302
            }
        }
        return result
    }

    // Calcular Edad
    public fun calculateAge() : Int {
        // Obtenemos los datos EditText

        val dia = this.dia.toInt()
        val mes = this.mes.toInt()
        val anyo = this.anyo.toInt()

        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH) + 1
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        var age = currentYear - anyo
        // Comprobamos si ha tenido el cumpleaños o no
        if (mes > currentMonth || (mes == currentMonth && dia > currentDay))
            age -= 1
        return age
    }
}