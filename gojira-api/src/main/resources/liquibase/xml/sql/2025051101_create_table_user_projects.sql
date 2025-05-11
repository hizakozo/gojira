CREATE TABLE IF NOT EXISTS user_projects
(
    user_id          UUID NOT NULL,
    project_id       UUID NOT NULL ,
    PRIMARY KEY (user_id, project_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
    FOREIGN KEY (project_id) REFERENCES projects (project_id) ON DELETE CASCADE
);