package ai.notboring.krishiville


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*


class MainActivity : AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        ProductFragment.OnListFragmentInteractionListener {

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

        val auth = FirebaseAuth.getInstance()
        val navHead = nav_view.getHeaderView(0)

        navHead.userName.text = auth.currentUser!!.displayName
        navHead.userEmail.text = auth.currentUser!!.email

        Glide
                .with(this)
                .load(auth.currentUser!!.photoUrl)
                .into(navHead.userImage)

        launchBuyerFragment()
    }

    override fun onListFragmentInteraction(item: ProductItem) {
        Log.i(mTAG, "Clicked $item")
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
                launchBuyerFragment()
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

    private fun launchBuyerFragment() {
        val fragment = ProductFragment.newInstance()
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.fragment_container, fragment).commit()
    }

    private fun launchSellerFragment() {
        TODO("Create seller fragment!")
    }
}
