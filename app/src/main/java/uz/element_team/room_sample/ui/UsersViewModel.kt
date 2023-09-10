package uz.element_team.room_sample.ui

import androidx.paging.Pager
import androidx.paging.PagingConfig
import uz.element_team.local.dao.UserDao
import uz.element_team.local.repository.UserDataSource
import javax.inject.Inject


class UsersViewModel @Inject constructor(
    private val userDao: UserDao
) {
    val users = Pager(config = PagingConfig(pageSize = 15)) {
        UserDataSource(userDao)
    }.flow
}