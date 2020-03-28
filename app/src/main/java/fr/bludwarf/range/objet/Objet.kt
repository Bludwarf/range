package fr.bludwarf.range.objet

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Objet(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val nom: String?
)