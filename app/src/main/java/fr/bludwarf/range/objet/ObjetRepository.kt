package fr.bludwarf.range.objet

import androidx.lifecycle.LiveData

// https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#7
class ObjetRepository(private val objetDao: ObjetDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val tout: LiveData<List<Objet>> = objetDao.tout()

    suspend fun inserer(objet: Objet) {
        objetDao.inserer(objet)
    }
}