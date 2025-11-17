# 프로젝트 디렉터리 구조 요약

간단한 기준으로, 실제 코드가 들어있는 경로만 정리했습니다(빌드 산출물 제외).

## 소스 세트
- `app/src/main` : 기본 앱 소스
- `app/src/local` : 로컬/모의 백엔드 용 flavor
- `app/src/firebase` : Firebase 백엔드 용 flavor
- `app/src/androidTest`, `app/src/test` : 계측/로컬 테스트

## 주요 패키지 (`app/src/main/java/com/example/androidlab`)
```
- core/                 # 공용 타입(AppResult 등)과 상수
- data/                 # 데이터 계층 (API/로컬/매퍼/레포 구현)
  - remote/juso/        # 주소(Juso) API 모델/서비스
  - auth/               # 인증 데이터 소스 (local/remote/mapper/model/repository)
  - repository/         # 기타 레포 구현 위치
- domain/               # 도메인 엔티티/레포 인터페이스/유즈케이스
  - auth/
- di/                   # Hilt 모듈
- navigation/           # NavHost(graph) 정의
- ui/                   # UI 레이어
  - components/         # 공용 컴포넌트(예: PasswordField)
  - feature/address/    # 주소 검색 화면
  - feature/auth/       # 로그인/회원가입 화면
  - feature/home/       # 홈 화면
  - theme/              # Compose 테마 리소스
```

## flavor 전용 소스 (`app/src/local/java`, `app/src/firebase/java`)
- 백엔드 구현을 flavor별로 분리할 수 있도록 패키지 루트만 준비되어 있습니다. 필요 시 각 flavor 폴더 안에 `com/example/androidlab/...` 경로로 실제 구현을 넣습니다.

## 참고
- `build.gradle.kts`의 productFlavors(`local`, `firebase`)와 위 소스 세트가 매칭되어 동작합니다.
- 네비게이션 진입점은 `navigation/AppNav.kt` 하나이며, 화면 추가 시 해당 파일에 route만 늘리면 됩니다.
