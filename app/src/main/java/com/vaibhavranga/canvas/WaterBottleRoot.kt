package com.vaibhavranga.canvas

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vaibhavranga.canvas.ui.theme.CanvasTheme

@Composable
fun WaterBottleRoot(modifier: Modifier = Modifier) {
    var usedAmount by remember { mutableIntStateOf(400) }
    val totalWaterAmount by remember { mutableIntStateOf(2400) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 20.dp,
            alignment = Alignment.CenterVertically
        ),
        modifier = modifier
            .fillMaxSize()
    ) {
        WaterBottle(
            totalWaterAmount = totalWaterAmount,
            unit = "ml",
            usedWaterAmount = usedAmount
        )
        Text(text = "Total amount is: $totalWaterAmount")
        Button(
            onClick = {
                if (usedAmount < totalWaterAmount) {
                    usedAmount += 200
                }
            }
        ) {
            Text(text = stringResource(R.string.pour_water))
        }
    }
}

@Composable
fun WaterBottle(
    totalWaterAmount: Int,
    unit: String,
    usedWaterAmount: Int,
    modifier: Modifier = Modifier,
    waterColor: Color = Color(0xFF279eff),
    bottleColor: Color = Color.White,
    capColor: Color = Color(0xFF0065b9)
) {
    var waterPercentage = animateFloatAsState(
        targetValue = usedWaterAmount.toFloat() / totalWaterAmount.toFloat(),
        label = "Water waves animation",
        animationSpec = tween(durationMillis = 1000)
    ).value
    var usedWaterAmountAnimated = animateIntAsState(
        targetValue = usedWaterAmount,
        label = "Used water amount animation",
        animationSpec = tween(durationMillis = 1000)
    ).value

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .width(200.dp)
            .height(600.dp)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val width = size.width
            val height = size.height
            val capWidth = size.width * 0.55f
            val capHeight = size.height * 0.13f

            val bottleBodyPath = Path().apply {
                moveTo(x = width * 0.3f, y = height * 0.1f)
                lineTo(x = width * 0.3f, y = height * 0.2f)
                quadraticTo(x1 = 0f, y1 = height * 0.3f, x2 = 0f, y2 = height * 0.4f)
                lineTo(x = 0f, y = height * 0.95f)
                quadraticTo(x1 = 0f, y1 = height, x2 = width * 0.05f, y2 = height)
                lineTo(x = width * 0.95f, y = height)
                quadraticTo(x1 = width, y1 = height, x2 = width, y2 = height * 0.95f)
                lineTo(x = width, y = height * 0.4f)
                quadraticTo(x1 = width, y1 = height * 0.3f, x2 = width * 0.7f, y2 = height * 0.2f)
                lineTo(x = width * 0.7f, y = height * 0.1f)
                close()
            }
            clipPath(
                path = bottleBodyPath
            ) {
                drawRect(
                    size = size,
                    color = Color.Red
                )
                val waterWavesYPosition = (1 - waterPercentage) * size.height
                val waterPath = Path().apply {
                    moveTo(x = 0f, y = waterWavesYPosition)
                    lineTo(x = size.width, y = waterWavesYPosition)
                    lineTo(x = size.width, y = size.height)
                    lineTo(x = 0f, y = size.height)
                    close()
                }
                drawPath(
                    path = waterPath,
                    color = waterColor
                )
            }
            drawRoundRect(
                color = capColor,
                size = Size(width = capWidth, height = capHeight),
                topLeft = Offset(x = size.width / 2 - capWidth / 2f, y = 0f),
                cornerRadius = CornerRadius(x = 45f, y = 45f)
            )
        }
        val annotatedText = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = if (waterPercentage > 0.45f) bottleColor else waterColor,
                    fontSize = 44.sp
                )
            ) {
                append(usedWaterAmountAnimated.toString())
            }
            withStyle(
                style = SpanStyle(
                    color = if (waterPercentage > 0.45f) bottleColor else waterColor,
                    fontSize = 22.sp
                )
            ) {
                append(" ")
                append(unit.toString())
            }
        }
        Text(text = annotatedText)
    }
}

@Preview(showSystemUi = true)
@Composable
private fun WaterBottlePreview() {
    CanvasTheme {
        WaterBottleRoot()
    }
}
