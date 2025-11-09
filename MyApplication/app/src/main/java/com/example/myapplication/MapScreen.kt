package com.example.myapplication

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import com.example.myapplication.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*

@Composable
fun MapScreen() {
    val context = LocalContext.current

    // 📍 Centro inicial del mapa
    val arequipaCenter = LatLng(-16.4090474, -71.537451)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(arequipaCenter, 12f)
    }

    // 🎥 Mover cámara hacia Arequipa
    LaunchedEffect(Unit) {
        cameraPositionState.animate(
            update = CameraUpdateFactory.newLatLngZoom(arequipaCenter, 13f),
            durationMs = 2000
        )
    }

    // 🗺️ Mostrar mapa
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {

        // 🟥 Polígonos de lugares importantes
        val mallAventuraPolygon = listOf(
            LatLng(-16.432292, -71.509145),
            LatLng(-16.432757, -71.509626),
            LatLng(-16.433013, -71.509310),
            LatLng(-16.432566, -71.508853)
        )

        val parqueLambramaniPolygon = listOf(
            LatLng(-16.422704, -71.530830),
            LatLng(-16.422920, -71.531340),
            LatLng(-16.423264, -71.531110),
            LatLng(-16.423050, -71.530600)
        )

        val plazaDeArmasPolygon = listOf(
            LatLng(-16.398866, -71.536961),
            LatLng(-16.398744, -71.536529),
            LatLng(-16.399178, -71.536289),
            LatLng(-16.399299, -71.536721)
        )

        // Dibujar los polígonos
        Polygon(
            points = plazaDeArmasPolygon,
            strokeColor = Color.Red,
            fillColor = Color(0x330000FF), // Azul transparente
            strokeWidth = 5f
        )
        Polygon(
            points = parqueLambramaniPolygon,
            strokeColor = Color.Green,
            fillColor = Color(0x3300FF00),
            strokeWidth = 5f
        )
        Polygon(
            points = mallAventuraPolygon,
            strokeColor = Color.Magenta,
            fillColor = Color(0x33FF00FF),
            strokeWidth = 5f
        )

        // 🟦 Polilíneas (rutas o caminos)
        val ruta1 = listOf(
            LatLng(-16.433415, -71.5442652), // JLByR
            LatLng(-16.4205151, -71.4945209), // Paucarpata
            LatLng(-16.398866, -71.536961)    // Plaza de Armas
        )

        val ruta2 = listOf(
            LatLng(-16.3524187, -71.5675994), // Zamacola
            LatLng(-16.432292, -71.509145)    // Mall Aventura
        )

        // 🔵 Dibujar polilíneas
        Polyline(
            points = ruta1,
            color = Color.Blue,
            width = 8f
        )

        Polyline(
            points = ruta2,
            color = Color.Red,
            width = 6f
        )

        // 📌 Marcadores con ícono “gorro”
        val locations = listOf(
            LatLng(-16.433415, -71.5442652), // JLByR
            LatLng(-16.4205151, -71.4945209), // Paucarpata
            LatLng(-16.3524187, -71.5675994)  // Zamacola
        )

        locations.forEachIndexed { index, location ->
            val title = when (index) {
                0 -> "José Luis Bustamante y Rivero"
                1 -> "Paucarpata"
                else -> "Zamacola"
            }

            Marker(
                state = MarkerState(position = location),
                title = title,
                snippet = "Punto de interés",
                icon = bitmapFromDrawable(context, R.drawable.gorro, 96, 96)
            )
        }
    }
}

/**
 * Convierte un drawable en BitmapDescriptor redimensionado
 */
fun bitmapFromDrawable(context: Context, resId: Int, width: Int, height: Int): BitmapDescriptor {
    val bitmap = BitmapFactory.decodeResource(context.resources, resId)
    val resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false)
    return BitmapDescriptorFactory.fromBitmap(resizedBitmap)
}



