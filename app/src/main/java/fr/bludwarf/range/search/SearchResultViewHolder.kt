package fr.bludwarf.range.search

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.bludwarf.range.R
import fr.bludwarf.range.objet.Objet

class SearchResultViewHolder(
    itemView: View,
    private val onClick: (Objet) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    private val nomObjetView: TextView = itemView.findViewById(R.id.nom)
    var objet: Objet? = null
        set(objet) {
            field = objet

            if (objet != null) {
                nomObjetView.text = objet.nom
            } else {
                nomObjetView.text = ""
            }
        }

    init {
        itemView.setOnClickListener {
            objet?.let {
                onClick(it)
            }
        }
    }
}