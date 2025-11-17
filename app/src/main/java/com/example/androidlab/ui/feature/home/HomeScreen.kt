package com.example.androidlab.ui.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.RowScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val BackgroundLavender = Color(0xFFEDE4FF)
private val PrimaryPurple = Color(0xFF7B61FF)

data class HomeFeedItem(
    val id: String,
    val author: String,
    val content: String,
    val likes: Int,
    val comments: Int
)

private val sampleFeed = listOf(
    HomeFeedItem(
        id = "1",
        author = "마음친구",
        content = "오늘 날씨가 좋아서 산책을 다녀왔어요. 같이 걷고 싶은 사람?",
        likes = 12,
        comments = 3
    ),
    HomeFeedItem(
        id = "2",
        author = "함께걷기",
        content = "동아리 활동 사진 공유합니다! 이번 주 모임도 기대돼요 😊",
        likes = 25,
        comments = 10
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onProfile: () -> Unit = {},
    onFeed: () -> Unit = {},
    onWritePost: () -> Unit = {},
    onBack: () -> Unit = {}
    // 이후: 탭별 onClick, 공감/댓글/공유 콜백 확장 가능
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("홈", "동아리 활동", "뿌리 찾기", "상담")

    Scaffold(
        containerColor = BackgroundLavender,
        topBar = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(PrimaryPurple)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "MAUM",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Spacer(Modifier.height(12.dp))
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.Transparent,
                    contentColor = Color.White,
                    indicator = {},
                    divider = {}
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                ) {
                                    if (index == 0) {
                                        Icon(
                                            imageVector = Icons.Default.Home,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(Modifier.width(4.dp))
                                    }
                                    Text(
                                        text = title,
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        fontWeight = if (selectedTab == index) FontWeight.SemiBold else FontWeight.Medium
                                    )
                                }
                            }
                        )
                    }
                }
            }
        },
        bottomBar = {
            BottomNavBar(
                onProfile = onProfile,
                onFeed = onFeed,
                onWritePost = onWritePost,
                onBack = onBack
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(sampleFeed, key = { it.id }) { item ->
                FeedCard(item)
            }
        }
    }
}

@Composable
private fun FeedCard(item: HomeFeedItem) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        color = Color.White
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Placeholder avatar
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(PrimaryPurple.copy(alpha = 0.2f), shape = MaterialTheme.shapes.small)
                )
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(item.author, fontWeight = FontWeight.SemiBold)
                    Text("방금 전", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
                }
            }

            Text(item.content, style = MaterialTheme.typography.bodyMedium)

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { /* TODO: like */ }) {
                    Icon(Icons.Default.Favorite, contentDescription = "공감", tint = PrimaryPurple)
                }
                Text("${item.likes}")
                Spacer(Modifier.width(12.dp))
                IconButton(onClick = { /* TODO: comment */ }) {
                    Icon(Icons.Default.Send, contentDescription = "댓글", tint = PrimaryPurple)
                }
                Text("${item.comments}")
            }
        }
    }
}

@Composable
private fun BottomNavBar(
    onProfile: () -> Unit,
    onFeed: () -> Unit,
    onWritePost: () -> Unit,
    onBack: () -> Unit
) {
    BottomAppBar(
        containerColor = PrimaryPurple,
        contentColor = Color.White,
        tonalElevation = 4.dp
    ) {
        BottomNavItem(icon = Icons.Default.Person, label = "마이페이지", onClick = onProfile)
        BottomNavItem(icon = Icons.Default.Favorite, label = "공감 피드", onClick = onFeed)
        BottomNavItem(icon = Icons.Default.PlusOne, label = "게시물 작성", onClick = onWritePost)
        BottomNavItem(icon = Icons.Default.ArrowBack, label = "돌아가기", onClick = onBack)
    }
}

@Composable
private fun RowScope.BottomNavItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .weight(1f)
            .padding(vertical = 8.dp),
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.White
        ),
        elevation = null
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, contentDescription = label, modifier = Modifier.size(22.dp))
            Text(
                label,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
