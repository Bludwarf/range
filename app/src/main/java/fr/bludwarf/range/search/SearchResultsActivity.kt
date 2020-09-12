package fr.bludwarf.range.search

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.bludwarf.range.R

class SearchResultsActivity : AppCompatActivity() {

    private lateinit var searchResultsViewModel: SearchResultsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {

        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)

            // https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#10
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
            val adapter = SearchResultsAdapter(this)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)

            //https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#13
            searchResultsViewModel = ViewModelProvider(this).get(SearchResultsViewModel::class.java)
            searchResultsViewModel.resultats.observe(this, Observer { objets ->
                // Update the cached copy of the words in the adapter.
                objets?.let { adapter.setObjets(it) }
            })
            searchResultsViewModel.rechercher(query)
        }
    }
}
