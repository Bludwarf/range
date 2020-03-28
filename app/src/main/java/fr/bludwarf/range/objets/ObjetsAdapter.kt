package fr.bludwarf.range.objets

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.bludwarf.range.R
import fr.bludwarf.range.objet.Objet


// https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#10
class ObjetsAdapter internal constructor(
    context: Context,
    private val editerObjet: (Objet) -> Unit
) : RecyclerView.Adapter<ObjetsAdapter.ObjetViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private var objets = emptyList<Objet>() // Cached copy of objets

    inner class ObjetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val objetItemView: TextView = itemView.findViewById(R.id.textView)
        val imageButton: ImageButton = itemView.findViewById(R.id.imageButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObjetViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return ObjetViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ObjetViewHolder, position: Int) {
        val objetCourant = objets[position]
        holder.objetItemView.text = objetCourant.nom
        holder.imageButton.setOnClickListener {
            editerObjet(objetCourant)
        }
    }

    internal fun setObjets(objets: List<Objet>) {
        this.objets = objets
        notifyDataSetChanged()
    }

    override fun getItemCount() = objets.size
}