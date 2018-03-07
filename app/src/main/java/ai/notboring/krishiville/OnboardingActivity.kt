package ai.notboring.krishiville

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import android.support.constraint.ConstraintLayout
import android.support.design.widget.Snackbar


class OnboardingActivity : AppCompatActivity() {

    private val TAG = "ai.nb.kv"
    // Choose an arbitrary request code value
    private val RC_SIGN_IN = 42

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            // already signed in

            Log.i(TAG, "Logged in already")

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        else {
            // not signed in

            val phoneConfigWithDefaultNumber = AuthUI.IdpConfig.PhoneBuilder()
                    .setDefaultCountryIso("in")
                    .build()

            val googleProvider = AuthUI.IdpConfig.GoogleBuilder().build()

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(Arrays.asList(
                                    phoneConfigWithDefaultNumber,
                                    googleProvider))
                            .setIsSmartLockEnabled(!BuildConfig.DEBUG /* credentials */, true)
                            .setTheme(R.style.AppTheme)
                            .setLogo(R.drawable.not_boring)
                            .build(),
                    RC_SIGN_IN)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            // Successfully signed in
            if (resultCode == Activity.RESULT_OK) {
                Log.i(TAG, "Logged in now")

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    showSnackbar(R.string.sign_in_cancelled)
                    return
                }

                if (response.error!!.errorCode == ErrorCodes.NO_NETWORK) {
                    showSnackbar(R.string.no_internet_connection)
                    return
                }

                showSnackbar(R.string.unknown_error)
                Log.e(TAG, "Sign-in error: ", response.error)
            }
        }
    }

    private fun showSnackbar(message: Int) {
        Snackbar.make(findViewById<ConstraintLayout>(R.id.onboarder), message, Snackbar.LENGTH_LONG)
                .setAction("Dismiss", null).show()
    }

}
