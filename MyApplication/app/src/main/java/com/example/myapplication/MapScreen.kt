package com.example.myapplication

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen() {
    val context = LocalContext.current

    // 📍 Lista de ubicaciones
    val locations = listOf(
        LatLng(-16.433415, -71.5442652), // JLByR
        LatLng(-16.4205151, -71.4945209), // Paucarpata
        LatLng(-16.3524187, -71.5675994)  // Zamacola
    )

    // 🧭 Cámara inicial centrada en Arequipa
    val arequipaCenter = LatLng(-16.4090474, -71.537451)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(arequipaCenter, 11f)
    }

    // 🎥 Control programático de la cámara (mover hacia Yura)
    LaunchedEffect(Unit) {
        // Esperar un momento para que el mapa se inicialice
        kotlinx.coroutines.delay(1500)

        // Mover con animación a Yura
        cameraPositionState.animate(
            update = CameraUpdateFactory.newLatLngZoom(
                LatLng(-16.2520984, -71.6836503), // Yura
                12f
            ),
            durationMs = 3000 // 3 segundos de animación
        )
    }

    // 🗺️ Mostrar el mapa
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        // 🔁 Colocar marcadores con ícono “gorro”
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

// 🧢 Función auxiliar para convertir tu drawable en un ícono redimensionado
fun bitmapFromDrawable(context: Context, resId: Int, width: Int, height: Int): BitmapDescriptor {
    val bitmap = BitmapFactory.decodeResource(context.resources, resId)
    val resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false)
    return BitmapDescriptorFactory.fromBitmap(resizedBitmap)
}


