---
description: 변경사항을 DOPAMING 커밋 컨벤션으로 커밋하고 GitHub에 Push하는 방법
---

# Git 커밋 & Push 워크플로우

## 커밋 컨벤션
모든 커밋은 `[DOPAMING] <타입>: <설명>` 형식을 따릅니다.

| 타입 | 사용 시점 |
|------|-----------|
| `feat` | 새로운 기능 추가 |
| `fix` | 버그 수정 |
| `docs` | README, 주석 등 문서 수정 |
| `style` | UI/CSS 스타일 변경 |
| `refactor` | 기능 변경 없는 코드 개선 |
| `chore` | 빌드, 설정, 패키지 변경 |

---

## 수동 커밋 방법

1. 변경사항 확인
```powershell
git status
git diff
```

2. 스테이징
```powershell
git add .
# 또는 특정 파일만
git add src/main/resources/templates/home.html
```

3. 커밋 (컨벤션에 맞게)
```powershell
git commit -m "[DOPAMING] feat: 새로운 기능 이름"
```

4. Push
```powershell
git push origin main
```

---

## 자동화 스크립트 사용

```powershell
# git add 후 아래 스크립트 실행
node .agent/skills/git-formatter/scripts/format_commit.js <타입> "<메시지>"

# 예시
git add .
node .agent/skills/git-formatter/scripts/format_commit.js feat "공포의 영역 카운트다운 추가"
node .agent/skills/git-formatter/scripts/format_commit.js fix "로그인 Security 설정 수정"
node .agent/skills/git-formatter/scripts/format_commit.js style "모바일 반응형 레이아웃 추가"
```

---

## 빠른 전체 Push (작업 완료 후)

```powershell
git add .
git commit -m "[DOPAMING] feat: (작업 내용 요약)"
git push origin main
```
