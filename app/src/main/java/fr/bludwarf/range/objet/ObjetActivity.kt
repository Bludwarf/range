package fr.bludwarf.range.objet

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import fr.bludwarf.range.R

class ObjetActivity : AppCompatActivity() {

    private lateinit var editWordView: EditText
    private lateinit var objetViewModel: ObjetViewModel

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_objet)
        editWordView = findViewById(R.id.edit_word)
        val button = findViewById<Button>(R.id.button_save)

        objetViewModel = ViewModelProvider(this).get(ObjetViewModel::class.java)
        objetViewModel.objet.observe(this, Observer { objet ->
            Toast.makeText(this@ObjetActivity, "Objet courant : ${objet.nom}", Toast.LENGTH_LONG).show()
            button.text = getString(R.string.objet_modifier)
            editWordView.setText(objet.nom)
        })

        if (intent.hasExtra(ID_OBJET_MODIFIE)) {
            val id = intent.getIntExtra(ID_OBJET_MODIFIE, 0)
            objetViewModel.load(id)
        }

        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editWordView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val word = editWordView.text.toString()
                replyIntent.putExtra(NOM_NOUVEL_OBJET, word)
                setResult(Activity.RESULT_OK, replyIntent)

                // FIXME comment récupérer l'objet courant ?
                if (intent.hasExtra(ID_OBJET_MODIFIE)) {
                    val id = intent.getIntExtra(ID_OBJET_MODIFIE, 0)
                    objetViewModel.modifier(Objet(id, editWordView.text.toString()))
                }
            }
            finish()
        }
    }

    companion object {
        const val NOM_NOUVEL_OBJET = "fr.bludwarf.range.objet.NOM_NOUVEL_OBJET"
        const val ID_OBJET_MODIFIE = "fr.bludwarf.range.objet.ID_OBJET_MODIFIE"
    }
}
