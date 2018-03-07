package ai.notboring.krishiville

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*
import com.firebase.ui.auth.ErrorCodes
import android.app.Activity
import com.firebase.ui.auth.IdpResponse
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val mTAG = "ai.nb.kv"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {

            R.id.buy -> {
                Log.i(mTAG, "buy")
            }

            R.id.sell -> {
                Log.i(mTAG, "sell")
            }

            R.id.about -> {
                Log.i(mTAG, "about")
            }

            R.id.contact -> {
                Log.i(mTAG, "contact")
            }

            R.id.settings -> {
                Log.i(mTAG, "settings")
            }

            R.id.logout -> {
                Log.i(mTAG, "logout")

                AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener {
                        // user is now signed out
                        startActivity(Intent(this@MainActivity, OnboardingActivity::class.java))
                        finish()
                    }

            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
