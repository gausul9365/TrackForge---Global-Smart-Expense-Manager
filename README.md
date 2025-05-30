

# TrackForge – Global Smart Expense Manager

TrackForge is a secure, scalable backend system built using Java and Spring Boot to help users manage and track daily expenses. It supports JWT-based authentication, real-time currency conversion, and filtered data reporting—making it ideal for real-world financial and Human Capital applications.

## Tech Stack
- **Backend:** Java, Spring Boot, Spring Security
- **Database:** MySQL, JPA/Hibernate
- **Auth:** JWT (JSON Web Token), Role-Based Access Control
- **API Tools:** REST APIs, Postman
- **3rd-Party Integrations:** ExchangeRate-API, Mailgun (Optional)

##  Key Features
-  Secure User Registration and Login with JWT Auth
-  Add, Edit, Delete, and View Expenses by category/date/amount
-  Real-Time Currency Conversion (INR → USD/EUR)
-  Monthly Totals and Category-Wise Expense Summary
-  Filter expenses by date range, tags, or amount
-  (Optional) Auto-send weekly reports via Mailgun email service

## 📁 Project Structure
  controller/ - Pure REST interface
  entity/ - Maps directly to DB tables
  repository/ - Data access (JPA magic lives here)
  service/ - Handles logic (filters, reports, calculations)
  config/ - Security setup, beans, CORS, etc.
  TrackForgeApplication.java



  ## Description:
  •	Built scalable, secure REST APIs for user and expense management using Spring Boot and Java
  •	Applied role-based access control with JWT token system to manage login and user-level permissions
  •	Designed 10+ RESTful endpoints to handle expense creation, update, delete, filtering, and summary logic
  •	Integrated real-time currency conversion using ExchangeRate-API (INR to USD/EUR) for global reporting
  •	Created custom filters to search expenses by date, category, or amount range with backend processing
  •	Modular backend architecture using Controller-Service-Repository pattern, fully tested using Postman
