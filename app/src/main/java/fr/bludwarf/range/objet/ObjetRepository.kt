package fr.bludwarf.range.objet

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

// https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#7
class ObjetRepository(private val objetDao: ObjetDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val tout: LiveData<List<Objet>> = objetDao.tout()

    @WorkerThread
    suspend fun inserer(objet: Objet) {
        objetDao.inserer(objet)
    }

    @WorkerThread
    suspend fun modifier(objet: Objet) {
        objetDao.modifier(objet)
    }

    @WorkerThread
    suspend fun get(id: Int): Objet {
        return objetDao.get(id)
    }
}