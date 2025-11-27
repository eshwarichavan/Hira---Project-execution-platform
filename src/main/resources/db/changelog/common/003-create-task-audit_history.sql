-- Audit History Table :
CREATE TABLE task_audit_history(

    id BIGSERIAL PRIMARY KEY,
    task_id VARCHAR(255) NOT NULL ,
    old_status VARCHAR(50) NOT NULL,
    new_status VARCHAR(50) NOT NULL,
    updated_by BIGINT,
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),

    FOREIGN KEY(task_id) REFERENCES Tasks(task_id) ON DELETE CASCADE,
    FOREIGN KEY(updated_by) REFERENCES Users(id) ON DELETE SET NULL
);