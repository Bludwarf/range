package fr.bludwarf.range.objet

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ObjetDao {
    @Query("SELECT * FROM objet")
    fun tout(): LiveData<List<Objet>>
    @Insert
    suspend fun inserer(objet: Objet)
}