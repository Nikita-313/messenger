package com.cinetech.ui.screen.chat.utils

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class UserMessageWithTailShape(
    private val topStart: CornerSize = CornerSize(30.dp),
    private val topEnd: CornerSize = CornerSize(30.dp),
    private val bottomStart: CornerSize = CornerSize(30.dp),
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {

        val shapeTopStart: Float = topStart.toPx(size, density)
        val shapeTopEnd: Float = topEnd.toPx(size, density)
        val shapeBottomStart: Float = bottomStart.toPx(size, density)

        val tailShape: Float = CornerSize(10.dp).toPx(size, density)
        val tailHeight: Float = CornerSize(2.dp).toPx(size, density)
        val addToRectHeight = CornerSize(10.dp).toPx(size, density)

        return Outline.Generic(Path().apply {
            reset()

            moveTo(shapeTopEnd, 0f)
            lineTo(size.width - shapeTopEnd, 0f)

            arcTo(
                rect = Rect(
                    offset = Offset(size.width - shapeTopEnd, 0f),
                    size = Size(shapeTopEnd, shapeTopEnd)
                ),
                startAngleDegrees = 270f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )

            lineTo(size.width, size.height - tailShape - tailHeight)
            arcTo(
                rect = Rect(
                    offset = Offset(size.width, size.height - tailShape - tailHeight - addToRectHeight),
                    size = Size(tailShape, tailShape + addToRectHeight)
                ),
                startAngleDegrees = 180f,
                sweepAngleDegrees = -90f,
                forceMoveTo = false
            )

            lineTo(size.width + tailShape / 2, size.height)

            lineTo(0f + shapeBottomStart, size.height)

            arcTo(
                rect = Rect(
                    offset = Offset(0f, size.height - shapeBottomStart),
                    size = Size(shapeBottomStart, shapeBottomStart)
                ),
                startAngleDegrees = 90f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(0f, size.height - shapeBottomStart)


            arcTo(
                rect = Rect(
                    offset = Offset(0f, 0f),
                    size = Size(shapeTopStart, shapeTopStart)
                ),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )

            close()
        })
    }

}