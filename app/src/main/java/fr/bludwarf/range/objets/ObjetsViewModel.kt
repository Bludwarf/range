package fr.bludwarf.range.objets

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import fr.bludwarf.range.AppDatabase
import fr.bludwarf.range.objet.Objet
import fr.bludwarf.range.objet.ObjetRepository
import kotlinx.coroutines.launch

// https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#8
// Class extends AndroidViewModel and requires application as a parameter.
class ObjetsViewModel(application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: ObjetRepository
    // LiveData gives us updated words when they change.
    val tout: LiveData<List<Objet>>

    init {
        // Gets reference to ObjetDao from ObjetRoomDatabase to construct
        // the correct ObjetRepository.
        val objetDao = AppDatabase.getDatabase(application, viewModelScope).objetDao()
        repository = ObjetRepository(objetDao)
        tout = repository.tout
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
    fun inserer(objet: Objet) = viewModelScope.launch {
        repository.inserer(objet)
    }

    fun modifier(objet: Objet) = viewModelScope.launch {
        repository.modifier(objet)
    }
}