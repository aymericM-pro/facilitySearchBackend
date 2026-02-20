-- =============================
-- PROFILES
-- =============================

CREATE TABLE profiles (
          id UUID PRIMARY KEY,
          name VARCHAR(255) NOT NULL,
          title VARCHAR(255),
          location VARCHAR(255),
          available BOOLEAN NOT NULL DEFAULT TRUE,
          email VARCHAR(255),
          phone VARCHAR(255),
          linkedin VARCHAR(255),
          website VARCHAR(255),
          about VARCHAR(2000),
          address_id UUID,
          CONSTRAINT fk_profile_address
              FOREIGN KEY (address_id)
                  REFERENCES addresses(id)
                  ON DELETE SET NULL
);

-- =============================
-- PROFILE SKILLS
-- =============================

CREATE TABLE profile_skills (
            profile_id UUID NOT NULL,
            skill VARCHAR(255) NOT NULL,
            CONSTRAINT fk_profile_skills_profile
                FOREIGN KEY (profile_id)
                    REFERENCES profiles(id)
                    ON DELETE CASCADE
);

-- =============================
-- EXPERIENCES
-- =============================

CREATE TABLE experiences (
                             id UUID PRIMARY KEY,
                             profile_id UUID NOT NULL,
                             company_id UUID,
                             role VARCHAR(255) NOT NULL,
                             location VARCHAR(255),
                             start_date DATE NOT NULL,
                             end_date DATE,
                             description TEXT,
                             CONSTRAINT fk_experience_profile
                                 FOREIGN KEY (profile_id)
                                     REFERENCES profiles(id)
                                     ON DELETE CASCADE,
                             CONSTRAINT fk_experience_company
                                 FOREIGN KEY (company_id)
                                     REFERENCES companies(id)
                                     ON DELETE SET NULL
);

-- =============================
-- EDUCATIONS
-- =============================

CREATE TABLE educations (
                            id UUID PRIMARY KEY,
                            profile_id UUID NOT NULL,
                            school VARCHAR(255) NOT NULL,
                            degree VARCHAR(255),
                            field VARCHAR(255),
                            location VARCHAR(255),
                            start_date DATE NOT NULL,
                            end_date DATE,
                            logo VARCHAR(255),
                            CONSTRAINT fk_education_profile
                                FOREIGN KEY (profile_id)
                                    REFERENCES profiles(id)
                                    ON DELETE CASCADE
);