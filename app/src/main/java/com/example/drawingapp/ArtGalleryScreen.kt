package com.example.drawingapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

/**
 * @author          - Christian E. Anderson
 * @teammate        - Crosby White & Matthew Williams
 * @version         - 16-FEB-2024
 *
 *      This file defines the art gallery screen for the Drawing App.
 */

/**
 *  Phase 2:
 *      The UI should be display a list of images that the user has drawn, if any.
 *      The UI should be done using Jetpack Compose.
 *      If the user clicks on a drawing in the list, it should load that drawing in the drawing canvas.
 *
 */
class ArtGalleryScreen : Fragment() {
    /**
     *
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_art_gallery_screen, container, false)
        val composeView = view.findViewById<ComposeView>(R.id.compose_view)
        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GotoDrawingScreen()
                }

            }
        }
        return view
    }

    @Composable
    fun GotoDrawingScreen() {
        Button(onClick = { findNavController().navigate(R.id.action_artGalleryScreen_to_drawScreen2) }
        ) {

            Log.d("NAV", "navigating to main screen")
        }
    }

    /*@Composable
    fun ListItem(data: MyData, modifier: Modifier = Modifier) {
        Row(modifier.fillMaxWidth()) {
            Text(text = data.name)
            // … other composable required for displaying `data`
        }
    }


    val binding = FragmentArtGalleryScreenBinding.inflate(layoutInflater, container, false)

    // Switch to draw screen
    binding.button.setOnClickListener
    {
        Log.d("SPLASH", "navigating to draw screen")
        findNavController().navigate(R.id.action_mainScreen_to_drawScreen2)
    }

    // Switch to save screen
    binding.button2.setOnClickListener
    {
        Log.d("SPLASH", "navigating to save screen")
        findNavController().navigate(R.id.action_mainScreen_to_saveScreen2)
    }
    return binding.root
}*/
}