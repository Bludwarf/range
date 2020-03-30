package fr.bludwarf.range.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import fr.bludwarf.range.AppDatabase
import fr.bludwarf.range.objet.Objet
import fr.bludwarf.range.objet.ObjetRepository
import kotlinx.coroutines.launch

// https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#8
// Class extends AndroidViewModel and requires application as a parameter.
class SearchResultsViewModel(application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository =
        ObjetRepository(AppDatabase.getDatabase(application, viewModelScope).objetDao())

    // https://stackoverflow.com/q/60179464/1655155
    val resultats = MutableLiveData<List<Objet>>()

    fun rechercher(nom: String) {
        viewModelScope.launch {
            val objets = repository.rechercher(nom)
            resultats.postValue(objets)
        }
    }
}