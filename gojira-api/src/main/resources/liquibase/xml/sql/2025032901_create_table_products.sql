CREATE TABLE IF NOT EXISTS projects (
    project_id UUID default gen_random_uuid() PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL
);