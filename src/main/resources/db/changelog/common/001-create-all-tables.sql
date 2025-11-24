-- User table :
CREATE TABLE users(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    created_by BIGINT ,
    updated_by BIGINT
);


-- Project table :
CREATE TABLE project(
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL UNIQUE,
    name VARCHAR(50) UNIQUE,
    description TEXT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    created_by BIGINT NOT NULL,

    FOREIGN KEY(created_by) REFERENCES users(id)
);


-- Sprint table :
CREATE TABLE sprint(
    id BIGSERIAL PRIMARY KEY,
    sprint_id BIGINT NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    project_id BIGINT NOT NULL,
    created_by BIGINT NOT NULL,

    FOREIGN KEY(project_id) REFERENCES project(id),
    FOREIGN KEY(created_by) REFERENCES users(id)
);



-- Task table :
CREATE TABLE tasks(
    id BIGSERIAL PRIMARY KEY,
    task_id BIGINT NOT NULL UNIQUE,
    title VARCHAR(50) NOT NULL UNIQUE,
    description TEXT ,
    status VARCHAR(50) NOT NULL,
    priority VARCHAR(50) NOT NULL,
    story_points INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    project_id BIGINT NOT NULL,
    sprint_id BIGINT ,
    assigned_to BIGINT NOT NULL,
    created_by BIGINT NOT NULL,


    FOREIGN KEY(project_id) REFERENCES project(id),
    FOREIGN KEY(sprint_id) REFERENCES sprint(id),
    FOREIGN KEY(assigned_to) REFERENCES users(id),
    FOREIGN KEY(created_by) REFERENCES users(id)
);



-- Task_Comments table :
CREATE TABLE task_comments(
    id BIGSERIAL PRIMARY KEY,
    comment_id BIGINT NOT NULL UNIQUE,
    comment TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    task_id BIGINT NOT NULL,
    commented_by BIGINT  NOT NULL,

    FOREIGN KEY(task_id) REFERENCES tasks(id),
    FOREIGN KEY(commented_by) REFERENCES users(id)
);

