package org.d3if0031.smartspending.data
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Record::class], version = 1)
abstract class RecordDb : RoomDatabase() {
    abstract val recordDao: RecordDAO

    companion object {
        @VisibleForTesting
        const val DATABASE_NAME = "smartspendingdb"
        @Volatile
        private var db: RecordDb? = null
        fun getDb(application: Application): RecordDb? {
            if (db == null) {
                synchronized(RecordDb::class.java) {
                    if (db == null) {
                        db = Room.databaseBuilder(
                            application.applicationContext,
                            RecordDb::class.java, DATABASE_NAME
                        )
                            .build()
                    }
                }
            }

            return db
        }
    }
}