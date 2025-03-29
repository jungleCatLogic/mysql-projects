DROP TABLE IF EXISTS project_category;
DROP TABLE IF EXISTS step;
DROP TABLE IF EXISTS material;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS category;

CREATE TABLE project (
    project_id INT NOT NULL AUTO_INCREMENT,
    project_name VARCHAR(128) NOT NULL,
    estimated_hours DECIMAL(7, 2),
    actual_hours DECIMAL(7, 2),
    difficulty INT,
    notes TEXT,
    PRIMARY KEY (project_id)
);

CREATE TABLE category (
    category_id INT NOT NULL AUTO_INCREMENT,
    category_name VARCHAR(128) NOT NULL,
    PRIMARY KEY (category_id)
);

CREATE TABLE step (
    step_id INT NOT NULL AUTO_INCREMENT,
    project_id INT NOT NULL,
    step_text TEXT NOT NULL,
    step_order INT NOT NULL,
    PRIMARY KEY (step_id),
    FOREIGN KEY (project_id) REFERENCES project (project_id) ON DELETE CASCADE
);

CREATE TABLE material (
    material_id INT NOT NULL AUTO_INCREMENT,
    project_id INT NOT NULL,
    material_name VARCHAR(128) NOT NULL,
    num_required INT,
    cost DECIMAL(7, 2),
    PRIMARY KEY (material_id),
    FOREIGN KEY (project_id) REFERENCES project (project_id) ON DELETE CASCADE
);

CREATE TABLE project_category (
    project_id INT NOT NULL,
    category_id INT NOT NULL,
    FOREIGN KEY (project_id) REFERENCES project (project_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES category (category_id) ON DELETE CASCADE,
    UNIQUE KEY (project_id, category_id)
);

-- Add some data 

-- Insert test project
INSERT INTO project (project_name, estimated_hours, actual_hours, difficulty, notes)
VALUES ('Test Project', 10.5, 0, 3, 'This is a test project for deletion testing');

-- Get the project ID (last insert ID)
SET @project_id = LAST_INSERT_ID();

-- Insert material for test project
INSERT INTO material (project_id, material_name, num_required, cost)
VALUES (@project_id, 'Test Material', 5, 10.50);

-- Insert step for test project
INSERT INTO step (project_id, step_text, step_order)
VALUES (@project_id, 'Test Step 1', 1);

-- Insert category
INSERT INTO category (category_name)
VALUES ('Test Category');

-- Get the category ID (last insert ID)
SET @category_id = LAST_INSERT_ID();

-- Create project-category relationship
INSERT INTO project_category (project_id, category_id)
VALUES (@project_id, @category_id);
