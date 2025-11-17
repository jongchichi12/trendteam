# 게시물(Post) 변경사항

## 디렉토리·파일
- DB:  
  - `data/local/db/PostEntity.kt`, `PostDao.kt`  
    - `observeAll()`: createdAt DESC로 모든 게시글 Flow 제공  
    - `insert()`: 단건 저장(REPLACE)  
    - `clear()`: 전체 삭제(테스트/리셋용)  
    - `incrementLikes()/decrementLikes()`: 좋아요 수 +1 / -1(0 미만 방지)  
  - `data/auth/local/db/AppDatabase.kt` (버전 3, postDao 포함)
- DI: `di/CommonModule.kt`(postDao 제공), `di/PostModule.kt`(PostRepository 바인딩)
- 도메인/데이터: `domain/post/Post.kt`, `domain/post/PostRepository.kt`, `data/post/PostMapper.kt`, `data/post/LocalPostRepositoryImpl.kt`
- 유즈케이스: `domain/post/usecase/{ObservePosts,CreatePost,LikePost,UnlikePost}.kt`
- UI/VM: `ui/feature/post/WritePostViewModel.kt`, `ui/feature/post/WritePostScreen.kt`(멀티라인 입력→게시→popBackStack)
- 네비: `navigation/AppNav.kt` (Routes.WRITE_POST, Home 하단 네비 → 글쓰기 composable 등록)

## 기능
- 게시글 저장/조회: Room posts 테이블, 최신순 Flow.
- 좋아요 토글: like/unlike(usecase) → HomeViewModel에서 상태 반영.
- 글쓰기: 입력 → CreatePost → Room 저장 → 홈으로 돌아가기.

## TODO
- 로그인 사용자 정보로 authorId/authorName 채우기.
- 이미지 업로드/표시(imageUri), 댓글 기능, 사용자 단위로 좋아요/댓글 영구 저장. 
