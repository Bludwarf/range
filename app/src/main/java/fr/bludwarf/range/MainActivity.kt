package fr.bludwarf.range

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
        val adapter = ObjetsAdapter(this) {
            val intent = Intent(this, ObjetActivity::class.java)
            intent.putExtra(ObjetActivity.ID_OBJET_MODIFIE, it.id)
            startActivityForResult(intent, modifierObjetActivityRequestCode)
        }
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

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                nouvelObjetActivityRequestCode -> data?.getStringExtra(ObjetActivity.NOM_NOUVEL_OBJET)?.let {
                    val objet = Objet(null, it)
                    objetsViewModel.inserer(objet)
                }
//                modifierObjetActivityRequestCode -> data?.getIntExtra(ObjetActivity.ID_OBJET_MODIFIE, 0)?.let {
//                    if (it != 0) {
//                        val objet = objetsViewModel.get(it)
//                        objetsViewModel.modifier(objet)
//                    }
//                }
            }
        }
    }
}
