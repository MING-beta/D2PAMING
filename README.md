# 🔥 D2 TRADER — 디아블로2 리저렉션 아이템 거래소

> 디아블로2 리저렉션(D2R) 플레이어를 위한 아이템 거래 플랫폼입니다.  
> 공포의 영역(Terror Zone) 실시간 정보와 아이템 판매/구매 게시판을 통합 제공합니다.

---

## 🚀 주요 기능

| 기능 | 설명 |
|------|------|
| 🛒 **아이템 마켓** | 서버/분류별 아이템 판매 게시판 |
| 🔥 **공포의 영역** | 현재/다음 Terror Zone 실시간 표시 + 변경까지 카운트다운 |
| 👤 **회원 인증** | 회원가입 / 로그인 / 로그아웃 |
| 🌙 **다크/라이트 모드** | 테마 전환 (localStorage 유지) |
| 📱 **반응형 디자인** | 모바일/태블릿/데스크탑 지원 |

---

## 🛠 기술 스택

- **Backend**: Java 17, Spring Boot 3.5, Spring Security, Spring Data JPA
- **Templating**: Thymeleaf + thymeleaf-extras-springsecurity6
- **Database**: H2 (In-Memory)
- **Build**: Gradle
- **Frontend**: Vanilla HTML/CSS/JS (Pretendard, Cinzel 폰트, Glassmorphism UI)

---

## ⚙️ 로컬 실행 방법

```bash
# 프로젝트 클론
git clone https://github.com/MING-beta/D2PAMING.git
cd D2PAMING

# 서버 실행 (Windows)
.\gradlew.bat bootRun

# 서버 실행 (Mac/Linux)
./gradlew bootRun
```

브라우저에서 `http://localhost:8080` 접속

---

## 📁 프로젝트 구조

```
src/
├── main/
│   ├── java/com/example/board/
│   │   ├── config/         # Security 설정
│   │   ├── controller/     # Web & API 컨트롤러
│   │   ├── domain/         # JPA 엔티티
│   │   ├── dto/            # 폼 DTO
│   │   ├── repository/     # JPA Repository
│   │   └── service/        # 비즈니스 로직
│   └── resources/
│       ├── templates/      # Thymeleaf HTML 템플릿
│       ├── static/css/     # 스타일시트
│       ├── data.sql        # 초기 데이터
│       └── application.properties
```

---

## 📌 커밋 컨벤션

모든 커밋은 아래 형식을 따릅니다:

```
[DOPAMING] <타입>: <설명>
```

| 타입 | 설명 |
|------|------|
| `feat` | 새로운 기능 추가 |
| `fix` | 버그 수정 |
| `docs` | 문서 수정 |
| `style` | 코드 포맷, UI 스타일 변경 |
| `refactor` | 코드 리팩토링 |
| `chore` | 빌드, 설정 변경 |

---

## 🤝 기여 방법

1. Fork 후 feature 브랜치 생성 (`git checkout -b feat/기능명`)
2. 변경사항 커밋 (`[DOPAMING] feat: 기능 설명`)
3. Pull Request 생성

---

© 2026 D2 TRADER — Created for Diablo II Resurrected Traders
