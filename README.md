# AndroidLab - 주소 검색 기능 구현 가이드

## 개요
행안부 실시간 주소검색 API를 활용한 주소 검색 기능 구현

## 주요 구현 내용

### 1. API 레이어
- **JusoApi.kt**: Retrofit 인터페이스
  - Base URL: `https://business.juso.go.kr/`
  - Endpoint: `addrlink/addrLinkApi.do`
  - 필수 파라미터: `confmKey`, `keyword`, `resultType=json`
  - 옵션 파라미터: `currentPage`, `countPerPage`

- **JusoModels.kt**: API 응답 DTO 및 도메인 모델
  - `JusoResponseDto`: API 응답 래퍼
  - `SimpleAddr`: 화면에서 사용하는 도메인 모델 (Parcelable)
  - `JusoItemDto.toDomain()`: DTO → 도메인 변환 확장 함수

### 2. 의존성 주입 (Hilt)
- **JusoNetworkModule.kt**
  - OkHttpClient 제공 (디버그 모드에서 로깅)
  - Retrofit 인스턴스 제공 (Moshi 컨버터)
  - JusoApi 구현체 제공

### 3. 데이터 레이어
- **JusoRepository.kt**
  - `search()`: 주소 검색 실행
  - 에러 코드 검증 (errorCode "0"이 정상)
  - DTO를 도메인 모델로 변환

### 4. UI 레이어
- **AddressSearchViewModel.kt**
  - `AddressUiState`: 검색어, 로딩 상태, 결과 리스트, 에러 관리
  - 디바운스 적용 (350ms)
  - `onQueryChange()`: 검색어 변경 시 자동 검색
  - `search()`: 주소 검색 실행

- **AddressSearchScreen.kt**
  - 검색어 입력 필드
  - 검색 버튼
  - 검색 결과 리스트 (LazyColumn)
  - `AddressRow`: 주소 항목 표시 컴포저블

### 5. 네비게이션
- **AppNav.kt**
  - Route: `Routes.ADDR_SEARCH = "addrSearch"`
  - 주소 선택 시 `savedStateHandle`에 결과 저장
  - 이전 화면으로 결과 전달

### 6. 회원가입 화면 연동
- **SignUpScreen.kt**
  - "주소찾기" 버튼 클릭 시 주소 검색 화면으로 이동
  - `savedStateHandle`에서 선택된 주소 수신
  - `vm.setAddress()`로 주소 필드 업데이트

## 설정 요구사항

### 1. BuildConfig 설정
`app/build.gradle.kts`에서 `JUSO_KEY` 설정 필요:
```kotlin
buildConfigField("String", "JUSO_KEY", "\"$jusoKey\"")
```
- `local.properties` 또는 환경변수에서 `JUSO_KEY` 로드

### 2. 플러그인 추가
```kotlin
plugins {
    // ...
    id("kotlin-parcelize")  // SimpleAddr를 Parcelable로 만들기 위해 필요
}
```

### 3. 의존성
- Retrofit 2.11.0
- Moshi Converter
- OkHttp Logging Interceptor
- Navigation Compose
- Hilt

## 사용 방법

### 주소 검색 화면 열기
```kotlin
navController.navigate(Routes.ADDR_SEARCH)
```

### 선택된 주소 받기
```kotlin
val picked by navController.currentBackStackEntry
    ?.savedStateHandle
    ?.getStateFlow<SimpleAddr?>("picked_addr", null)
    ?.collectAsState() ?: remember { mutableStateOf(null) }

LaunchedEffect(picked) {
    picked?.let { addr ->
        // 주소 사용
        vm.setAddress(addr.roadAddr)
        // 핸들 비우기
        navController.currentBackStackEntry?.savedStateHandle?.set("picked_addr", null)
    }
}
```

## 파일 구조
```
app/src/main/java/com/example/androidlab/
├── data/
│   ├── remote/juso/
│   │   ├── JusoApi.kt          # Retrofit API 인터페이스
│   │   └── JusoModels.kt        # DTO 및 도메인 모델
│   └── repository/
│       └── JusoRepository.kt    # 주소 검색 리포지토리
├── di/
│   └── JusoNetworkModule.kt     # Hilt 네트워크 모듈
└── ui/
    └── feature/
        ├── address/
        │   ├── AddressSearchViewModel.kt
        │   └── AddressSearchScreen.kt
        └── auth/signup/
            └── SignUpScreen.kt  # 주소 찾기 버튼 연동
```

## 주요 기능
- ✅ 실시간 주소 검색 (디바운스 적용)
- ✅ 검색 결과 리스트 표시
- ✅ 주소 선택 및 결과 전달
- ✅ 에러 처리
- ✅ 로딩 상태 표시

