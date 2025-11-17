# 인증/로그인(Auth) 변경사항

## 파일
- `navigation/AppNav.kt`: 라우트 `login`, `signUp`, `home` 연결. SignUp 성공 시 로그인 화면으로 돌아가고, 로그인 성공 시 홈으로 이동(popUpTo로 스택 정리).
- 로그인 UI/VM: `ui/feature/auth/login/*` (기존 코드), 회원가입 UI/VM: `ui/feature/auth/signup/*`.
  - 로그인/회원가입 화면은 Compose UI + ViewModel 형태(기존 구현 유지).

## 현재 동작
- 로그인 성공 → 홈으로 이동, 로그인 화면 스택 제거.
- 회원가입 완료 → 로그인 화면으로 이동.

## TODO
- 로그인 유저 정보(Home/글쓰기/프로필) 연동, 비밀번호 찾기/로그아웃/프로필 편집 등의 액션 추가 시 이 문서에 기록.
