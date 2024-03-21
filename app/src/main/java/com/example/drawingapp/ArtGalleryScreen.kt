package com.example.drawingapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
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

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                LazyColumn(Modifier.fillMaxSize()) {
                    // We use a LazyColumn since the layout manager of the RecyclerView is a vertical LinearLayoutManager
                }
            }
            /*
                Column {
                    GotoDrawingScreen()
                }
            }*/
        }
    }

    @Composable
    fun GotoDrawingScreen() {
        Button(onClick = { findNavController().navigate(R.id.action_artGalleryScreen_to_drawScreen2) }
        ) {
        }
    }

    /*@Composable
    fun ListItem(data: MyData, modifier: Modifier = Modifier) {
        Row(modifier.fillMaxWidth()) {
            Text(text = data.name)
            // … other composables required for displaying `data`
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