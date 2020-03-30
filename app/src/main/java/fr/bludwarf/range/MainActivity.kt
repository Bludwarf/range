package fr.bludwarf.range

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.bludwarf.range.objet.Objet
import fr.bludwarf.range.objet.ObjetActivity
import fr.bludwarf.range.objets.ObjetsAdapter
import fr.bludwarf.range.objets.ObjetsViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var objetsViewModel: ObjetsViewModel
    private val nouvelObjetActivityRequestCode = 1
    private val modifierObjetActivityRequestCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#10
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val editerObjet: (Objet) -> Unit = {
            val intent = Intent(this, ObjetActivity::class.java)
            intent.putExtra(ObjetActivity.ID_OBJET_MODIFIE, it.id)
            startActivityForResult(intent, modifierObjetActivityRequestCode)
        }
        val adapter = ObjetsAdapter(this, editerObjet)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#13
        objetsViewModel = ViewModelProvider(this).get(ObjetsViewModel::class.java)
        objetsViewModel.tout.observe(this, Observer { objets ->
            // Update the cached copy of the words in the adapter.
            objets?.let { adapter.setObjets(it) }
        })

        fab.setOnClickListener { view ->
            val intent = Intent(this@MainActivity, ObjetActivity::class.java)
            startActivityForResult(intent, nouvelObjetActivityRequestCode)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        // https://developer.android.com/guide/topics/search/search-dialog#ReceivingTheQuery
        // Get the SearchView and set the searchable configuration
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false) // Do not iconify the widget; expand it by default
        }

        // https://www.youtube.com/watch?v=9OWmnYPX1uc
//        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                Toast.makeText(this@MainActivity, "onQueryTextSubmit", Toast.LENGTH_LONG).show()
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                Toast.makeText(this@MainActivity, "onQueryTextChange", Toast.LENGTH_LONG).show()
//                return true
//            }
//
//        })

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

}
