CREATE TABLE IF NOT EXISTS users
(
    user_id          UUID                  default gen_random_uuid() PRIMARY KEY,    -- アプリ独自のユーザーID
    external_user_id VARCHAR(255) NOT NULL,                                          -- JWT の sub（Auth0 のユーザーID）
    email            VARCHAR(255) NOT NULL,                                          -- ユーザーのメールアドレス
    name             VARCHAR(255) NOT NULL,                                          -- ユーザーの名前
    created_at       BIGINT       NOT NULL DEFAULT EXTRACT(EPOCH FROM NOW()) * 1000, -- 作成日時（ミリ秒）
    CONSTRAINT unique_external_user_id UNIQUE (external_user_id)
);