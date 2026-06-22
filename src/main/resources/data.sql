-- users
INSERT INTO users
(user_id, email, password, nickname, profile_image, is_member, created_at, updated_at, deleted_at)
VALUES
    (1, 'user1@test.com', '1234', '유저1', NULL, true, CURRENT_TIMESTAMP, NULL, NULL),
    (2, 'user2@test.com', '1234', '유저2', NULL, true, CURRENT_TIMESTAMP, NULL, NULL),
    (3, 'deleted@test.com', '1234', '탈퇴유저', NULL, false, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

-- posts
INSERT INTO posts
(post_id, user_id, title, content, post_image, date_written, post_hide, is_edited,
 comment_count, view_count, like_count, created_at, updated_at, deleted_at)
VALUES
    (1, 1, '첫 번째 게시글', '첫 번째 게시글 내용입니다.', NULL, CURRENT_TIMESTAMP, false, false,
     2, 10, 1, CURRENT_TIMESTAMP, NULL, NULL),

    (2, 2, '숨김 게시글', '숨김 처리된 게시글입니다.', NULL, CURRENT_TIMESTAMP, true, false,
     0, 3, 0, CURRENT_TIMESTAMP, NULL, NULL),

    (3, 1, '삭제 게시글', '삭제된 게시글입니다.', NULL, CURRENT_TIMESTAMP, false, false,
     0, 0, 0, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

-- comments
INSERT INTO comments
(comment_id, post_id, user_id, parent_comment_id, comment_content,
 comment_date_written, is_having_child, is_blinded,
 created_at, updated_at, deleted_at)
VALUES
    (1, 1, 2, NULL, '첫 번째 댓글입니다.',
     CURRENT_TIMESTAMP, true, false,
     CURRENT_TIMESTAMP, NULL, NULL),

    (2, 1, 1, 1, '첫 번째 댓글의 대댓글입니다.',
     CURRENT_TIMESTAMP, false, false,
     CURRENT_TIMESTAMP, NULL, NULL),

    (3, 1, 2, NULL, '블라인드 처리된 댓글입니다.',
     CURRENT_TIMESTAMP, false, true,
     CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

-- likes
INSERT INTO likes
(like_id, post_id, user_id)
VALUES
    (1, 1, 2);

-- post_change_history
INSERT INTO post_change_history
(change_id, post_id, changed_at, changed_title, changed_content, changed_post_image)
VALUES
    (1, 1, CURRENT_TIMESTAMP, '수정 전 제목', '수정 전 내용입니다.', NULL);

-- post_report_history
INSERT INTO post_report_history
(report_id, post_id, user_id, reported_at)
VALUES
    (1, 1, 2, CURRENT_TIMESTAMP);


ALTER TABLE users ALTER COLUMN user_id RESTART WITH 100;
ALTER TABLE posts ALTER COLUMN post_id RESTART WITH 100;
ALTER TABLE comments ALTER COLUMN comment_id RESTART WITH 100;
ALTER TABLE likes ALTER COLUMN like_id RESTART WITH 100;
ALTER TABLE post_change_history ALTER COLUMN change_id RESTART WITH 100;
ALTER TABLE post_report_history ALTER COLUMN report_id RESTART WITH 100;