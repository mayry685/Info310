-- Insert test data into Account table
INSERT INTO Account (AccountCode, FirstName, LastName, Username, Password, Email, Status)
VALUES
    ('doejo123', 'John', 'Doe', 'john_doe', 'password123', 'john@example.com', 'Active'),
    ('smija456', 'Jane', 'Smith', 'jane_smith', 'secret456', 'jane@example.com', 'Active'),
    ('johal@$$', 'Alice', 'Johnson', 'alice_johnson', 'p@$$w0rd', 'alice@example.com', 'Inactive');

-- Insert test data into Course table
INSERT INTO Course (CourseCode, CourseName, CourseDescription)
VALUES
    ('MATH130', 'Mathematics', 'Basic mathematics course'),
    ('PHYS130', 'Physics', 'Introduction to physics');

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
INSERT INTO Events (StartDate, EndDate, EventName, EventDescription, Location, CourseID, AccountID, Completed)
VALUES
    ('2024-05-10', '2024-05-12', 'Conference', 'Annual conference', 'New York', 'C1000', null, true),
    ('2024-06-15', '2024-06-18', 'Workshop', 'Hands-on workshop', 'Los Angeles', null, 'AC100000', false),
    ('2024-05-02 08:00:00', '2024-05-02 09:00:00', 'Assessment', 'Maths assessment', 'The Building', 'C1000', null, true),
    ('2024-05-01 18:00:00', '2024-05-01 22:00:00', 'Study', 'Study for the maths assessment', 'The Library', null, 'AC100000', false);


