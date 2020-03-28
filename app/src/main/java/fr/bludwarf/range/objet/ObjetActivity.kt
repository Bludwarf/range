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

        if (intent.hasExtra(ID_OBJET_MODIFIE)) {
            val id = intent.getIntExtra(ID_OBJET_MODIFIE, 0)
            objetViewModel.load(id)
        }

        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(nomSaisi.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val nom = nomSaisi.text.toString()

                // FIXME comment récupérer l'objet courant ?
                if (intent.hasExtra(ID_OBJET_MODIFIE)) {
                    val id = intent.getIntExtra(ID_OBJET_MODIFIE, 0)
                    objetViewModel.modifier(Objet(id, nom))
                    replyIntent.putExtra(ID_OBJET_MODIFIE, id)
                } else {
                    replyIntent.putExtra(NOM_NOUVEL_OBJET, nom)
                }

                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val NOM_NOUVEL_OBJET = "fr.bludwarf.range.objet.NOM_NOUVEL_OBJET"
        const val ID_OBJET_MODIFIE = "fr.bludwarf.range.objet.ID_OBJET_MODIFIE"
    }
}
