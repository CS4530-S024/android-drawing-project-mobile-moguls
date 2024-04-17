package com.example.drawingapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import java.util.Date

class CloudSavingScreen : Fragment() {
    private lateinit var auth: FirebaseAuth
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {

        }
    }
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
                                Spacer(modifier = Modifier.padding(10.dp))

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

                        } else { // User is logged in, show the main content
                            Text("Welcome ${user!!.email} with id: ${user!!.uid}")
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
                            Text("Data string: $dataString")

                            // store a document to a user-private collection in fire-store
                            Button(onClick = {
                                val document = mapOf(
                                    "My uid" to user!!.uid,
                                    "name" to "My name!",
                                    "time" to Date()
                                )
                                db.collection("users/").document(user!!.uid)
                                    .set(document)
                                    .addOnSuccessListener { Log.e("UPLOAD", "SUCCESSFUL!") }
                                    .addOnFailureListener { e -> Log.e("UPLOAD", "FAILED!: $e") }
                            }) {
                                Text("Post data!")
                            }

                            // download and show the image saved in fire-store if possible
                            var downloadedBitmap by remember { mutableStateOf<Bitmap?>(null) }
                            val ref = Firebase.storage.reference
                            val fileRef = ref.child("${user!!.uid}/picture.png")
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
                            if (downloadedBitmap != null) {
                                Image(
                                    bitmap = downloadedBitmap!!.asImageBitmap(),
                                    "Downloaded image"
                                )
                            }
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
        return view
    }
}