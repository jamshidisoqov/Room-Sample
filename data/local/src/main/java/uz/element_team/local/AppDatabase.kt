package uz.element_team.local

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.element_team.local.dao.UserDao
import uz.element_team.models.local.user.UserEntity

@Database(
    entities = [UserEntity::class], version = 1, exportSchema = true, autoMigrations = []
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUseDao(): UserDao
}
