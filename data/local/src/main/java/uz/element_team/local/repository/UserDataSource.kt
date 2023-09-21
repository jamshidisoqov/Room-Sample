package uz.element_team.local.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.element_team.local.dao.UserDao
import uz.element_team.models.local.user.UserEntity

class UserDataSource(private val userDao: UserDao) : PagingSource<Int, UserEntity>() {
    override fun getRefreshKey(state: PagingState<Int, UserEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserEntity> {
        return try {
            val page = params.key ?: DEFAULT_PAGE
            withContext(Dispatchers.IO) {
                val users = userDao.getUserByPage(PAGE_SIZE, (page - 1) * PAGE_SIZE)
                Log.d("tttb", "data:$users")
                LoadResult.Page(
                    data = users,
                    prevKey = if (page == DEFAULT_PAGE) null else page - 1,
                    nextKey = if (users.isEmpty()) null else page + 1
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val DEFAULT_PAGE = 1
        const val PAGE_SIZE = 15
    }
}