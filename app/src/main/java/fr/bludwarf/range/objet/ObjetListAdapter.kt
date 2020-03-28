package fr.bludwarf.range.objet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.bludwarf.range.R

// https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#10
class ObjetListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<ObjetListAdapter.ObjetViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private var objets = emptyList<Objet>() // Cached copy of objets

    inner class ObjetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val objetItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObjetViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return ObjetViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ObjetViewHolder, position: Int) {
        val current = objets[position]
        holder.objetItemView.text = current.nom
    }

    internal fun setObjets(objets: List<Objet>) {
        this.objets = objets
        notifyDataSetChanged()
    }

    override fun getItemCount() = objets.size
}