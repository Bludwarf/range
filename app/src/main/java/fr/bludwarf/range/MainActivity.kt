package fr.bludwarf.range

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.bludwarf.range.objet.Objet
import fr.bludwarf.range.objet.ObjetListAdapter
import fr.bludwarf.range.objet.ObjetViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var objetViewModel: ObjetViewModel
    private val nouvelObjetActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#10
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ObjetListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#13
        objetViewModel = ViewModelProvider(this).get(ObjetViewModel::class.java)
        objetViewModel.tout.observe(this, Observer { objets ->
            // Update the cached copy of the words in the adapter.
            objets?.let { adapter.setObjets(it) }
        })

        fab.setOnClickListener { view ->
            val intent = Intent(this@MainActivity, CreationObjetActivity::class.java)
            startActivityForResult(intent, nouvelObjetActivityRequestCode)
        }
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
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    // https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#13
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == nouvelObjetActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(CreationObjetActivity.EXTRA_REPLY)?.let {
                val objet = Objet(0, it) // FIXME original errored code : Objet(it)
                objetViewModel.insert(objet)
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Nom vide",
                Toast.LENGTH_LONG).show()
        }
    }
}
