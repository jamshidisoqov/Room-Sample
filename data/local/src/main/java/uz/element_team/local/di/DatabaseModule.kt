package uz.element_team.local.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.element_team.local.AppDatabase
import uz.element_team.local.dao.UserDao
import uz.element_team.models.local.user.UserEntity
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @OptIn(DelicateCoroutinesApi::class)
    @[Provides Singleton]
    fun provideDatabase(@ApplicationContext ctx: Context): AppDatabase =
        Room.databaseBuilder(ctx, AppDatabase::class.java, "users.db")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    val roomDatabase = provideDatabase(ctx)
                    val userDao = roomDatabase.getUseDao()
                    GlobalScope.launch(Dispatchers.IO) {
                        loadUsers().onEach {
                            Log.e("load data", "$it")
                            userDao.insertUsers(it)
                        }.launchIn(this)
                    }
                }

                fun loadUsers(): Flow<List<UserEntity>> = flow<List<UserEntity>> {
                    var i = 1
                    while (i != 30_000) {
                        val users = arrayListOf<UserEntity>()
                        repeat(15) {
                            users.add(UserEntity(i, "User $i", userAge = i))
                            i++
                        }
                        emit(users)
                        delay(2_000)
                    }
                }.flowOn(Dispatchers.IO)

            }).build()

    @[Provides Singleton]
    fun provideDao(appDatabase: AppDatabase): UserDao = appDatabase.getUseDao()


}