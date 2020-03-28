package fr.bludwarf.range.objet

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ObjetDao {
    @Query("SELECT * FROM objet")
    fun tout(): LiveData<List<Objet>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun inserer(objet: Objet)
    @Query("DELETE FROM objet")
    suspend fun toutSupprimer()
}