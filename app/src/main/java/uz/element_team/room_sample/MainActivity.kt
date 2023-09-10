package uz.element_team.room_sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import dagger.hilt.android.AndroidEntryPoint
import uz.element_team.models.local.user.UserEntity
import uz.element_team.room_sample.ui.UsersViewModel
import uz.element_team.room_sample.ui.theme.RoomSampleTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var usersViewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomSampleTheme {
                val users = usersViewModel.users.collectAsLazyPagingItems()
                UsersListContent(users = users)
            }
        }
    }

    @Composable
    private fun UsersListContent(users: LazyPagingItems<UserEntity>) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(count = users.itemCount) { index ->
                val user = users[index]
                user?.let {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(text = it.userName)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = it.userAge.toString())
                    }
                }
            }
        }
    }
}
