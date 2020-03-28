package fr.bludwarf.range.objet

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Objet::class], version = 1)
abstract class ObjetDatabase : RoomDatabase() {
    abstract fun objetDao(): ObjetDao

//    private class AppDatabaseCallback(
//        private val scope: CoroutineScope
//    ) : RoomDatabase.Callback() {
//
//        override fun onOpen(db: SupportSQLiteDatabase) {
//            super.onOpen(db)
//            INSTANCE?.let { database ->
//                scope.launch {
//                    populateDatabase(database.objetDao())
//                }
//            }
//        }
//
//        suspend fun populateDatabase(objetDao: ObjetDao) {
//            // Delete all content here.
//            objetDao.toutSupprimer()
//
//            // Add sample objets.
//            var objet = Objet(1, "Objet 1")
//            objetDao.inserer(objet)
//            objet = Objet(2, "Objet 2")
//            objetDao.inserer(objet)
//        }
//    }

    // https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#6
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ObjetDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ObjetDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ObjetDatabase::class.java,
                    "objet_database"
                )
//                    .addCallback(AppDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}