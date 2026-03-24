-- 테스트 회원 데이터 (password: 1234)
INSERT INTO member (username, password, created_at) VALUES
('admin', '$2a$10$1avEEiDvqQUSu5Gi12jWsetpsFaJ471yv6.p6TJ8vyFqEvGF5Kr56', NOW()),
('user1', '$2a$10$1avEEiDvqQUSu5Gi12jWsetpsFaJ471yv6.p6TJ8vyFqEvGF5Kr56', NOW()),
('user2', '$2a$10$1avEEiDvqQUSu5Gi12jWsetpsFaJ471yv6.p6TJ8vyFqEvGF5Kr56', NOW());

-- 테스트 게시글 데이터
INSERT INTO post (title, content, view_count, member_id, created_at, updated_at, server_type, category, item_name, currency_type, currency_name, price_amount, trade_status) VALUES
('[판매] 샤코 팝니다', '가장 저렴하게 팝니다. 쪽지주세요.', 0, 1, NOW(), NOW(), 'LADDER', 'ARMOR', '할리퀸 크레스트 (샤코)', 'RUNE', '자', 1, 'SELLING'),
('[구매] 묻지마 수수께끼 삽니다', '룬 많습니다. 바로 거래 가능', 5, 2, NOW(), NOW(), 'STANDARD', 'ARMOR', '수수께끼', 'RUNE', '베르', 2, 'SELLING'),
('[나눔] 초보용 세트 템 나눔', '방 잡고 기다립니다.', 3, 3, NOW(), NOW(), 'LADDER', 'ETC', '초보용 템', 'DP', '', 0, 'SOLD');
