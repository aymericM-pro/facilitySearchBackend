CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TYPE contract_type AS ENUM (
    'CDI',
    'CDD',
    'INTERNSHIP',
    'FREELANCE',
    'APPRENTICESHIP'
);

CREATE TYPE company_type AS ENUM (
    'STARTUP',
    'SCALE_UP',
    'ENTERPRISE'
);

CREATE TYPE industry AS ENUM (
    'FINTECH',
    'INSURTECH',
    'HEALTHTECH',
    'MEDTECH',
    'BIOTECH',
    'EDTECH',
    'PROPTECH',
    'LEGALTECH',
    'HRTECH',
    'MARTECH',
    'ADTECH',
    'ECOMMERCE',
    'SAAS',
    'ARTIFICIAL_INTELLIGENCE',
    'CYBERSECURITY',
    'BLOCKCHAIN_WEB3',
    'CLIMATETECH',
    'GREEN_ENERGY',
    'GAMING',
    'CLOUD_COMPUTING'
);

CREATE TABLE addresses (
                           id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                           city VARCHAR(255) NOT NULL,
                           country VARCHAR(255),
                           postal_code VARCHAR(20)
);

CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       pseudo VARCHAR(255) UNIQUE NOT NULL,
                       role VARCHAR(50) DEFAULT 'USER'
);

CREATE TABLE companies (
                           id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                           name VARCHAR(255) NOT NULL,
                           address_id UUID REFERENCES addresses(id),
                           created_at TIMESTAMP DEFAULT NOW(),
                           company_type company_type,
                           industry industry NOT NULL
);

CREATE TABLE skills (
                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                        name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE job_offers (
                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            title VARCHAR(255) NOT NULL,
                            company_id UUID NOT NULL,
                            location VARCHAR(255),
                            description VARCHAR(5000),
                            remote BOOLEAN,
                            salary_min NUMERIC(15,2),
                            salary_max NUMERIC(15,2),
                            contract_type contract_type,
                            created_at TIMESTAMP DEFAULT NOW(),
                            CONSTRAINT fk_job_company
                                FOREIGN KEY (company_id)
                                    REFERENCES companies(id)
                                    ON DELETE CASCADE
);

CREATE TABLE candidates (
                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            first_name VARCHAR(255) NOT NULL,
                            last_name VARCHAR(255) NOT NULL,
                            email VARCHAR(255) UNIQUE NOT NULL,
                            created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE applications (
                              id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                              job_offer_id UUID NOT NULL,
                              candidate_id UUID NOT NULL,
                              status VARCHAR(50) DEFAULT 'PENDING',
                              applied_at TIMESTAMP DEFAULT NOW(),
                              CONSTRAINT fk_application_job
                                  FOREIGN KEY (job_offer_id)
                                      REFERENCES job_offers(id)
                                      ON DELETE CASCADE,
                              CONSTRAINT fk_application_candidate
                                  FOREIGN KEY (candidate_id)
                                      REFERENCES candidates(id)
                                      ON DELETE CASCADE
);

CREATE TABLE job_offer_skills (
                                  job_offer_id UUID NOT NULL,
                                  skill_id UUID NOT NULL,
                                  PRIMARY KEY (job_offer_id, skill_id),
                                  CONSTRAINT fk_jos_job
                                      FOREIGN KEY (job_offer_id)
                                          REFERENCES job_offers(id)
                                          ON DELETE CASCADE,
                                  CONSTRAINT fk_jos_skill
                                      FOREIGN KEY (skill_id)
                                          REFERENCES skills(id)
                                          ON DELETE CASCADE
);

INSERT INTO skills (id, name) VALUES
    (gen_random_uuid(), 'Java'),
    (gen_random_uuid(), 'Kotlin'),
    (gen_random_uuid(), 'Spring Boot'),
    (gen_random_uuid(), 'Spring Security'),
    (gen_random_uuid(), 'PostgreSQL'),
    (gen_random_uuid(), 'Docker'),
    (gen_random_uuid(), 'Kubernetes'),
    (gen_random_uuid(), 'AWS'),
    (gen_random_uuid(), 'Terraform'),
    (gen_random_uuid(), 'Vue.js'),
    (gen_random_uuid(), 'React'),
    (gen_random_uuid(), 'Angular'),
    (gen_random_uuid(), 'TypeScript'),
    (gen_random_uuid(), 'Node.js'),
    (gen_random_uuid(), 'Kafka'),
    (gen_random_uuid(), 'Redis'),
    (gen_random_uuid(), 'MongoDB'),
    (gen_random_uuid(), 'CI/CD'),
    (gen_random_uuid(), 'JUnit'),
    (gen_random_uuid(), 'Clean Architecture'),
    -- Languages
    (gen_random_uuid(), 'Python'),
    (gen_random_uuid(), 'Go'),
    (gen_random_uuid(), 'Rust'),
    (gen_random_uuid(), 'C#'),
    (gen_random_uuid(), 'C++'),
    (gen_random_uuid(), 'PHP'),
    (gen_random_uuid(), 'Ruby'),
    (gen_random_uuid(), 'Scala'),
    (gen_random_uuid(), 'Swift'),
    (gen_random_uuid(), 'Dart'),
    -- Frontend
    (gen_random_uuid(), 'Next.js'),
    (gen_random_uuid(), 'Nuxt.js'),
    (gen_random_uuid(), 'Svelte'),
    (gen_random_uuid(), 'Tailwind CSS'),
    (gen_random_uuid(), 'HTML/CSS'),
    (gen_random_uuid(), 'JavaScript'),
    (gen_random_uuid(), 'Flutter'),
    (gen_random_uuid(), 'React Native'),
    -- Backend frameworks
    (gen_random_uuid(), 'Django'),
    (gen_random_uuid(), 'FastAPI'),
    (gen_random_uuid(), 'Express.js'),
    (gen_random_uuid(), 'NestJS'),
    (gen_random_uuid(), 'Ruby on Rails'),
    (gen_random_uuid(), '.NET'),
    (gen_random_uuid(), 'Quarkus'),
    (gen_random_uuid(), 'Micronaut'),
    -- Data & messaging
    (gen_random_uuid(), 'MySQL'),
    (gen_random_uuid(), 'Elasticsearch'),
    (gen_random_uuid(), 'RabbitMQ'),
    (gen_random_uuid(), 'Cassandra'),
    (gen_random_uuid(), 'DynamoDB'),
    (gen_random_uuid(), 'Neo4j'),
    (gen_random_uuid(), 'SQL Server'),
    (gen_random_uuid(), 'Oracle DB'),
    -- Cloud & DevOps
    (gen_random_uuid(), 'GCP'),
    (gen_random_uuid(), 'Azure'),
    (gen_random_uuid(), 'Ansible'),
    (gen_random_uuid(), 'Jenkins'),
    (gen_random_uuid(), 'GitLab CI'),
    (gen_random_uuid(), 'GitHub Actions'),
    (gen_random_uuid(), 'ArgoCD'),
    (gen_random_uuid(), 'Helm'),
    (gen_random_uuid(), 'Prometheus'),
    (gen_random_uuid(), 'Grafana'),
    -- Testing & quality
    (gen_random_uuid(), 'Selenium'),
    (gen_random_uuid(), 'Cypress'),
    (gen_random_uuid(), 'Mockito'),
    (gen_random_uuid(), 'SonarQube'),
    -- Architecture & practices
    (gen_random_uuid(), 'Microservices'),
    (gen_random_uuid(), 'GraphQL'),
    (gen_random_uuid(), 'REST API'),
    (gen_random_uuid(), 'gRPC'),
    (gen_random_uuid(), 'Event Sourcing'),
    (gen_random_uuid(), 'CQRS'),
    -- AI & Data Science
    (gen_random_uuid(), 'TensorFlow'),
    (gen_random_uuid(), 'PyTorch'),
    (gen_random_uuid(), 'Spark'),
    (gen_random_uuid(), 'Hadoop'),
    (gen_random_uuid(), 'Pandas');

INSERT INTO addresses (id, city, country) VALUES
    (gen_random_uuid(), 'Paris', 'France'),
    (gen_random_uuid(), 'Roubaix', 'France'),
    (gen_random_uuid(), 'Toulouse', 'France'),
    (gen_random_uuid(), 'Nice', 'France'),
    (gen_random_uuid(), 'Berlin', 'Germany'),
    (gen_random_uuid(), 'Montpellier', 'France'),
    (gen_random_uuid(), 'Bezons', 'France'),
    (gen_random_uuid(), 'Boulogne-Billancourt', 'France'),
    (gen_random_uuid(), 'Levallois-Perret', 'France'),
    (gen_random_uuid(), 'Vélizy', 'France'),
    (gen_random_uuid(), 'Montreuil', 'France'),
    (gen_random_uuid(), 'Saint-Denis', 'France'),
    (gen_random_uuid(), 'Courbevoie', 'France'),
    (gen_random_uuid(), 'Clichy', 'France'),
    (gen_random_uuid(), 'Issy-les-Moulineaux', 'France'),
    (gen_random_uuid(), 'Stockholm', 'Sweden'),
    (gen_random_uuid(), 'London', 'United Kingdom'),
    (gen_random_uuid(), 'Nantes', 'France'),
    (gen_random_uuid(), 'Labège', 'France'),
    (gen_random_uuid(), 'Grenoble', 'France'),
    (gen_random_uuid(), 'Lyon', 'France');

INSERT INTO companies (id, name, address_id, company_type, industry)
SELECT gen_random_uuid(), v.name, a.id, v.ctype::company_type, v.ind::industry
FROM (VALUES
    -- Core
    ('Agate IT',              'Paris',                  'STARTUP',    'SAAS'),
    ('Doctolib',              'Paris',                  'SCALE_UP',   'HEALTHTECH'),
    ('Alan',                  'Paris',                  'SCALE_UP',   'INSURTECH'),
    ('Qonto',                 'Paris',                  'SCALE_UP',   'FINTECH'),
    ('Mirakl',                'Paris',                  'SCALE_UP',   'ECOMMERCE'),
    ('Back Market',           'Paris',                  'SCALE_UP',   'ECOMMERCE'),
    ('OVHcloud',              'Roubaix',                'ENTERPRISE',  'CLOUD_COMPUTING'),
    ('Dataiku',               'Paris',                  'SCALE_UP',   'ARTIFICIAL_INTELLIGENCE'),
    ('Ledger',                'Paris',                  'SCALE_UP',   'BLOCKCHAIN_WEB3'),
    ('BlaBlaCar',             'Paris',                  'SCALE_UP',   'SAAS'),
    ('Sopra Steria',          'Paris',                  'ENTERPRISE',  'SAAS'),
    ('Capgemini',             'Paris',                  'ENTERPRISE',  'SAAS'),
    ('Thales',                'Toulouse',               'ENTERPRISE',  'CYBERSECURITY'),
    ('Airbus',                'Toulouse',               'ENTERPRISE',  'SAAS'),
    ('Orange',                'Paris',                  'ENTERPRISE',  'CYBERSECURITY'),
    ('BNP Paribas',           'Paris',                  'ENTERPRISE',  'FINTECH'),
    ('Crédit Agricole CIB',   'Paris',                  'ENTERPRISE',  'FINTECH'),
    ('Natixis',               'Paris',                  'ENTERPRISE',  'FINTECH'),
    ('Amadeus',               'Nice',                   'ENTERPRISE',  'SAAS'),
    ('SAP',                   'Berlin',                 'ENTERPRISE',  'SAAS'),
    -- French Tech & Startups
    ('Swile',                 'Montpellier',            'SCALE_UP',   'HRTECH'),
    ('Payfit',                'Paris',                  'SCALE_UP',   'HRTECH'),
    ('Spendesk',              'Paris',                  'SCALE_UP',   'FINTECH'),
    ('Algolia',               'Paris',                  'SCALE_UP',   'SAAS'),
    ('ContentSquare',         'Paris',                  'SCALE_UP',   'MARTECH'),
    ('ManoMano',              'Paris',                  'SCALE_UP',   'ECOMMERCE'),
    ('Vestiaire Collective',  'Paris',                  'SCALE_UP',   'ECOMMERCE'),
    ('Deezer',                'Paris',                  'ENTERPRISE',  'SAAS'),
    ('Criteo',                'Paris',                  'ENTERPRISE',  'ADTECH'),
    ('Dailymotion',           'Paris',                  'ENTERPRISE',  'ADTECH'),
    ('Meero',                 'Paris',                  'STARTUP',    'ARTIFICIAL_INTELLIGENCE'),
    ('Scaleway',              'Paris',                  'SCALE_UP',   'CLOUD_COMPUTING'),
    ('Numberly',              'Paris',                  'SCALE_UP',   'MARTECH'),
    ('AB Tasty',              'Paris',                  'SCALE_UP',   'MARTECH'),
    ('Platform.sh',           'Paris',                  'SCALE_UP',   'CLOUD_COMPUTING'),
    -- ESN & Consulting
    ('Atos',                  'Bezons',                 'ENTERPRISE',  'SAAS'),
    ('Accenture',             'Paris',                  'ENTERPRISE',  'SAAS'),
    ('CGI',                   'Paris',                  'ENTERPRISE',  'SAAS'),
    ('Altran',                'Paris',                  'ENTERPRISE',  'SAAS'),
    ('Alten',                 'Boulogne-Billancourt',   'ENTERPRISE',  'SAAS'),
    ('Devoteam',              'Levallois-Perret',       'ENTERPRISE',  'CLOUD_COMPUTING'),
    ('Octo Technology',       'Paris',                  'SCALE_UP',   'SAAS'),
    ('Xebia',                 'Paris',                  'SCALE_UP',   'SAAS'),
    ('Ippon Technologies',    'Paris',                  'SCALE_UP',   'SAAS'),
    ('Zenika',                'Paris',                  'SCALE_UP',   'SAAS'),
    -- Grands groupes & Industrie
    ('Dassault Systèmes',     'Vélizy',                 'ENTERPRISE',  'SAAS'),
    ('Ubisoft',               'Montreuil',              'ENTERPRISE',  'GAMING'),
    ('Renault',               'Boulogne-Billancourt',   'ENTERPRISE',  'SAAS'),
    ('SNCF',                  'Saint-Denis',            'ENTERPRISE',  'SAAS'),
    ('EDF',                   'Paris',                  'ENTERPRISE',  'GREEN_ENERGY'),
    ('Société Générale',      'Paris',                  'ENTERPRISE',  'FINTECH'),
    ('AXA',                   'Paris',                  'ENTERPRISE',  'INSURTECH'),
    ('Total Energies',        'Courbevoie',             'ENTERPRISE',  'GREEN_ENERGY'),
    ('L''Oréal',              'Clichy',                 'ENTERPRISE',  'ECOMMERCE'),
    ('Safran',                'Paris',                  'ENTERPRISE',  'SAAS'),
    -- International
    ('Google',                'Paris',                  'ENTERPRISE',  'CLOUD_COMPUTING'),
    ('Microsoft',             'Issy-les-Moulineaux',    'ENTERPRISE',  'CLOUD_COMPUTING'),
    ('Amazon',                'Clichy',                 'ENTERPRISE',  'CLOUD_COMPUTING'),
    ('Meta',                  'Paris',                  'ENTERPRISE',  'ARTIFICIAL_INTELLIGENCE'),
    ('Apple',                 'Paris',                  'ENTERPRISE',  'SAAS'),
    ('Spotify',               'Stockholm',              'ENTERPRISE',  'SAAS'),
    ('Revolut',               'London',                 'SCALE_UP',   'FINTECH'),
    ('Klarna',                'Stockholm',              'SCALE_UP',   'FINTECH'),
    ('N26',                   'Berlin',                 'SCALE_UP',   'FINTECH'),
    ('Zalando',               'Berlin',                 'ENTERPRISE',  'ECOMMERCE'),
    -- Régions françaises
    ('iAdvize',               'Nantes',                 'SCALE_UP',   'ARTIFICIAL_INTELLIGENCE'),
    ('Berger-Levrault',       'Labège',                 'ENTERPRISE',  'SAAS'),
    ('Hardis Group',          'Grenoble',               'ENTERPRISE',  'SAAS'),
    ('Braincube',             'Lyon',                   'SCALE_UP',   'ARTIFICIAL_INTELLIGENCE')
) AS v(name, city, ctype, ind)
JOIN addresses a ON a.city = v.city;



INSERT INTO job_offers (id, title, company_id, location, description, remote, salary_min, salary_max, contract_type)
SELECT gen_random_uuid(), v.title, c.id, a.city, v.description, v.remote, v.smin, v.smax, v.ctype::contract_type
FROM (VALUES
    ('Senior Kotlin Backend Engineer',       'Agate IT',            'Design and implement scalable microservices with Kotlin and Spring Boot.',                       true,  60000, 75000,  'CDI'),
    ('Fullstack Developer Vue / Java',        'Qonto',               'Build modern SaaS features with Vue.js and Spring Boot.',                                        true,  55000, 70000,  'CDI'),
    ('DevOps Engineer AWS',                   'Doctolib',            'Maintain Kubernetes clusters and CI/CD pipelines on AWS.',                                       true,  65000, 85000,  'CDI'),
    ('Frontend Engineer React',               'Back Market',         'Develop scalable UI components with React and TypeScript.',                                      true,  50000, 65000,  'CDI'),
    ('Software Architect',                    'Thales',              'Lead architecture design across distributed systems.',                                           false, 75000, 95000,  'CDI'),
    -- 50 new job offers
    ('Backend Developer Python',              'Algolia',             'Develop and maintain Python microservices with Django and FastAPI.',                             true,  50000, 65000,  'CDI'),
    ('SRE Engineer',                          'Criteo',              'Ensure reliability and performance of production systems at scale.',                             true,  65000, 85000,  'CDI'),
    ('Data Engineer Spark',                   'Dataiku',             'Build and optimize data pipelines using Spark and Kafka.',                                       true,  55000, 75000,  'CDI'),
    ('Mobile Developer Flutter',              'Swile',               'Create cross-platform mobile apps with Flutter and Dart.',                                       true,  45000, 60000,  'CDI'),
    ('Fullstack Developer React / Node.js',   'Payfit',              'Build end-to-end features with React and Express.js.',                                           true,  50000, 68000,  'CDI'),
    ('Cloud Architect AWS',                   'OVHcloud',            'Design cloud-native architectures on AWS with Terraform and Kubernetes.',                        true,  70000, 90000,  'CDI'),
    ('Machine Learning Engineer',             'ContentSquare',       'Develop and deploy ML models with PyTorch and TensorFlow.',                                      true,  60000, 80000,  'CDI'),
    ('Java Backend Developer',                'Sopra Steria',        'Develop robust backend services with Java and Spring Boot.',                                     false, 45000, 60000,  'CDI'),
    ('DevSecOps Engineer',                    'Atos',                'Integrate security into CI/CD pipelines with SonarQube and GitLab CI.',                         true,  55000, 72000,  'CDI'),
    ('Angular Frontend Developer',            'Capgemini',           'Build enterprise web applications with Angular and TypeScript.',                                 false, 42000, 58000,  'CDI'),
    ('Rust Systems Developer',                'Safran',              'Develop high-performance embedded systems with Rust and C++.',                                   false, 55000, 75000,  'CDI'),
    ('Scala Data Engineer',                   'Spotify',             'Build real-time data processing pipelines with Scala and Spark.',                                true,  60000, 80000,  'CDI'),
    ('Go Backend Developer',                  'Revolut',             'Design and build high-throughput APIs in Go.',                                                   true,  55000, 75000,  'CDI'),
    ('React Native Mobile Developer',         'N26',                 'Develop the mobile banking app with React Native.',                                              true,  50000, 70000,  'CDI'),
    ('Platform Engineer Kubernetes',          'Scaleway',            'Build and maintain internal developer platforms with Kubernetes and Helm.',                      true,  60000, 80000,  'CDI'),
    ('PHP Backend Developer',                 'ManoMano',            'Maintain and evolve e-commerce platform with PHP and Symfony.',                                  true,  42000, 55000,  'CDI'),
    ('QA Automation Engineer',                'Spendesk',            'Design and implement automated test suites with Cypress and Selenium.',                          true,  45000, 60000,  'CDI'),
    ('C++ Embedded Developer',                'Airbus',              'Develop firmware and embedded software for aerospace systems.',                                  false, 50000, 70000,  'CDI'),
    ('Fullstack Developer .NET / Angular',    'Société Générale',    'Build internal tools with .NET backend and Angular frontend.',                                   false, 48000, 65000,  'CDI'),
    ('Lead Developer Java',                   'Octo Technology',     'Lead a team of developers on Java/Spring microservices projects.',                               true,  65000, 85000,  'CDI'),
    ('Consultant Cloud GCP',                  'Zenika',              'Advise clients on cloud migration strategies using GCP.',                                        true,  50000, 70000,  'CDI'),
    ('Kotlin Android Developer',              'Deezer',              'Develop native Android applications with Kotlin.',                                               true,  45000, 62000,  'CDI'),
    ('Frontend Engineer Svelte',              'AB Tasty',            'Build modern and reactive UIs with Svelte and TypeScript.',                                      true,  48000, 65000,  'CDI'),
    ('Infrastructure Engineer Terraform',     'Devoteam',            'Manage infrastructure as code with Terraform and Ansible.',                                      true,  55000, 75000,  'CDI'),
    ('Senior Ruby on Rails Developer',        'Vestiaire Collective', 'Build and scale marketplace features with Ruby on Rails.',                                      true,  55000, 72000,  'CDI'),
    ('NestJS Backend Developer',              'Dailymotion',         'Design and implement APIs with NestJS and PostgreSQL.',                                          true,  48000, 65000,  'CDI'),
    ('Elasticsearch Engineer',                'Numberly',            'Optimize search infrastructure with Elasticsearch at scale.',                                    true,  55000, 75000,  'CDI'),
    ('Site Reliability Engineer',             'Google',              'Build observability and monitoring with Prometheus and Grafana.',                                 true,  70000, 95000,  'CDI'),
    ('Software Engineer C#',                  'Microsoft',           'Develop cloud services with C# and Azure.',                                                      true,  65000, 90000,  'CDI'),
    ('Backend Engineer Java / Kotlin',        'Amazon',              'Build large-scale distributed systems on AWS.',                                                  true,  65000, 90000,  'CDI'),
    ('AI Research Engineer',                  'Meta',                'Research and develop cutting-edge AI models with PyTorch.',                                      true,  75000, 100000, 'CDI'),
    ('iOS Developer Swift',                   'Apple',               'Develop native iOS applications with Swift and SwiftUI.',                                        false, 60000, 85000,  'CDI'),
    ('Fullstack Developer Next.js',           'Zalando',             'Build e-commerce features with Next.js and GraphQL.',                                            true,  55000, 75000,  'CDI'),
    ('Fintech Backend Developer Go',          'Klarna',              'Build payment processing services with Go and gRPC.',                                            true,  55000, 75000,  'CDI'),
    ('Chatbot Developer Python',              'iAdvize',             'Build conversational AI solutions with Python and NLP frameworks.',                              true,  45000, 60000,  'CDI'),
    ('ERP Developer Java',                    'Berger-Levrault',     'Develop and customize ERP solutions with Java and Spring.',                                      false, 40000, 55000,  'CDI'),
    ('IoT Engineer Python / C++',             'Hardis Group',        'Develop IoT data collection and analysis platforms.',                                            false, 45000, 62000,  'CDI'),
    ('Data Scientist Python',                 'Braincube',           'Analyze manufacturing data with Pandas and machine learning models.',                            true,  50000, 70000,  'CDI'),
    ('Game Developer C++',                    'Ubisoft',             'Develop AAA game engine features with C++ and Unreal Engine.',                                   false, 45000, 65000,  'CDI'),
    ('3D Simulation Developer',               'Dassault Systèmes',   'Build 3D simulation software for industrial applications.',                                      false, 50000, 70000,  'CDI'),
    ('Cybersecurity Engineer',                'Orange',              'Implement security solutions and conduct penetration testing.',                                   false, 55000, 75000,  'CDI'),
    ('API Developer Spring Boot',             'AXA',                 'Build and maintain REST APIs for insurance platforms.',                                          false, 50000, 68000,  'CDI'),
    ('Data Platform Engineer',                'Total Energies',      'Design data platforms with Hadoop and Spark for energy analytics.',                              true,  55000, 75000,  'CDI'),
    ('Consultant Technique Kotlin',           'Ippon Technologies',  'Accompagner les clients dans la modernisation de leurs backends Kotlin.',                        true,  50000, 70000,  'CDI'),
    ('Développeur Quarkus',                   'Xebia',               'Développer des microservices cloud-native avec Quarkus et GraalVM.',                            true,  48000, 65000,  'CDI'),
    ('Frontend Developer Vue.js',             'Platform.sh',         'Build reactive dashboards and admin panels with Vue.js and Nuxt.',                               true,  45000, 60000,  'CDI'),
    ('Ingénieur Big Data',                    'BNP Paribas',         'Concevoir des pipelines big data avec Kafka et Cassandra.',                                      false, 50000, 70000,  'CDI'),
    ('Développeur RPA Python',                'Crédit Agricole CIB', 'Automatiser les processus métier avec Python et des outils RPA.',                               false, 42000, 58000,  'CDD'),
    ('Stagiaire Développeur React',           'Alan',                'Participer au développement d''une application web interne en React.',                           false, 8000,  12000,  'INTERNSHIP'),
    ('Alternant DevOps',                      'BlaBlaCar',           'Apprendre et mettre en place des pipelines CI/CD avec GitHub Actions.',                         false, 14000, 18000,  'APPRENTICESHIP')
) AS v(title, company_name, description, remote, smin, smax, ctype)
JOIN companies c ON c.name = v.company_name
JOIN addresses a ON a.id = c.address_id;

INSERT INTO candidates (id, first_name, last_name, email) VALUES
          (gen_random_uuid(), 'Lucas', 'Martin', 'lucas.martin@gmail.com'),
          (gen_random_uuid(), 'Emma', 'Bernard', 'emma.bernard@gmail.com'),
          (gen_random_uuid(), 'Hugo', 'Dubois', 'hugo.dubois@gmail.com'),
          (gen_random_uuid(), 'Chloé', 'Moreau', 'chloe.moreau@gmail.com'),
          (gen_random_uuid(), 'Nathan', 'Laurent', 'nathan.laurent@gmail.com'),
          (gen_random_uuid(), 'Léa', 'Simon', 'lea.simon@gmail.com'),
          (gen_random_uuid(), 'Thomas', 'Michel', 'thomas.michel@gmail.com'),
          (gen_random_uuid(), 'Camille', 'Garcia', 'camille.garcia@gmail.com'),
          (gen_random_uuid(), 'Maxime', 'Roux', 'maxime.roux@gmail.com'),
          (gen_random_uuid(), 'Inès', 'Fournier', 'ines.fournier@gmail.com'),
          (gen_random_uuid(), 'Antoine', 'Dupont', 'antoine.dupont@gmail.com'),
          (gen_random_uuid(), 'Julie', 'Lefebvre', 'julie.lefebvre@gmail.com'),
          (gen_random_uuid(), 'Raphaël', 'Leroy', 'raphael.leroy@gmail.com'),
          (gen_random_uuid(), 'Clara', 'David', 'clara.david@gmail.com'),
          (gen_random_uuid(), 'Alexandre', 'Bertrand', 'alexandre.bertrand@gmail.com'),
          (gen_random_uuid(), 'Manon', 'Robert', 'manon.robert@gmail.com'),
          (gen_random_uuid(), 'Gabriel', 'Richard', 'gabriel.richard@gmail.com'),
          (gen_random_uuid(), 'Sarah', 'Petit', 'sarah.petit@gmail.com'),
          (gen_random_uuid(), 'Louis', 'Durand', 'louis.durand@gmail.com'),
          (gen_random_uuid(), 'Zoé', 'Lecomte', 'zoe.lecomte@gmail.com'),
          (gen_random_uuid(), 'Arthur', 'Garnier', 'arthur.garnier@gmail.com'),
          (gen_random_uuid(), 'Lina', 'Bonnet', 'lina.bonnet@gmail.com'),
          (gen_random_uuid(), 'Jules', 'Lambert', 'jules.lambert@gmail.com'),
          (gen_random_uuid(), 'Margot', 'Fontaine', 'margot.fontaine@gmail.com'),
          (gen_random_uuid(), 'Théo', 'Rousseau', 'theo.rousseau@gmail.com'),
          (gen_random_uuid(), 'Eva', 'Vincent', 'eva.vincent@gmail.com'),
          (gen_random_uuid(), 'Adam', 'Muller', 'adam.muller@gmail.com'),
          (gen_random_uuid(), 'Jade', 'Girard', 'jade.girard@gmail.com'),
          (gen_random_uuid(), 'Enzo', 'Andre', 'enzo.andre@gmail.com'),
          (gen_random_uuid(), 'Louise', 'Mercier', 'louise.mercier@gmail.com'),
          (gen_random_uuid(), 'Mathis', 'Blanc', 'mathis.blanc@gmail.com'),
          (gen_random_uuid(), 'Alice', 'Guerin', 'alice.guerin@gmail.com'),
          (gen_random_uuid(), 'Léon', 'Boyer', 'leon.boyer@gmail.com'),
          (gen_random_uuid(), 'Rose', 'Lopez', 'rose.lopez@gmail.com'),
          (gen_random_uuid(), 'Paul', 'Martinez', 'paul.martinez@gmail.com'),
          (gen_random_uuid(), 'Ambre', 'Legrand', 'ambre.legrand@gmail.com'),
          (gen_random_uuid(), 'Victor', 'Faure', 'victor.faure@gmail.com'),
          (gen_random_uuid(), 'Juliette', 'Giraud', 'juliette.giraud@gmail.com'),
          (gen_random_uuid(), 'Ethan', 'Noel', 'ethan.noel@gmail.com'),
          (gen_random_uuid(), 'Charlotte', 'Henry', 'charlotte.henry@gmail.com'),
          (gen_random_uuid(), 'Noé', 'Morel', 'noe.morel@gmail.com'),
          (gen_random_uuid(), 'Agathe', 'Chevalier', 'agathe.chevalier@gmail.com'),
          (gen_random_uuid(), 'Sacha', 'Perrin', 'sacha.perrin@gmail.com'),
          (gen_random_uuid(), 'Émilie', 'Clement', 'emilie.clement@gmail.com'),
          (gen_random_uuid(), 'Axel', 'Renard', 'axel.renard@gmail.com'),
          (gen_random_uuid(), 'Anna', 'Picard', 'anna.picard@gmail.com'),
          (gen_random_uuid(), 'Romain', 'Brunet', 'romain.brunet@gmail.com'),
          (gen_random_uuid(), 'Célia', 'Arnaud', 'celia.arnaud@gmail.com'),
          (gen_random_uuid(), 'Mathéo', 'Colin', 'matheo.colin@gmail.com'),
          (gen_random_uuid(), 'Nina', 'Vidal', 'nina.vidal@gmail.com'),
          (gen_random_uuid(), 'Clément', 'Masson', 'clement.masson@gmail.com'),
          (gen_random_uuid(), 'Océane', 'Marie', 'oceane.marie@gmail.com'),
          (gen_random_uuid(), 'Bastien', 'Lemoine', 'bastien.lemoine@gmail.com'),
          (gen_random_uuid(), 'Lucie', 'Philippe', 'lucie.philippe@gmail.com'),
          (gen_random_uuid(), 'Dylan', 'Marchand', 'dylan.marchand@gmail.com'),
          (gen_random_uuid(), 'Pauline', 'Duval', 'pauline.duval@gmail.com'),
          (gen_random_uuid(), 'Valentin', 'Denis', 'valentin.denis@gmail.com'),
          (gen_random_uuid(), 'Elsa', 'Caron', 'elsa.caron@gmail.com'),
          (gen_random_uuid(), 'Quentin', 'Maillot', 'quentin.maillot@gmail.com');


INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT
    gen_random_uuid(),
    jo.id,
    c.id,
    'PENDING'
FROM job_offers jo, candidates c
WHERE jo.title = 'Senior Kotlin Backend Engineer'
  AND c.email = 'lucas.martin@gmail.com';


INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT
    gen_random_uuid(),
    jo.id,
    c.id,
    'ACCEPTED'
FROM job_offers jo, candidates c
WHERE jo.title = 'DevOps Engineer AWS'
  AND c.email = 'emma.bernard@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT
    gen_random_uuid(),
    jo.id,
    c.id,
    'REJECTED'
FROM job_offers jo, candidates c
WHERE jo.title = 'Frontend Engineer React'
  AND c.email = 'hugo.dubois@gmail.com';

-- 50 new applications

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'PENDING'
FROM job_offers jo, candidates c
WHERE jo.title = 'Backend Developer Python' AND c.email = 'antoine.dupont@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'ACCEPTED'
FROM job_offers jo, candidates c
WHERE jo.title = 'Backend Developer Python' AND c.email = 'julie.lefebvre@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'PENDING'
FROM job_offers jo, candidates c
WHERE jo.title = 'SRE Engineer' AND c.email = 'raphael.leroy@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'REJECTED'
FROM job_offers jo, candidates c
WHERE jo.title = 'SRE Engineer' AND c.email = 'clara.david@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'ACCEPTED'
FROM job_offers jo, candidates c
WHERE jo.title = 'Data Engineer Spark' AND c.email = 'alexandre.bertrand@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'PENDING'
FROM job_offers jo, candidates c
WHERE jo.title = 'Data Engineer Spark' AND c.email = 'manon.robert@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'PENDING'
FROM job_offers jo, candidates c
WHERE jo.title = 'Mobile Developer Flutter' AND c.email = 'gabriel.richard@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'ACCEPTED'
FROM job_offers jo, candidates c
WHERE jo.title = 'Mobile Developer Flutter' AND c.email = 'sarah.petit@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'REJECTED'
FROM job_offers jo, candidates c
WHERE jo.title = 'Fullstack Developer React / Node.js' AND c.email = 'louis.durand@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'PENDING'
FROM job_offers jo, candidates c
WHERE jo.title = 'Fullstack Developer React / Node.js' AND c.email = 'zoe.lecomte@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'ACCEPTED'
FROM job_offers jo, candidates c
WHERE jo.title = 'Cloud Architect AWS' AND c.email = 'arthur.garnier@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'PENDING'
FROM job_offers jo, candidates c
WHERE jo.title = 'Machine Learning Engineer' AND c.email = 'lina.bonnet@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'REJECTED'
FROM job_offers jo, candidates c
WHERE jo.title = 'Machine Learning Engineer' AND c.email = 'jules.lambert@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'PENDING'
FROM job_offers jo, candidates c
WHERE jo.title = 'Java Backend Developer' AND c.email = 'margot.fontaine@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'ACCEPTED'
FROM job_offers jo, candidates c
WHERE jo.title = 'Java Backend Developer' AND c.email = 'theo.rousseau@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'PENDING'
FROM job_offers jo, candidates c
WHERE jo.title = 'DevSecOps Engineer' AND c.email = 'eva.vincent@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'REJECTED'
FROM job_offers jo, candidates c
WHERE jo.title = 'Angular Frontend Developer' AND c.email = 'adam.muller@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'PENDING'
FROM job_offers jo, candidates c
WHERE jo.title = 'Angular Frontend Developer' AND c.email = 'jade.girard@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'ACCEPTED'
FROM job_offers jo, candidates c
WHERE jo.title = 'Rust Systems Developer' AND c.email = 'enzo.andre@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'PENDING'
FROM job_offers jo, candidates c
WHERE jo.title = 'Scala Data Engineer' AND c.email = 'louise.mercier@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'REJECTED'
FROM job_offers jo, candidates c
WHERE jo.title = 'Scala Data Engineer' AND c.email = 'mathis.blanc@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'PENDING'
FROM job_offers jo, candidates c
WHERE jo.title = 'Go Backend Developer' AND c.email = 'alice.guerin@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'ACCEPTED'
FROM job_offers jo, candidates c
WHERE jo.title = 'Go Backend Developer' AND c.email = 'leon.boyer@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'PENDING'
FROM job_offers jo, candidates c
WHERE jo.title = 'React Native Mobile Developer' AND c.email = 'rose.lopez@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'REJECTED'
FROM job_offers jo, candidates c
WHERE jo.title = 'React Native Mobile Developer' AND c.email = 'paul.martinez@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'ACCEPTED'
FROM job_offers jo, candidates c
WHERE jo.title = 'Platform Engineer Kubernetes' AND c.email = 'ambre.legrand@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'PENDING'
FROM job_offers jo, candidates c
WHERE jo.title = 'PHP Backend Developer' AND c.email = 'victor.faure@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'PENDING'
FROM job_offers jo, candidates c
WHERE jo.title = 'QA Automation Engineer' AND c.email = 'juliette.giraud@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'ACCEPTED'
FROM job_offers jo, candidates c
WHERE jo.title = 'QA Automation Engineer' AND c.email = 'ethan.noel@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'REJECTED'
FROM job_offers jo, candidates c
WHERE jo.title = 'C++ Embedded Developer' AND c.email = 'charlotte.henry@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'PENDING'
FROM job_offers jo, candidates c
WHERE jo.title = 'C++ Embedded Developer' AND c.email = 'noe.morel@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'ACCEPTED'
FROM job_offers jo, candidates c
WHERE jo.title = 'Fullstack Developer .NET / Angular' AND c.email = 'agathe.chevalier@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'PENDING'
FROM job_offers jo, candidates c
WHERE jo.title = 'Lead Developer Java' AND c.email = 'sacha.perrin@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'REJECTED'
FROM job_offers jo, candidates c
WHERE jo.title = 'Lead Developer Java' AND c.email = 'emilie.clement@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'PENDING'
FROM job_offers jo, candidates c
WHERE jo.title = 'Consultant Cloud GCP' AND c.email = 'axel.renard@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'ACCEPTED'
FROM job_offers jo, candidates c
WHERE jo.title = 'Kotlin Android Developer' AND c.email = 'anna.picard@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'PENDING'
FROM job_offers jo, candidates c
WHERE jo.title = 'Frontend Engineer Svelte' AND c.email = 'romain.brunet@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'REJECTED'
FROM job_offers jo, candidates c
WHERE jo.title = 'Infrastructure Engineer Terraform' AND c.email = 'celia.arnaud@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'PENDING'
FROM job_offers jo, candidates c
WHERE jo.title = 'Senior Ruby on Rails Developer' AND c.email = 'matheo.colin@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'ACCEPTED'
FROM job_offers jo, candidates c
WHERE jo.title = 'Site Reliability Engineer' AND c.email = 'nina.vidal@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'PENDING'
FROM job_offers jo, candidates c
WHERE jo.title = 'Site Reliability Engineer' AND c.email = 'clement.masson@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'REJECTED'
FROM job_offers jo, candidates c
WHERE jo.title = 'Software Engineer C#' AND c.email = 'oceane.marie@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'PENDING'
FROM job_offers jo, candidates c
WHERE jo.title = 'Backend Engineer Java / Kotlin' AND c.email = 'bastien.lemoine@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'ACCEPTED'
FROM job_offers jo, candidates c
WHERE jo.title = 'AI Research Engineer' AND c.email = 'lucie.philippe@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'PENDING'
FROM job_offers jo, candidates c
WHERE jo.title = 'iOS Developer Swift' AND c.email = 'dylan.marchand@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'REJECTED'
FROM job_offers jo, candidates c
WHERE jo.title = 'Game Developer C++' AND c.email = 'pauline.duval@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'ACCEPTED'
FROM job_offers jo, candidates c
WHERE jo.title = 'Cybersecurity Engineer' AND c.email = 'valentin.denis@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'PENDING'
FROM job_offers jo, candidates c
WHERE jo.title = 'Ingénieur Big Data' AND c.email = 'elsa.caron@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'PENDING'
FROM job_offers jo, candidates c
WHERE jo.title = 'Stagiaire Développeur React' AND c.email = 'quentin.maillot@gmail.com';

INSERT INTO applications (id, job_offer_id, candidate_id, status)
SELECT gen_random_uuid(), jo.id, c.id, 'PENDING'
FROM job_offers jo, candidates c
WHERE jo.title = 'Senior Kotlin Backend Engineer' AND c.email = 'victor.faure@gmail.com';

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id
FROM job_offers jo, skills s
WHERE jo.title = 'Senior Kotlin Backend Engineer'
  AND s.name IN ('Kotlin','Spring Boot','PostgreSQL','Docker','Clean Architecture');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id
FROM job_offers jo, skills s
WHERE jo.title = 'DevOps Engineer AWS'
  AND s.name IN ('AWS','Kubernetes','Docker','Terraform','CI/CD');

-- 50 new job_offer_skills

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Backend Developer Python' AND s.name IN ('Python','Django','FastAPI','PostgreSQL','Docker');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'SRE Engineer' AND s.name IN ('Kubernetes','Prometheus','Grafana','Terraform','AWS');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Data Engineer Spark' AND s.name IN ('Spark','Kafka','Python','Hadoop','PostgreSQL');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Mobile Developer Flutter' AND s.name IN ('Flutter','Dart','REST API','CI/CD');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Fullstack Developer React / Node.js' AND s.name IN ('React','Node.js','TypeScript','Express.js','MongoDB');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Cloud Architect AWS' AND s.name IN ('AWS','Terraform','Kubernetes','Docker','Microservices');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Machine Learning Engineer' AND s.name IN ('PyTorch','TensorFlow','Python','Docker','Pandas');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Java Backend Developer' AND s.name IN ('Java','Spring Boot','PostgreSQL','JUnit','Microservices');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'DevSecOps Engineer' AND s.name IN ('GitLab CI','SonarQube','Docker','Kubernetes','Terraform');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Angular Frontend Developer' AND s.name IN ('Angular','TypeScript','HTML/CSS','REST API');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Rust Systems Developer' AND s.name IN ('Rust','C++','Docker','CI/CD');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Scala Data Engineer' AND s.name IN ('Scala','Spark','Kafka','Cassandra','Docker');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Go Backend Developer' AND s.name IN ('Go','gRPC','Redis','Docker','Kubernetes');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'React Native Mobile Developer' AND s.name IN ('React Native','TypeScript','REST API','CI/CD');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Platform Engineer Kubernetes' AND s.name IN ('Kubernetes','Helm','Docker','ArgoCD','Terraform');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'PHP Backend Developer' AND s.name IN ('PHP','MySQL','Redis','Docker','CI/CD');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'QA Automation Engineer' AND s.name IN ('Cypress','Selenium','TypeScript','CI/CD');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'C++ Embedded Developer' AND s.name IN ('C++','Rust','CI/CD');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Fullstack Developer .NET / Angular' AND s.name IN ('.NET','C#','Angular','TypeScript','SQL Server');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Lead Developer Java' AND s.name IN ('Java','Spring Boot','Kafka','Microservices','Clean Architecture');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Consultant Cloud GCP' AND s.name IN ('GCP','Terraform','Kubernetes','Docker','Microservices');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Kotlin Android Developer' AND s.name IN ('Kotlin','CI/CD','REST API');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Frontend Engineer Svelte' AND s.name IN ('Svelte','TypeScript','HTML/CSS','Tailwind CSS');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Infrastructure Engineer Terraform' AND s.name IN ('Terraform','Ansible','AWS','GCP','Docker');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Senior Ruby on Rails Developer' AND s.name IN ('Ruby','Ruby on Rails','PostgreSQL','Redis','Docker');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'NestJS Backend Developer' AND s.name IN ('NestJS','TypeScript','PostgreSQL','Docker','REST API');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Elasticsearch Engineer' AND s.name IN ('Elasticsearch','Python','Docker','Kafka');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Site Reliability Engineer' AND s.name IN ('Kubernetes','Prometheus','Grafana','GCP','Docker');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Software Engineer C#' AND s.name IN ('C#','.NET','Azure','Docker','Microservices');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Backend Engineer Java / Kotlin' AND s.name IN ('Java','Kotlin','AWS','DynamoDB','Microservices');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'AI Research Engineer' AND s.name IN ('PyTorch','Python','Pandas','Docker','Spark');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'iOS Developer Swift' AND s.name IN ('Swift','CI/CD','REST API');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Fullstack Developer Next.js' AND s.name IN ('Next.js','React','TypeScript','GraphQL','Docker');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Fintech Backend Developer Go' AND s.name IN ('Go','gRPC','Kafka','PostgreSQL','Docker');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Chatbot Developer Python' AND s.name IN ('Python','FastAPI','Docker','REST API');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'ERP Developer Java' AND s.name IN ('Java','Spring Boot','PostgreSQL','JUnit');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'IoT Engineer Python / C++' AND s.name IN ('Python','C++','Docker','MQTT');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Data Scientist Python' AND s.name IN ('Python','Pandas','TensorFlow','Spark');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Game Developer C++' AND s.name IN ('C++','CI/CD');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = '3D Simulation Developer' AND s.name IN ('C++','Python','Docker');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Cybersecurity Engineer' AND s.name IN ('Python','Docker','Kubernetes','CI/CD');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'API Developer Spring Boot' AND s.name IN ('Java','Spring Boot','Spring Security','PostgreSQL','REST API');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Data Platform Engineer' AND s.name IN ('Hadoop','Spark','Kafka','Python','AWS');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Consultant Technique Kotlin' AND s.name IN ('Kotlin','Spring Boot','Microservices','Docker','Clean Architecture');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Développeur Quarkus' AND s.name IN ('Java','Quarkus','Docker','Kubernetes','Microservices');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Frontend Developer Vue.js' AND s.name IN ('Vue.js','Nuxt.js','TypeScript','HTML/CSS','Tailwind CSS');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Ingénieur Big Data' AND s.name IN ('Kafka','Cassandra','Spark','Java','Docker');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Développeur RPA Python' AND s.name IN ('Python','PostgreSQL','REST API');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Stagiaire Développeur React' AND s.name IN ('React','TypeScript','HTML/CSS','REST API');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Alternant DevOps' AND s.name IN ('GitHub Actions','Docker','CI/CD');

INSERT INTO job_offer_skills (job_offer_id, skill_id)
SELECT jo.id, s.id FROM job_offers jo, skills s
WHERE jo.title = 'Fullstack Developer Vue / Java' AND s.name IN ('Vue.js','Java','Spring Boot','TypeScript','PostgreSQL');

