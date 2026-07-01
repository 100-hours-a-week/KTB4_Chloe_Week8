-- users
INSERT INTO users
(user_id, email, password, nickname, profile_image, is_member, created_at, updated_at, deleted_at)
VALUES
    (1, 'user1@test.com', 'User1234**', '유저1', '/UploadPhoto/ProfileImage/2UEEC49619S3TS3UD3V7.jpg', true, CURRENT_TIMESTAMP, NULL, NULL),
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
     0, 0, 0, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),

    (4, 2, '네 번째 게시글', '네 번째 게시글 내용입니다.', NULL, CURRENT_TIMESTAMP, false, false,
        1, 5, 2, CURRENT_TIMESTAMP, NULL, NULL),

    (5, 1, '다섯 번째 게시글', '다섯 번째 게시글 내용입니다.', NULL, CURRENT_TIMESTAMP, false, false,
     3, 12, 5, CURRENT_TIMESTAMP, NULL, NULL),

    (6, 2, '여섯 번째 게시글', '여섯 번째 게시글 내용입니다.', NULL, CURRENT_TIMESTAMP, false, false,
     0, 7, 1, CURRENT_TIMESTAMP, NULL, NULL),

    (7, 1, '일곱 번째 게시글', '일곱 번째 게시글 내용입니다.', NULL, CURRENT_TIMESTAMP, false, false,
     4, 20, 7, CURRENT_TIMESTAMP, NULL, NULL),

    (8, 2, '여덟 번째 게시글', '여덟 번째 게시글 내용입니다.', NULL, CURRENT_TIMESTAMP, false, false,
     2, 9, 3, CURRENT_TIMESTAMP, NULL, NULL),

    (9, 1, '아홉 번째 게시글', '아홉 번째 게시글 내용입니다.', NULL, CURRENT_TIMESTAMP, false, false,
     1, 15, 2, CURRENT_TIMESTAMP, NULL, NULL),

    (10, 2, '열 번째 게시글', '열 번째 게시글 내용입니다.', NULL, CURRENT_TIMESTAMP, false, false,
     0, 6, 0, CURRENT_TIMESTAMP, NULL, NULL),

    (11, 1, '열한 번째 게시글', '열한 번째 게시글 내용입니다.', NULL, CURRENT_TIMESTAMP, false, false,
     5, 30, 10, CURRENT_TIMESTAMP, NULL, NULL),

    (12, 2, '열두 번째 게시글', '열두 번째 게시글 내용입니다.', NULL, CURRENT_TIMESTAMP, true, false,
     0, 2, 0, CURRENT_TIMESTAMP, NULL, NULL),

    (13, 1, '열세 번째 게시글', '열세 번째 게시글 내용입니다.', NULL, CURRENT_TIMESTAMP, false, false,
     2, 18, 4, CURRENT_TIMESTAMP, NULL, NULL),

    (14, 2, '열네 번째 게시글', '열네 번째 게시글 내용입니다.', NULL, CURRENT_TIMESTAMP, false, false,
     3, 14, 6, CURRENT_TIMESTAMP, NULL, NULL),

    (15, 1, '열다섯 번째 게시글', '열다섯 번째 게시글 내용입니다.', NULL, CURRENT_TIMESTAMP, false, false,
     0, 4, 1, CURRENT_TIMESTAMP, NULL, NULL),

    (16, 2, '열여섯 번째 게시글', '열여섯 번째 게시글 내용입니다.', NULL, CURRENT_TIMESTAMP, false, false,
     1, 11, 2, CURRENT_TIMESTAMP, NULL, NULL),

    (17, 1, '열일곱 번째 게시글', '열일곱 번째 게시글 내용입니다.', NULL, CURRENT_TIMESTAMP, false, false,
     2, 22, 5, CURRENT_TIMESTAMP, NULL, NULL),

    (18, 2, '열여덟 번째 게시글', '열여덟 번째 게시글 내용입니다.', NULL, CURRENT_TIMESTAMP, false, false,
     4, 25, 8, CURRENT_TIMESTAMP, NULL, NULL),

    (19, 1, '열아홉 번째 게시글', '열아홉 번째 게시글 내용입니다.', NULL, CURRENT_TIMESTAMP, false, false,
     0, 1, 0, CURRENT_TIMESTAMP, NULL, NULL),

    (20, 2, '스무 번째 게시글', '스무 번째 게시글 내용입니다.', NULL, CURRENT_TIMESTAMP, false, false,
     3, 16, 4, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

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
     CURRENT_TIMESTAMP, NULL, NULL);

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