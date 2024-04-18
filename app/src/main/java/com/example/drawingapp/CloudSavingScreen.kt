package com.example.drawingapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream
import java.util.Date

/**
 * @author          - Christian E. Anderson
 * @teammate(s)     - Crosby White & Matthew Williams
 * @version         - Phase 3 = 19-APR-2024
 *
 *      This file defines the cloud saving screen for the Drawing App.
 *
 *  Phase 3: Add firebase authentication, image cloud storage, and displaying images in cloud storage.
 *s
 */
class CloudSavingScreen : Fragment() {
    private lateinit var auth: FirebaseAuth

    /**
     * Defines what the view will look at creation.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth = Firebase.auth
        val view = inflater.inflate(R.layout.fragment_art_gallery_screen, container, false)
        val composeView = view.findViewById<ComposeView>(R.id.compose_view)
        val vm: MyViewModel by activityViewModels()
        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var user by remember { mutableStateOf(Firebase.auth.currentUser) }

                    Column {
                        if (user == null) { // show the login stuff only if the user hasn't logged in yet
                            Column {
                                Row {
                                    Button(onClick = {
                                        Log.d("NAV", "navigating to draw screen")
                                        findNavController().navigate(R.id.action_cloud_saving_screen_to_drawScreen2)
                                    }
                                    ) {
                                        Text(text = "Back to Local Saving")
                                    }
                                }

                                // UI for inputting username and password
                                var email by remember { mutableStateOf("") }
                                var password by remember { mutableStateOf("") }
                                Text(
                                    text = "User not logged in",
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                    )
                                )
                                OutlinedTextField(
                                    value = email,
                                    onValueChange = { email = it },
                                    label = { Text("Email") })
                                Spacer(modifier = Modifier.padding(5.dp))
                                Text(

                                    text = "Passwords must be 6 or more characters",
                                    style = TextStyle(
                                        fontSize = 12.sp
                                    )
                                )
                                OutlinedTextField(
                                    value = password,
                                    onValueChange = { password = it },
                                    label = { Text("Password") },
                                    visualTransformation = PasswordVisualTransformation()
                                )

                                Row {
                                    Button(onClick = {
                                        Firebase.auth.signInWithEmailAndPassword(email, password)
                                            .addOnCompleteListener(requireActivity()) { task ->
                                                if (task.isSuccessful) {
                                                    user = Firebase.auth.currentUser
                                                } else {
                                                    Toast.makeText(
                                                        requireContext(),
                                                        "login failed, try again",
                                                        Toast.LENGTH_SHORT
                                                    ).show()

                                                }
                                            }
                                    }) {
                                        Text("Login")
                                    }
                                    Button(onClick = {
                                        Firebase.auth.createUserWithEmailAndPassword(
                                            email,
                                            password
                                        )
                                            .addOnCompleteListener(requireActivity()) { task ->
                                                if (task.isSuccessful) {
                                                    user = Firebase.auth.currentUser
                                                } else {
                                                    Toast.makeText(
                                                        requireContext(),
                                                        "Failed to signup, try again",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    Log.e("Create user error", "${task.exception}")
                                                }
                                            }
                                    }) {
                                        Text("Signup")
                                    }
                                }
                            }

                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Login Successful!",
                                Toast.LENGTH_LONG
                            ).show()
                            Log.d("User logged In", "${user!!.email} logged in")
                            Button(onClick = {
                                Log.d("NAV", "navigating to save screen")
                                findNavController().navigate(R.id.action_cloud_saving_screen_to_saveScreen2)
                            }
                            ) {
                                Text("Back")
                            }
                            Spacer(modifier = Modifier.padding(15.dp))

                            Row {
                                Text(
                                    text = "${user!!.email}'s Cloud",
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        textAlign = TextAlign.Center
                                    ),
                                    modifier = Modifier.width(LocalConfiguration.current.screenWidthDp.dp)
                                )
                            }
                            Row {
                                HorizontalDivider(thickness = 2.dp)
                            }
                            var dataString by remember { mutableStateOf("") }

                            // probably bad to do this in a composable
                            // grab a document from a public folder on firebase
                            val db = Firebase.firestore
                            val collection = db.collection("demoCollection")
                            collection
                                .get()
                                .addOnSuccessListener { result ->
                                    val doc = result.first()
                                    dataString = "${doc.id} => ${doc.data}"
                                }
                                .addOnFailureListener { exception ->
                                    Log.w("Uh oh", "Error getting documents.", exception)
                                }
                            Row {
                                Text(vm.currentFileName)
                                Image(
                                    bitmap = vm.bitmap.value!!.asImageBitmap(),
                                    contentDescription = null
                                )
                            }
                            // store a document to a user-private collection in fire-store
                            Button(onClick = {
                                val document = mapOf(
                                    "My uid" to user!!.uid,
                                    "time" to Date(),
                                    "filename" to vm.currentFileName,
                                    "drawing" to vm.getImageFromFilename(
                                        vm.currentFileName,
                                        requireContext()
                                    )
                                )
                                // converts bitmap into png
                                val baos = ByteArrayOutputStream()
                                vm.bitmap.value!!.compress(Bitmap.CompressFormat.PNG, 0, baos)
                                val data = baos.toByteArray() // bytes of the PNG

                                // Uploads png to firestore object storage
                                val reference = Firebase.storage.reference
                                val fileReference =
                                    reference.child("${user!!.uid}/${vm.currentFileName}.png")
                                val uploadTask = fileReference.putBytes(data)
                                uploadTask
                                    .addOnFailureListener { e ->
                                        Log.e("PICUPLOAD", "Failed !$e")
                                    }
                                    .addOnSuccessListener {
                                        Log.e("PICUPLOAD", "Success!")
                                        Toast.makeText(
                                            requireContext(),
                                            "Drawing Uploaded!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                db.collection("users/").document(user!!.uid)
                                    .set(document)
                                    .addOnSuccessListener { Log.e("UPLOAD", "SUCCESSFUL!") }
                                    .addOnFailureListener { e -> Log.e("UPLOAD", "FAILED!: $e") }
                            }) {
                                Text("Upload to Cloud")
                            }

                            Row {
                                // TODO - uncomment when code works
                                //  DisplayCloudDrawings(user!!, vm)
                            }
                            Row {
                                Button(onClick = {
                                    Firebase.auth.signOut()
                                    user = null
                                }) {
                                    Text("Sign out")
                                }
                            }
                        }
                    }
                }
            }
        }
        return view
    }

    /**
     * Defines the lazy gird for displaying images in the firebase storage.
     * Should look similar to the art gallery gird.
     */
    @Composable
    private fun DisplayCloudDrawings(user: FirebaseUser, vm: MyViewModel) {
        // download and show the image saved in fire-store if possible
        var downloadedBitmap by remember { mutableStateOf<Bitmap?>(null) }
        val ref = Firebase.storage.reference
        val fileRef = ref.child("${user.uid}/${vm.currentFileName}.png")
        fileRef.getBytes(10 * 1024 * 1024).addOnSuccessListener { bytes ->
            downloadedBitmap =
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }
            .addOnFailureListener { e ->
                Log.e(
                    "DOWNLOAD_IMAGE",
                    "Failed to get image $e"
                )
            }
            .addOnSuccessListener { e ->
                Log.e(
                    "DOWNLOAD_IMAGE",
                    "Successfully got images"
                )
            }
        if (downloadedBitmap != null) {
            Log.e("DOWNLOAD_IMAGE", "image downloaded")
            Image(
                bitmap = downloadedBitmap!!.asImageBitmap(),
                "Downloaded image"
            )
        }

        if (downloadedBitmap == null) {
            Log.e("DOWNLOAD_IMAGE", "downloaded image is: $downloadedBitmap")
        }
        val cloudDrawingsList = listOf(downloadedBitmap)
        val gridState = rememberLazyStaggeredGridState()
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            state = gridState,
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            content = {
                items(cloudDrawingsList.size) {
                    //  DisplayImage(cloudDrawingsList[it]!!)
                }
            }
        )
    }

    /**
     * Defines how the png images should be displayed in the lazy gird
     */
    @Composable
    fun DisplayImage(image: Bitmap, modifier: Modifier = Modifier) {
        Row(
            modifier
                .wrapContentWidth()
                .padding(horizontal = Dp(1f), vertical = Dp(3f))
        ) {
            OutlinedButton(onClick = {
                // vm.currentFileName = data.fileName
                // vm.setBitmapImage(drawingImage)
                // findNavController().navigate(R.id.action_artGalleryScreen_to_drawScreen2)
            }, shape = RoundedCornerShape(0),
                border = BorderStroke(0.dp, Color.White),
                modifier = Modifier
                    .padding(0.dp)
                    .wrapContentSize(),
                content = {
                    // DisplayCard(modifier, image)
                }) // End button

        }
    }
}

/**
 * Defines how the png images should be displayed in the image container
 */
@Composable
fun DisplayCard(modifier: Modifier = Modifier, drawingImage: Bitmap) {
    Card(
        modifier.padding(all = Dp(5f)),
        border = BorderStroke(width = Dp(1f), color = Color.Gray)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier.padding(4.dp))
            /*Text(
                text = data.fileName,
                fontSize = TextUnit(value = 21f, type = TextUnitType.Sp)
            )*/
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
        }
    }
}