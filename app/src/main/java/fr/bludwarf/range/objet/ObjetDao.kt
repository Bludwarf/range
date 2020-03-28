package fr.bludwarf.range.objet

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ObjetDao {
    @Query("SELECT * FROM objet")
    fun tout(): LiveData<List<Objet>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun inserer(objet: Objet)
    @Query("DELETE FROM objet")
    suspend fun toutSupprimer()
    @Update
    suspend fun modifier(objet: Objet)
    @Query("SELECT * FROM objet WHERE id = :id")
    suspend fun get(id: Int): Objet
}