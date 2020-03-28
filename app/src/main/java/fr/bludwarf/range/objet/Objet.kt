package fr.bludwarf.range.objet

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Objet(
    @PrimaryKey(autoGenerate = true) val id: Int,
    /*@ColumnInfo*/ val nom: String? // TODO Annotation superflue ?
)