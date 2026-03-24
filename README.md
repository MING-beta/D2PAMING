# 🔥 D2PAMING — 디아블로2 리저렉션 커뮤니티 & 아이템 거래소

> 디아블로2 리저렉션(D2R) 플레이어를 위한 **커뮤니티 & 아이템 거래 플랫폼**입니다.  
> 공포의 영역(Terror Zone) 실시간 정보와 아이템 판매/구매 게시판을 통합 제공합니다.

---

## 🚀 주요 기능

| 기능 | 설명 |
|------|------|
| 🛒 **아이템 마켓** | 서버/분류별 아이템 판매 게시판 |
| 🔥 **공포의 영역** | 현재/다음 Terror Zone 실시간 표시 + 변경까지 카운트다운 |
| 💬 **커뮤니티** | 거래 제안 / 문의 댓글 시스템 |
| 💎 **DP (도파밍 포인트)** | D2PAMING 전용 사이트 화폐 단위 |
| 👤 **회원 인증** | 회원가입 / 로그인 / 로그아웃 |
| 🌙 **다크/라이트 모드** | 테마 전환 (localStorage 유지) |
| 📱 **반응형 디자인** | 모바일/태블릿/데스크탑 지원 |

---

## 💎 화폐 단위 (Currency)

| 단위 | 설명 |
|------|------|
| 룬 (RUNE) | 디아블로2 게임 내 룬 |
| 보석 (GEM) | 디아블로2 게임 내 보석 |
| DP (도파밍 포인트) | D2PAMING 사이트 전용 포인트 화폐 |

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

> 기본 테스트 계정: `admin` / `1234`

---

## ☁️ 라이브 서버 배포 (Render.com)

이 프로젝트는 Render.com을 통해 상시 가동할 수 있도록 설정되어 있습니다.

1. **[Render.com](https://render.com/)**에서 GitHub 계정으로 가입합니다.
2. **New +** > **Blueprint** 메뉴를 선택합니다.
3. 이 저장소(`D2PAMING`)를 연결하면 `render.yaml` 설정에 따라 자동으로 서버가 구축됩니다.
4. 배포가 완료되면 Render에서 제공하는 `.onrender.com` 주소에서 실제 서비스를 확인 가능합니다.

> [!NOTE]
> 무료 티어는 15분 미사용 시 서버가 휴면 상태로 진입합니다. 첫 접속 시 로딩이 다소 걸릴 수 있습니다.

---

## 📁 프로젝트 구조

```
src/
├── main/
│   ├── java/com/example/board/
│   │   ├── config/         # Security 설정
│   │   ├── controller/     # Web & API 컨트롤러
│   │   ├── domain/         # JPA 엔티티 (CurrencyType: RUNE, GEM, DP)
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

© 2026 D2PAMING — 디아블로2 리저렉션 커뮤니티 & 아이템 거래소
