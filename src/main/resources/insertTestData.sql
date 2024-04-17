-- Insert test data into Account table
INSERT INTO Account (FirstName, LastName, Username, Password, Email, Status)
VALUES
    ('John', 'Doe', 'john_doe', 'password123', 'john@example.com', 'Active'),
    ('Jane', 'Smith', 'jane_smith', 'secret456', 'jane@example.com', 'Active'),
    ('Alice', 'Johnson', 'alice_johnson', 'p@$$w0rd', 'alice@example.com', 'Inactive');

-- Insert test data into Course table
INSERT INTO Course (CourseName, CourseDescription)
VALUES
    ('Mathematics', 'Basic mathematics course'),
    ('Physics', 'Introduction to physics');

-- Insert test data into CourseList table
INSERT INTO CourseList (CourseID, AccountID)
VALUES
    ('C1000', 'AC100000'),
    ('C1001', 'AC100000'),
    ('C1001', 'AC100001');

-- Insert test data into Assignment table
INSERT INTO Assignment (CourseID, AssignmentName, AssignmentDescription, DueDate, Weight)
VALUES
    ('C1000', 'Homework 1', 'Basic math problems', '2024-04-30', 10),
    ('C1001', 'Lab Report', 'Experiment analysis', '2024-05-05', 15);

-- Insert test data into Events table
INSERT INTO Events (StartDate, EndDate, EventName, EventDescription, Location, Completed)
VALUES
    ('2024-05-10', '2024-05-12', 'Conference', 'Annual conference', 'New York', true),
    ('2024-06-15', '2024-06-18', 'Workshop', 'Hands-on workshop', 'Los Angeles', false);