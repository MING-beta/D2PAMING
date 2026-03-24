---
name: git-formatter
description: Git 커밋 메시지를 일정하게 포맷팅하고 커밋을 생성하는 스킬입니다.
---

# Git Formatter Skill

이 스킬은 커밋 메시지의 컨벤션을 통일하고, 자동으로 정해진 형식에 맞춰 커밋을 생성하기 위한 지침과 스크립트를 제공합니다.

## 규칙 (Instructions)

1. **커밋 메시지 형식**:
   모든 커밋 메시지는 다음 형식을 따라야 합니다.
   `[DOPAMING] <타입>: <설명>`

   - `<타입>` 목록:
     - `feat`: 새로운 기능 추가
     - `fix`: 버그 수정
     - `docs`: 문서 수정
     - `style`: 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
     - `refactor`: 코드 리팩토링
     - `test`: 테스트 코드, 리팩토링 테스트 코드 추가
     - `chore`: 빌드 업무 수정, 패키지 매니저 수정 등

2. **작업 순서**:
   - 변경된 파일을 확인합니다. (`git status`)
   - `scripts/format-commit.js` 스크립트를 사용하여 커밋하거나, 위의 규칙에 맞춰 수동으로 커밋합니다.

3. **자동화 스크립트 사용**:
   만약 커밋을 자동으로 생성하고 싶다면 아래의 스크립트를 실행하세요.
   ```bash
   node .agent/skills/git-formatter/scripts/format_commit.js "<타입>" "<메시지>"
   ```
