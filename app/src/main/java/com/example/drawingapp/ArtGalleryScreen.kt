package com.example.drawingapp

import android.graphics.Bitmap
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

/**
 * @author          - Christian E. Anderson
 * @teammate(s)     - Crosby White & Matthew Williams
 * @version         - Phase 2 = 22-MAR-2024; Phase 1 = 16-FEB-2024
 *
 *      This file defines the art gallery screen for the Drawing App.
 *
 *  Phase 2:
 *      The UI should be display a list of images that the user has drawn, if any.
 *      The UI should be done using Jetpack Compose.
 *      If the user clicks on a drawing in the list, it should load that drawing in the drawing canvas.
 */
class ArtGalleryScreen : Fragment() {
    private val vm: MyViewModel by activityViewModels()

    /**
     * Defines what the view will look at creation.
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
                        Spacer(Modifier.padding(15.dp))
                        Row (Modifier.align(Alignment.CenterHorizontally)) {
                            Button(onClick = {
                                Log.d("NAV", "navigating to save screen 2")
                                findNavController().navigate(R.id.action_artGalleryScreen_to_splashScreen)
                            }, modifier=
                                Modifier.background(color=MaterialTheme.colorScheme.background)
                            ) {
                                Text(text = "Back")
                            }
                            Spacer(modifier = Modifier.padding(32.dp))
                            Button(onClick = {
                                Log.d("NAV", "navigating to draw screen 2")
                                findNavController().navigate(R.id.action_artGalleryScreen_to_drawScreen2)
                            }
                            ) {
                                Text(text = "Draw Screen")
                            }
                        }

                        Spacer(modifier=Modifier.padding(15.dp))

                        val allDrawings by vm.allDrawings.observeAsState()
                        val gridState = rememberLazyStaggeredGridState()
                        LazyVerticalStaggeredGrid(
                            columns = StaggeredGridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            state = gridState,
                            horizontalArrangement = Arrangement.spacedBy(0.dp),
                            content = {
                                if (allDrawings != null) {
                                    items(allDrawings!!.size) {
                                        ListItem(allDrawings!![it])
                                    }
                                }
                            }

                        )
                    }
                }
            }
            return view
        }
    }

    /**
     * This composable function defines how a drawing in the list should be displayed on the view.
     * @param data      - Drawing to be displayed
     * @param modifier  - Object used to define design of the UI elements
     */
    @Composable
    fun ListItem(data: Drawing, modifier: Modifier = Modifier) {
        val drawingImage = vm.getImageFromFilename(data.fileName, context).copy(Bitmap.Config.ARGB_8888, true)
        Row(
            modifier
                .wrapContentWidth()
                .padding(horizontal = Dp(1f), vertical = Dp(3f))
        ) {
            OutlinedButton(onClick = {
                Log.d("NAV", "Navigating from the gallery to drawing ${data.fileName}")
                vm.currentFileName = data.fileName
                vm.setBitmapImage(drawingImage)
                findNavController().navigate(R.id.action_artGalleryScreen_to_drawScreen2)
            },      shape = RoundedCornerShape(0),
                    border = BorderStroke(0.dp, Color.White),
                    modifier = Modifier.padding(0.dp).wrapContentSize(),
                    content= {
                Card(
                    modifier.padding(all = Dp(5f)),
                    border = BorderStroke(width = Dp(1f), color = Color.Gray)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(modifier.padding(4.dp))
                        Text(
                            text = data.fileName,
                            fontSize = TextUnit(value = 21f, type = TextUnitType.Sp)
                        )
                        Spacer(modifier.padding(4.dp))
                        Image(
                            drawingImage.asImageBitmap(),
                            "Default Description",
                            modifier = modifier
                                .background(color = Color.White)
                                .height(Dp(120f))
                                .width(Dp(120f))
                        )
                        Spacer(modifier.padding(6.dp))
                    } // End column
                } // End card
            }) // End button
        }// End Row
    }// End List Item
}// End screen class