# 🎓 Scholarship Management System (Project Horizon)

**"Broadening Horizons, Shaping Your Future"**

## 📚 Overview  
The **Scholarship Management System** is a JavaFX desktop application designed to simplify and digitize the scholarship management process. It supports three main user roles:  
- **Administrators:** Manage scholarships, review applications, and generate reports.  
- **Students:** Apply for scholarships, track application status, and provide feedback.  
- **Guests:** Browse available scholarships without logging in.  

The project adheres to **GoF (Gang of Four)** and **GRASP (General Responsibility Assignment Software Patterns)** principles, ensuring clean architecture and maintainable code.  

---

## 🚀 Features  
### ✅ **Administrator Features:**  
- Login and dashboard overview  
- Add, edit, and delete scholarships  
- Approve or reject applications  
- Generate detailed reports  

### ✅ **Student Features:**  
- View and apply for scholarships  
- Track application status  
- Receive real-time notifications  
- Submit feedback  

### ✅ **Guest Features:**  
- Browse scholarships  
- View scholarship details  

---

## 🛠️ **Technologies Used:**  
- **JavaFX:** Graphical User Interface  
- **Java:** Core business logic  
- **SQL Server:** Database management  
- **JDBC:** Database connectivity  
- **FXML:** UI Layout  

---

## 🗂️ **Project Structure:**  
- **Controllers:** Handle interactions between UI and business logic  
- **Models:** Represent core data entities  
- **Utilities:** Reusable components (e.g., DatabaseUtil)  
- **Views:** FXML and CSS files for UI  

---

## 🧠 **Design Patterns Implemented:**  
- **Singleton Pattern:** Database connection management  
- **Observer Pattern:** Real-time notifications for students  
- **Factory Method Pattern:** Simplifies object creation logic  
- **Controller (GRASP):** Mediates between UI and business logic  
- **High Cohesion & Low Coupling:** Enhances code maintainability  

---

## 📊 **Database Schema:**  
Key tables include:  
- `Users`  
- `Scholarships`  
- `Applications`  
- `Notifications`  
- `Feedback`  
- `Logs`  
- `Bookmarks`  

CREATE TABLE Users (
    UserID INT PRIMARY KEY IDENTITY(1,1),
    UserName VARCHAR(255) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Email VARCHAR(255) UNIQUE NOT NULL,
    Role VARCHAR(255) NOT NULL CHECK (Role IN ('Student', 'Admin'))
);
CREATE TABLE Scholarships (
    ScholarshipID INT PRIMARY KEY IDENTITY(1,1),
    ScholarshipName VARCHAR(255) NOT NULL,
    ProviderName VARCHAR(255) NOT NULL,
    Description TEXT NOT NULL,
    EligibilityCriteria TEXT,
    ApplicationDeadline DATETIME NOT NULL,
    ApplicationRequirements TEXT,
    Level VARCHAR(255) NOT NULL CHECK (Level IN ('BS', 'MS', 'PhD')),
    Country VARCHAR(255) NOT NULL,
    ViewCount INT DEFAULT 0,
    ApplicationCount INT DEFAULT 0
);
CREATE TABLE Applications (
    ApplicationID INT PRIMARY KEY IDENTITY(1,1),
    ScholarshipID INT NOT NULL,
    UserID INT NOT NULL,
    FullName VARCHAR(255) NOT NULL,
    FatherName VARCHAR(255),
    Phone VARCHAR(255),
    Address VARCHAR(255),
    DateOfBirth DATETIME,
    University VARCHAR(255),
    DegreeProgram VARCHAR(255) NOT NULL CHECK (DegreeProgram IN ('BS', 'MS', 'PhD')),
    CGPA FLOAT,
    InterMarks FLOAT,
    MatricMarks FLOAT,
    AcademicAchievements TEXT,
    StatementOfPurpose TEXT,
    Extracurricular TEXT,
    CNIC VARCHAR(255) NOT NULL,
    Status VARCHAR(255) NOT NULL CHECK (Status IN ('Submitted', 'Under Review', 'Approved', 'Rejected')),
    IsApproved Bit DEFAULT 0,
    SubmissionDate DATETIME NOT NULL,
    ReviewDate DATETIME,
    StatusUpdateDate DATETIME,
    ReviewerComments TEXT,
    FOREIGN KEY (ScholarshipID) REFERENCES Scholarships(ScholarshipID) ON DELETE CASCADE,
    FOREIGN KEY (UserID) REFERENCES Users(UserID) ON DELETE CASCADE
);
CREATE TABLE Notifications (
    NotificationID INT PRIMARY KEY IDENTITY(1,1),
    UserID INT NOT NULL,
    Event TEXT NOT NULL,
    Message TEXT NOT NULL,
    SentAt DATETIME NOT NULL,
    IsRead Bit DEFAULT 0,
    FOREIGN KEY (UserID) REFERENCES Users(UserID) ON DELETE CASCADE
);
CREATE TABLE Feedback (
    FeedbackID INT PRIMARY KEY IDENTITY(1,1),
    UserID INT NOT NULL,
    FeedbackType VARCHAR(255) NOT NULL,
    FeedbackText TEXT NOT NULL,
    SubmittedAt DATETIME NOT NULL,
    AdminResponse TEXT,
    FOREIGN KEY (UserID) REFERENCES Users(UserID) ON DELETE CASCADE
);

-- Add ScholarshipName column
ALTER TABLE Feedback
ADD ScholarshipName VARCHAR(255);
-- Add Rating column
ALTER TABLE Feedback
ADD Rating FLOAT CHECK (Rating BETWEEN 1 AND 5); -- Ensure rating is between 1 and 5
CREATE TABLE Documentation (
    DocumentationID INT PRIMARY KEY IDENTITY(1,1),
    UserID INT NOT NULL,
    FileName VARCHAR(255) NOT NULL,
    FilePath VARCHAR(255) NOT NULL,
    ActionTime DATETIME NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID) ON DELETE CASCADE
);
CREATE TABLE Logs (
    LogID INT PRIMARY KEY IDENTITY(1,1),
    UserID INT NOT NULL,
    Action VARCHAR(255) NOT NULL,
    ActionTime DATETIME NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID) ON DELETE CASCADE
);
CREATE TABLE Bookmarks (
    BookmarkId INT PRIMARY KEY IDENTITY(1,1),
    UserId INT NOT NULL,
    ScholarshipId INT NOT NULL,
    CreatedAt DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (UserId) REFERENCES Users(UserId) ON DELETE CASCADE,
    FOREIGN KEY (ScholarshipId) REFERENCES Scholarships(ScholarshipId) ON DELETE CASCADE
);




