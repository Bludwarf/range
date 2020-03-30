package fr.bludwarf.range.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.bludwarf.range.R
import fr.bludwarf.range.objet.Objet


// https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#10
class SearchResultsAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<SearchResultsAdapter.ObjetViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private var objets = emptyList<Objet>() // Cached copy of objets

    inner class ObjetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomObjetView: TextView = itemView.findViewById(R.id.nom)
        val itemLayout: LinearLayout = itemView.findViewById(R.id.itemLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObjetViewHolder {
        val itemView = inflater.inflate(R.layout.search_result, parent, false)
        return ObjetViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ObjetViewHolder, position: Int) {
        val objetCourant = objets[position]
        holder.nomObjetView.text = objetCourant.nom
    }

    internal fun setObjets(objets: List<Objet>) {
        this.objets = objets
        notifyDataSetChanged()
    }

    override fun getItemCount() = objets.size
}