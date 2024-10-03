package com.example.calculatorcompose

import android.os.Bundle
import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalConfiguration
import com.example.calculatorcompose.ui.theme.CalculatorComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorComposeTheme {
                Calculadora()
            }
        }
    }
}

@Composable
fun Resultado(resultado: String, horizontal: Boolean) {
    val fontSize = if (!horizontal) 80.sp else 70.sp
    val modifier = if (!horizontal) {
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.30f)
    } else {
        Modifier
            .padding(top = 0.dp)
            .width(380.dp)
            .fillMaxHeight(0.26f)
    }

    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.End,
        modifier = modifier
    ) {
        Text(
            text = resultado,
            fontSize = fontSize
        )
    }
}

@Composable
fun BotonCalculadora(
    text: String,
    size: Int,
    color: Color,
    widthDp: Int,
    esOperacion: Boolean,
    resultado: String,
    onTextChange: (String) -> Unit
) {
    val onClickAction: () -> Unit = {
        when (text) {
            "AC" -> onTextChange("0")
            "=" -> onTextChange(calcularResultado(resultado))
            else -> {
                if (resultado == "0") {
                    onTextChange(text)
                } else {
                    onTextChange(if (resultado.length < 7) resultado + text else resultado)
                }
            }
        }
    }

    Button(
        modifier = Modifier
            .width(widthDp.dp)
            .height(100.dp)
            .padding(5.dp),
        onClick = {
            onClickAction()
        },
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {
        Text(
            text = text, fontSize = size.sp
        )
    }
}

@Composable
fun FilaDeBotones(
    tresBotones: Boolean,
    text1: String,
    text2: String,
    text3: String,
    text4: String,
    size: Int,
    resultado: String,
    onTextChange: (String) -> Unit
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(95.dp)
    ) {
        val colorOperador = Color(73, 108, 124)
        val colorNumero = Color(3, 45, 59)

        val color1 = when (text4) {
            "/", "x", "-", "+", "=" -> colorOperador
            else -> colorNumero
        }
        if (!tresBotones) {
            BotonCalculadora(text1, size, colorNumero, 90, true, resultado, onTextChange)
            BotonCalculadora(text2, size, colorNumero, 90, true, resultado, onTextChange)
            BotonCalculadora(text3, size, colorNumero, 90, true, resultado, onTextChange)
            BotonCalculadora(text4, size, color1, 90, false, resultado, onTextChange)
        } else if (text2 == "Share") {
            BotonCalculadora(text1, size, colorOperador, 90, true, resultado, onTextChange)
            BotonCalculadora(text2, 20, colorOperador, 90, false, resultado, onTextChange)
            BotonCalculadora(text3, size, colorOperador, 90, false, resultado, onTextChange)
            BotonCalculadora(text4, size, colorOperador, 90, false, resultado, onTextChange)
        } else {
            BotonCalculadora(text1, size, colorOperador, 90, true, resultado, onTextChange)
            BotonCalculadora(text2, size, colorNumero, 90, true, resultado, onTextChange)
            BotonCalculadora(text3, size, colorOperador, 90, false, resultado, onTextChange)
            BotonCalculadora(text4, size, colorOperador, 90, false, resultado, onTextChange)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Preview(
    showSystemUi = true,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
@Composable
fun Calculadora() {
    var resultado by rememberSaveable { mutableStateOf("0") }
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            CalculadoraHorizontal(resultado) { nuevoResultado -> resultado = nuevoResultado }
        }
        else -> {
            CalculadoraVertical(resultado) { nuevoResultado -> resultado = nuevoResultado }
        }
    }
}

@Composable
fun CalculadoraVertical(resultado: String, onTextChange: (String) -> Unit) {
    CalculatorComposeTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Resultado(resultado, false)
            FilaDeBotones(true, "%", "Share", "AC", "/", 25, resultado, onTextChange)
            FilaDeBotones(false, "1", "2", "3", "x", 25, resultado, onTextChange)
            FilaDeBotones(false, "4", "5", "6", "-", 25, resultado, onTextChange)
            FilaDeBotones(false, "7", "8", "9", "+", 25, resultado, onTextChange)
            FilaDeBotones(true, ".", "0", ",", "=", 25, resultado, onTextChange)
        }
    }
}

@Composable
fun CalculadoraHorizontal(resultado: String, onTextChange: (String) -> Unit) {
    val colorCustomisableOperador = Color(73, 108, 124)
    val colorCustomisableNumeros = Color(3, 45, 59)
    CalculatorComposeTheme {
        val altura = 80
        val padding = 5
        Row {
            Column {
                Resultado(resultado, false)
                Row(modifier = Modifier
                    .padding(padding.dp)
                    .height(altura.dp)) {
                    BotonCalculadora("1", 30, colorCustomisableNumeros, 120, true, resultado, onTextChange)
                    BotonCalculadora("2", 30, colorCustomisableNumeros, 120, true, resultado, onTextChange)
                    BotonCalculadora("3", 30, colorCustomisableNumeros, 120, true, resultado, onTextChange)
                    BotonCalculadora(".", 30, colorCustomisableOperador, 120, true, resultado, onTextChange)
                    BotonCalculadora("AC", 30, colorCustomisableOperador, 120, true, resultado, onTextChange)
                    BotonCalculadora("x", 30, colorCustomisableOperador, 120, false, resultado, onTextChange)
                    BotonCalculadora("/", 30, colorCustomisableOperador, 120, false, resultado, onTextChange)
                }
                Row(modifier = Modifier
                    .padding(padding.dp)
                    .height(altura.dp)) {
                    BotonCalculadora("4", 30, colorCustomisableNumeros, 120, true, resultado, onTextChange)
                    BotonCalculadora("5", 30, colorCustomisableNumeros, 120, true, resultado, onTextChange)
                    BotonCalculadora("6", 30, colorCustomisableNumeros, 120, true, resultado, onTextChange)
                    BotonCalculadora("0", 30,  colorCustomisableNumeros, 120, true, resultado, onTextChange)
                    BotonCalculadora("=", 30, colorCustomisableOperador, 120, false, resultado, onTextChange)
                    BotonCalculadora("-", 30, colorCustomisableOperador, 120, false, resultado, onTextChange)
                    BotonCalculadora("%", 30, colorCustomisableOperador, 120, false, resultado, onTextChange)
                }
                Row(modifier = Modifier
                    .padding(padding.dp)
                    .height(altura.dp)) {
                    BotonCalculadora("7", 30, colorCustomisableNumeros, 120, true, resultado, onTextChange)
                    BotonCalculadora("8", 30, colorCustomisableNumeros, 120, true, resultado, onTextChange)
                    BotonCalculadora("9", 30, colorCustomisableNumeros, 120, true, resultado, onTextChange)
                    BotonCalculadora(",", 30, colorCustomisableOperador, 120, true, resultado, onTextChange)
                    BotonCalculadora("+", 30, colorCustomisableOperador, 120, false, resultado, onTextChange)
                    BotonCalculadora("share", 30, colorCustomisableOperador, 240, false, resultado, onTextChange)
                }
            }
        }
    }
}

fun calcularResultado(expresion: String): String {
    if (expresion.isEmpty()) return "0"
    return try {
        val resultado = calcular(expresion)
        resultado.toString()
    } catch (e: Exception) {
        "Error"
    }
}

fun calcular(expresion: String): Double {
    return when {
        expresion.contains("+") -> {
            val calculo = expresion.split("+")
            calculo[0].toDouble() + calculo[1].toDouble()
        }
        expresion.contains("-") -> {
            val calculo = expresion.split("-")
            calculo[0].toDouble() - calculo[1].toDouble()
        }
        expresion.contains("x") || expresion.contains("*") -> {
            val calculo = expresion.split("x").ifEmpty { expresion.split("*") }
            calculo[0].toDouble() * calculo[1].toDouble()
        }
        expresion.contains("/") -> {
            val calculo = expresion.split("/")
            calculo[0].toDouble() / calculo[1].toDouble()
        }
        else -> expresion.toDouble()
    }
}
