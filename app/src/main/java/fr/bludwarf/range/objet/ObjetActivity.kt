package fr.bludwarf.range.objet

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import fr.bludwarf.range.R

class ObjetActivity : AppCompatActivity() {

    private lateinit var nomSaisi: EditText
    private lateinit var objetViewModel: ObjetViewModel

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_objet)
        nomSaisi = findViewById(R.id.nom)
        val button = findViewById<Button>(R.id.button_save)

        objetViewModel = ViewModelProvider(this).get(ObjetViewModel::class.java)
        objetViewModel.objet.observe(this, Observer { objet ->
            button.text = getString(R.string.objet_modifier)
            nomSaisi.setText(objet.nom)
        })

        val id = if (intent.hasExtra(ID_OBJET_MODIFIE)) intent.getIntExtra(ID_OBJET_MODIFIE, 0) else null
        if (id != null) {
            objetViewModel.load(id)
        }

        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(nomSaisi.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val nom = nomSaisi.text.toString()

                // TODO doit-on recréer un nouvel objet ou y a-t-il une autre méthode ?
                val objet = Objet(id, nom)
                if (id != null) {
                    objetViewModel.modifier(objet)
                } else {
                    objetViewModel.inserer(objet)
                }

                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val ID_OBJET_MODIFIE = "fr.bludwarf.range.objet.ID_OBJET_MODIFIE"
    }
}
