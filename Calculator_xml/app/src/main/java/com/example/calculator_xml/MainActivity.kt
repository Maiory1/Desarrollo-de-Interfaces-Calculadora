package com.example.calculator_xml

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var valorResultado: EditText
    private var operador = ""
    private var numeroActual = 0.0
    private var numeroAnterior = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        valorResultado = findViewById(R.id.textView_Resultado)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setBotones()
    }

    private fun setBotones() {
        val botones = arrayOf(
            R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4,
            R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9,
            R.id.button_coma, R.id.button_divsion, R.id.button_multiplicacion,
            R.id.button_porcentaje, R.id.button_resta, R.id.button_suma,
            R.id.button_Borrar, R.id.button_igual
        )

        for (button in botones) {
            val botonx = findViewById<Button>(button)
            botonx.setOnClickListener { onClickBotones(botonx) }
        }
    }

    private fun appendToInput(valor: String) {
        valorResultado.append(valor)
    }

    private fun onClickBotones(view: View) {
        when (view.id) {
            R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4,

            R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9 -> {
                val botonx = findViewById<Button>(view.id)
                appendToInput(botonx.text.toString())
            }

            R.id.button_resta, R.id.button_suma, R.id.button_multiplicacion, R.id.button_divsion -> {
                val botonx = findViewById<Button>(view.id)
                setOperador(botonx.text.toString())
            }

            R.id.button_igual -> {
                calcularOperacion()
            }

            R.id.button_Borrar -> {
                valorResultado.text.clear()
                operador = ""
                numeroActual = 0.0
                numeroAnterior = 0.0
            }
            R.id.button_coma -> {
                if (!valorResultado.text.contains(".")) {
                    appendToInput(".")
                }
            }
        }
    }

    private fun setOperador(operar: String) {
        operador = operar
        numeroAnterior = valorResultado.text.toString().toDoubleOrNull() ?: 0.0
        valorResultado.text.clear()
    }

    private fun calcularOperacion() {
        numeroActual = valorResultado.text.toString().toDoubleOrNull() ?: 0.0
        var resultado: Double? = null


        resultado = when (operador) {
            "+" -> numeroAnterior + numeroActual
            "-" -> numeroAnterior - numeroActual
            "*", "x" -> numeroAnterior * numeroActual
            "/" -> if (numeroActual != 0.0) numeroAnterior / numeroActual else null
            else -> numeroAnterior
        }

        if (null !=  resultado) {
            valorResultado.setText(resultado.toString())
        } else {
            valorResultado.setText("Error")
        }

        operador = ""
    }
}
