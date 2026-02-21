ALTER TABLE experiences
    ADD COLUMN summary TEXT;

CREATE TABLE experience_missions (
                                     experience_id UUID NOT NULL,
                                     mission TEXT NOT NULL,
                                     CONSTRAINT fk_experience_missions
                                         FOREIGN KEY (experience_id)
                                             REFERENCES experiences(id)
                                             ON DELETE CASCADE
);