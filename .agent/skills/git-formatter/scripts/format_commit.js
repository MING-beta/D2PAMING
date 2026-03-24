#!/usr/bin/env node
/**
 * [DOPAMING] Git 커밋 자동화 스크립트
 * 사용법: node .agent/skills/git-formatter/scripts/format_commit.js <타입> <메시지>
 *
 * 타입: feat | fix | docs | style | refactor | test | chore
 * 예시: node .agent/skills/git-formatter/scripts/format_commit.js feat "공포의 영역 카운트다운 추가"
 */

const { execSync } = require('child_process');

const VALID_TYPES = ['feat', 'fix', 'docs', 'style', 'refactor', 'test', 'chore'];

const type = process.argv[2];
const message = process.argv[3];

if (!type || !message) {
    console.error('사용법: node format_commit.js <타입> <메시지>');
    console.error(`타입 목록: ${VALID_TYPES.join(', ')}`);
    process.exit(1);
}

if (!VALID_TYPES.includes(type)) {
    console.error(`❌ 유효하지 않은 타입: "${type}"`);
    console.error(`사용 가능한 타입: ${VALID_TYPES.join(', ')}`);
    process.exit(1);
}

const commitMessage = `[DOPAMING] ${type}: ${message}`;

try {
    // 스테이징된 변경사항 확인
    const staged = execSync('git diff --cached --name-only', { encoding: 'utf-8' }).trim();
    if (!staged) {
        console.warn('⚠️  스테이징된 변경사항이 없습니다. git add 를 먼저 실행하세요.');
        process.exit(1);
    }

    execSync(`git commit -m "${commitMessage}"`, { stdio: 'inherit' });
    console.log(`✅ 커밋 완료: ${commitMessage}`);
} catch (err) {
    console.error('❌ 커밋 실패:', err.message);
    process.exit(1);
}
