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

Refer to the `docs/database-schema.sql` file for full schema details.  

---

## 📸 **UI Screenshots:**  
- Login Screen  
- Admin Dashboard  
- Student Dashboard  
- Guest Scholarship Viewer  

Screenshots can be found in the `/docs/screenshots/` directory.  

---

## 📥 **Setup Instructions:**  
1. Clone the repository:  
   ```bash
   git clone https://github.com/your-username/scholarship-manager-javafx.git
