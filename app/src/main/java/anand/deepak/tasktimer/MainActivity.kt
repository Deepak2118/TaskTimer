package anand.deepak.tasktimer

import anand.deepak.tasktimer.databinding.ActivityMainBinding
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.content_main.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), AddEditFragment.OnSaveClicked, MainActivityFragment.OnTaskEdit {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    // Whether the activity is in 2-pane mode
    // i.e. running in landscape or on a tablet.
    private var mTwoPane = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        Log.d(TAG, "Oncreate: Starts")

        mTwoPane = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        var fragment = supportFragmentManager.findFragmentById(R.id.task_details_container)
        if (fragment != null) {
            showEditPane()
        } else {
            task_details_container.visibility = if(mTwoPane) View.INVISIBLE else View.GONE
            mainFragment.view?.visibility = View.VISIBLE
        }

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

    }

    private fun showEditPane() {
        task_details_container.visibility = View.VISIBLE
        //Hide left pane if it is single pane mode

        mainFragment.view?.visibility = if (mTwoPane) View.VISIBLE else View.GONE
    }

    private fun removeEditPane(fragment: Fragment? = null) {
        Log.d(TAG, "removeEditPane: called")
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .remove(fragment)
                .commit()
        }

        // Set the visibility of the right hand pane
        task_details_container.visibility = if (mTwoPane) View.INVISIBLE else View.GONE

        // Show the left hand pane
        mainFragment.view?.visibility = View.VISIBLE

        supportActionBar?.setDisplayHomeAsUpEnabled(false)

    }

    override fun onSaveClicked() {
        Log.d(TAG, "onSaveClicked: called")
        var fragment = supportFragmentManager.findFragmentById(R.id.task_details_container)
        removeEditPane(fragment)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.menumain_addTask -> {
                taskEditRequest(null)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
            android.R.id.home -> {
                Log.d(TAG, "onOptionsItemSelected: home button pressed")
                val fragment = supportFragmentManager.findFragmentById(R.id.task_details_container)
                removeEditPane(fragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun taskEditRequest(task: Task?) {
        Log.d(TAG, "taskEditRequest: Starts")

        // Create a new fragment to edit the task
        val newFragment = AddEditFragment.newInstance(task)
        supportFragmentManager.beginTransaction()
            .replace(R.id.task_details_container, newFragment)
            .commit()
        showEditPane()

        Log.d(TAG, "taskEditRequest: Ends")
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.task_details_container)
        if (fragment == null || mTwoPane) {
            super.onBackPressed()
        } else {
            removeEditPane(fragment)
        }
    }

    override fun onTaskEdit(task: Task) {
        taskEditRequest(task)
    }
}