package uz.element_team.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.element_team.models.local.user.UserEntity

@Dao
abstract class UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertUser(userEntity: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertUsers(list: List<UserEntity>)

    @Query("select * from users limit :size offset :skipSize")
    abstract fun getUserByPage(
        size: Int, skipSize: Int
    ): List<UserEntity>
}