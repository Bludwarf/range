package fr.bludwarf.range.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.bludwarf.range.R
import fr.bludwarf.range.objet.Objet


// https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#10
class SearchResultAdapter internal constructor(
    context: Context,
    private val onClick: (Objet) -> Unit
) : RecyclerView.Adapter<SearchResultViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private var objets = emptyList<Objet>() // Cached copy of objets

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val itemView = inflater.inflate(R.layout.search_result, parent, false)
        return SearchResultViewHolder(itemView, onClick)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val objetCourant = objets[position]
        holder.objet = objetCourant
    }

    internal fun setObjets(objets: List<Objet>) {
        this.objets = objets
        notifyDataSetChanged()
    }

    override fun getItemCount() = objets.size
}