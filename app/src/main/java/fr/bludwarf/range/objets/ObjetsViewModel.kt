package fr.bludwarf.range.objets

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import fr.bludwarf.range.AppDatabase
import fr.bludwarf.range.objet.Objet
import fr.bludwarf.range.objet.ObjetRepository

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
}