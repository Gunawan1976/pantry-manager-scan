package org.example.pantry_manager_scan.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class CurvedBottomNavShape(private val cutoutRadiusDp: Float = 42f) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        // Konversi ukuran radius dari DP ke Pixel
        val cutoutRadius = density.run { cutoutRadiusDp.dp.toPx() }
        val centerX = size.width / 2f

        // 1. Buat bentuk kotak penuh (sebesar area bottom bar)
        val baseRect = Path().apply {
            addRect(Rect(0f, 0f, size.width, size.height))
        }

        // 2. Buat bentuk lingkaran di titik tengah atas
        val circle = Path().apply {
            addOval(
                Rect(
                    center = Offset(centerX, 0f), // 0f artinya tepat di garis batas atas
                    radius = cutoutRadius
                )
            )
        }

        // 3. Kurangi kotak dengan lingkaran untuk membuat lubang (cut-out)
        val finalPath = Path.combine(
            operation = PathOperation.Difference,
            path1 = baseRect,
            path2 = circle
        )

        return Outline.Generic(finalPath)
    }
}