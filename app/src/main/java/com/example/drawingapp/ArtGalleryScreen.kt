package com.example.drawingapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.drawingapp.databinding.FragmentArtGalleryScreenBinding

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
    private val vm: MyViewModel by activityViewModels()
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
                    Column {
                        Button(onClick = {
                            Log.d("NAV", "navigating to draw screen 2")
                            findNavController().navigate(R.id.action_artGalleryScreen_to_drawScreen2)
                        }
                        ) {
                            Text(text = "Navigate to DrawScreen2")
                        }
                        Spacer(modifier = Modifier.padding(32.dp))
                        Button(onClick = {
                            Log.d("NAV", "navigating to save screen 2")
                            findNavController().navigate(R.id.action_artGalleryScreen_to_saveScreen2)
                        }
                        ) {
                            Text(text = "Navigate to SaveScreen2")
                        }

                        val allDrawings by vm.allDrawings.observeAsState()
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            if (allDrawings != null) {
                                items(allDrawings!!.size) {
                                    ListItem(allDrawings!![it])
                                }
                            }

                        }
                    }
                }
            }
            return view
        }
    }

    @Composable
    fun ListItem(data: Drawing, modifier: Modifier = Modifier) {
        Row(modifier
                .fillMaxWidth()
                .padding(horizontal=Dp(40f), vertical=Dp(3f))
                .height(Dp(350f))) {
            Card(modifier.padding(all=Dp(20f)),
                border= BorderStroke(width=Dp(1f), color=Color.Gray)
            ) {
                Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()) {
                    Text(text = data.fileName, fontSize=TextUnit(value=26f, type=TextUnitType.Sp))
                    Spacer(modifier.padding(8.dp))
                    Image(
                        vm.getImageFromFilename(data.fileName, context).asImageBitmap(),
                        "Default Description",
                        modifier=modifier
                                .background(color = Color.White)
                                .height(Dp(180f))
                                .width(Dp(180f))
                    )
                }

            }
        }

    }
}