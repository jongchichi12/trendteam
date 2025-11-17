# MAUM 기능 변경 요약

최근 작업에서 추가/수정된 주요 파일과 역할을 정리했습니다.

## 데이터/DB
- `data/local/db/PostEntity.kt`: Room posts 테이블 엔티티 추가(id, authorId, authorName, content, imageUri?, likesCount, commentsCount, createdAt).
- `data/local/db/PostDao.kt`: posts 조회/삽입 DAO. `observeAll()`로 최신순 Flow 제공.
- `data/auth/local/db/AppDatabase.kt`: DB 버전 3으로 올리고 PostEntity 포함, `postDao()` 추가.
- `di/CommonModule.kt`: Hilt 제공자에 `postDao()` 추가.

## 도메인/리포/유즈케이스
- `domain/post/Post.kt`: 도메인 모델.
- `data/post/PostMapper.kt`: Entity ↔ Domain 매핑.
- `data/post/LocalPostRepositoryImpl.kt`: PostRepository 로컬 구현(DAO Flow 연동, insert).
- `domain/post/PostRepository.kt`: 인터페이스 정의.
- `domain/post/usecase/ObservePosts.kt`, `CreatePost.kt`: 게시물 읽기/작성 유즈케이스.
- `di/PostModule.kt`: PostRepository Hilt 바인딩.

## 뷰모델
- `ui/feature/home/HomeViewModel.kt`: posts Flow 수집해 HomeUiState(posts/로딩/에러) 제공.
- `ui/feature/post/WritePostViewModel.kt`: 글쓰기 상태(content/로딩/에러) 관리, CreatePost 호출.

## UI
- `ui/feature/home/HomeScreen.kt`: HomeViewModel 상태 구독하여 피드 표시, 빈 상태 카드 추가. 하단 네비 메뉴 텍스트/아이콘 정리.
- `ui/feature/post/WritePostScreen.kt`: 글쓰기 화면(TopAppBar+멀티라인 입력+게시 버튼). ExperimentalMaterial3 Opt-in 반영.
- `ui/feature/profile/MyPageScreen.kt`: 마이페이지 플레이스홀더 화면.
- `ui/feature/feed/FavoriteFeedScreen.kt`: 공감 피드 플레이스홀더 화면.

## 네비게이션
- `navigation/AppNav.kt`: 라우트 확장(`writePost`, `myPage`, `favoriteFeed`). 홈 하단 네비 콜백 연결(마이페이지/공감 피드/게시물 작성). WritePost/MyPage/FavoriteFeed 컴포저블 추가.

## 동작 개요
- 홈: Room posts Flow → 카드형 리스트 표시. 비어 있으면 안내 카드.
- 글쓰기: 내용 입력 후 게시 → Room에 저장 → 홈으로 복귀.
- 하단 네비: 마이페이지/공감 피드/게시물 작성/뒤로가기 이동.

## 남은 TODO
- 공감/댓글 액션 로직, 로그인 유저 정보 연동(authorId/authorName), 탭별 실제 콘텐츠, 마이페이지/공감 피드 UI 채우기.
