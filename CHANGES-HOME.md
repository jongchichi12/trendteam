# 홈(Home) 변경사항

## 파일
- `ui/feature/home/HomeScreen.kt`: 피드 UI, 탭, 하단 네비, 좋아요 토글 버튼(아이콘 색상 토글, 비어있을 때 안내 카드).
- `ui/feature/home/HomeViewModel.kt`: HomeUiState(posts/로딩/에러/likedPostIds), posts Flow 수집, onLikeToggle 처리.
  - `onLikeToggle(postId)`: 이미 좋아요면 UnlikePost, 아니면 LikePost 후 likedPostIds 갱신(세션 한정).
- 네비 연동: `navigation/AppNav.kt`에서 Home에 마이페이지/공감피드/글쓰기 콜백 주입.

## 주요 로직
- Room posts Flow를 수집해 리스트 표시, 비었을 때 안내 카드.
- 좋아요 토글: `onLikeToggle(postId)` 호출 → Like/Unlike usecase → likedPostIds로 UI 토글(세션 한정).
- 하단 네비: 마이페이지/공감 피드/게시물 작성/뒤로가기 버튼.

## TODO
- 댓글 액션/상세 보기, 로그인 사용자 기반 좋아요 영구 저장, 탭별 실제 콘텐츠. 
