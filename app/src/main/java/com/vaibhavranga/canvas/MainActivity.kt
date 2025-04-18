package com.vaibhavranga.canvas

import android.graphics.Paint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vaibhavranga.canvas.ui.theme.CanvasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CanvasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    InstagramLogo(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun InstagramLogo(modifier: Modifier = Modifier) {
    Canvas(
        modifier = modifier
            .size(300.dp)
            .padding(12.dp)
    ) {
        drawRoundRect(
            brush = Brush.linearGradient(
                listOf(Color.Yellow, Color.Red, Color.Blue)
            ),
            cornerRadius = CornerRadius(x = 120f, y = 120f),
            style = Stroke(width = 42f)
        )
        drawCircle(
            brush = Brush.linearGradient(
                listOf(Color.Red, Color.Blue)
            ),
            radius = 150f,
            style = Stroke(width = 42f)
        )
        drawCircle(
            brush = Brush.linearGradient(
                listOf(Color.Red, Color.Blue)
            ),
            radius = 42f,
            center = Offset(
                x = size.width.times(0.8f),
                y = size.height.times(0.2f)
            )
        )
    }
}

@Composable
fun FacebookLogo(modifier: Modifier = Modifier) {
    Canvas(
        modifier = modifier
            .size(300.dp)
            .padding(12.dp)
    ) {
        val paint = Paint().apply {
            textAlign = Paint.Align.CENTER
            textSize = 820f
            color = Color.White.toArgb()
        }

        drawRoundRect(
            color = Color.Blue,
            cornerRadius = CornerRadius(x = 120f, y = 120f)
        )
        drawContext.canvas.nativeCanvas.drawText(
            "f",
            center.x.plus(100f),
            center.y.plus(320f),
            paint
        )
    }
}



@Preview(showSystemUi = true)
@Composable
private fun MyPreview() {
    CanvasTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            FacebookLogo()
        }
    }
}