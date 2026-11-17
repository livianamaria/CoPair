package com.example.copair

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalFocusManager
import com.example.copair.ui.auth.AboutYouScreen
import com.example.copair.ui.auth.CreateAccountScreen
import com.example.copair.ui.auth.ForgotPasswordScreen
import com.example.copair.ui.auth.LoginScreen
import com.example.copair.ui.auth.SignInScreen
import com.example.copair.ui.theme.CoPairTheme
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.lifecycleScope
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.google.firebase.firestore.FirebaseFirestore
import com.example.copair.ui.home.HomeScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CoPairTheme {
                var screen by rememberSaveable {
                    mutableStateOf(
                        if (FirebaseAuth.getInstance().currentUser?.isEmailVerified == true) {
                            "home"
                        } else {
                            "welcome"
                        }
                    )
                }
                var registrationNickname by remember { mutableStateOf("") }
                var registrationEmail by remember { mutableStateOf("") }
                var registrationPassword by remember { mutableStateOf("") }

                val focusManager = LocalFocusManager.current

                val goBack: () -> Unit = {
                    focusManager.clearFocus()

                    screen = when (screen) {
                        "forgot" -> "signin"
                        "about" -> "create"
                        else -> "welcome"
                    }
                }

                BackHandler(enabled = screen != "welcome") {
                    goBack()
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        if (screen == "create" || screen == "about") {
                            // Păstrează formularul în memorie.
                            // Când e ascuns, nu îl plasează pe ecran.
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .layout { measurable, constraints ->
                                        val placeable =
                                            measurable.measure(constraints)

                                        layout(
                                            placeable.width,
                                            placeable.height
                                        ) {
                                            if (screen == "create") {
                                                placeable.placeRelative(0, 0)
                                            }
                                        }
                                    }
                            ) {
                                CreateAccountScreen(
                                    onBackClick = goBack,
                                    onContinueClick = { nickname, email, password ->
                                        registrationNickname = nickname
                                        registrationEmail = email
                                        registrationPassword = password

                                        focusManager.clearFocus()
                                        screen = "about"
                                    }
                                )
                            }

                            if (screen == "about") {
                                AboutYouScreen(
                                    onBackClick = goBack,
                                    onComplete = {
                                        focusManager.clearFocus()
                                        screen = "home"
                                    },
                                    registrationNickname = registrationNickname,
                                    registrationEmail = registrationEmail,
                                    registrationPassword = registrationPassword
                                )
                            }
                        } else {
                            when (screen) {
                                "home" -> HomeScreen()
                                "signin" -> SignInScreen(
                                    onBackClick = goBack,
                                    onForgotPasswordClick = {
                                        focusManager.clearFocus()
                                        screen = "forgot"
                                    },

                                    onSignInClick = { email, password ->
                                        val auth = FirebaseAuth.getInstance()

                                        auth.signInWithEmailAndPassword(email, password)
                                            .addOnSuccessListener { result ->
                                                val user = result.user

                                                if (user != null) {
                                                    user.reload()
                                                        .addOnSuccessListener {
                                                            if (!user.isEmailVerified) {
                                                                auth.signOut()

                                                                Toast.makeText(
                                                                    this@MainActivity,
                                                                    "Please verify your email before signing in.",
                                                                    Toast.LENGTH_LONG
                                                                ).show()
                                                            } else {
                                                                FirebaseFirestore.getInstance()
                                                                    .collection("users")
                                                                    .document(user.uid)
                                                                    .get()
                                                                    .addOnSuccessListener { document ->
                                                                        focusManager.clearFocus()

                                                                        screen = if (
                                                                            document.getBoolean("profileCompleted") == true
                                                                        ) {
                                                                            "home"
                                                                        } else {
                                                                            "about"
                                                                        }
                                                                    }
                                                                    .addOnFailureListener { e ->
                                                                        Toast.makeText(
                                                                            this@MainActivity,
                                                                            e.localizedMessage ?: "Profile loading failed.",
                                                                            Toast.LENGTH_LONG
                                                                        ).show()
                                                                    }
                                                            }
                                                        }
                                                        .addOnFailureListener {
                                                            auth.signOut()
                                                            Toast.makeText(
                                                                this@MainActivity,
                                                                "Could not verify email status.",
                                                                Toast.LENGTH_LONG
                                                            ).show()
                                                        }
                                                }
                                            }
                                            .addOnFailureListener {
                                                Toast.makeText(
                                                    this@MainActivity,
                                                    "Invalid email or password.",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                    },

                                    )

                                "forgot" -> ForgotPasswordScreen(
                                    onBackClick = goBack
                                )

                                else -> LoginScreen(
                                    onLoginClick = {
                                        screen = "signin"
                                    },
                                    onCreateAccountClick = {
                                        screen = "create"
                                    },
                                    onGoogleSignInClick = {
                                        signInWithGoogle { isNewUser ->
                                            if (isNewUser) {
                                                screen = "about"
                                            } else {
                                                screen = "home"
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    private fun signInWithGoogle(
        onResult: (Boolean) -> Unit
    ) {
        lifecycleScope.launch {
            try {
                val googleOption =
                    GetSignInWithGoogleOption.Builder(
                        getString(R.string.default_web_client_id)
                    ).build()

                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleOption)
                    .build()

                val credentialManager =
                    CredentialManager.create(this@MainActivity)

                val result = credentialManager.getCredential(
                    context = this@MainActivity,
                    request = request
                )

                val credential = result.credential

                if (
                    credential is CustomCredential &&
                    credential.type ==
                    GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                ) {
                    val googleCredential =
                        GoogleIdTokenCredential.createFrom(
                            credential.data
                        )

                    val firebaseCredential =
                        GoogleAuthProvider.getCredential(
                            googleCredential.idToken,
                            null
                        )

                    FirebaseAuth.getInstance()
                        .signInWithCredential(firebaseCredential)
                        .addOnCompleteListener(this@MainActivity) { task ->

                            if (task.isSuccessful) {

                                val isNewUser =
                                    task.result.additionalUserInfo
                                        ?.isNewUser == true

                                onResult(isNewUser)

                            } else {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Google authentication failed: ${task.exception?.localizedMessage}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Invalid Google credential",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } catch (e: GetCredentialCancellationException) {
                // Utilizatorul a anulat autentificarea
            } catch (e: GetCredentialException) {
                Toast.makeText(
                    this@MainActivity,
                    "Google Sign-In error: ${e.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
            } catch (e: Exception) {
                Toast.makeText(
                    this@MainActivity,
                    "Error: ${e.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}