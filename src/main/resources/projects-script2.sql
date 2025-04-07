-- Show table data 
SELECT * FROM project; 
SELECT * FROM material;
SELECT * FROM step; 
SELECT * FROM project_category;
SELECT * FROM category;


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



-- Insert manually into project tables
INSERT INTO category (category_name) VALUES ('Doors and Windows');

SELECT * FROM project; 

INSERT INTO material (project_id, material_name, num_required, cost)
VALUES (2, '2-inch screws', 20, 1.09);

INSERT INTO step (project_id, step_text, step_order)
VALUES (2, 'Screw door hangers on the top and bottom of each side of the door frame', 1);

INSERT INTO project_category (project_id, category_id)
VALUES (2, 2);