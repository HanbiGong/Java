# Java

This repository contains my APD545 course work in Java/JavaFX, including Workshops 1–7 and the final Hotel Reservation System milestone project.

- `Workshop-01/` ~ `Workshop-07/`: Weekly workshop tasks (separate mini projects)
- `Final Project/`: Final milestone project (Hotel Reservation System)

## Tech Stack
- Java: (e.g., 17)
- JavaFX: (e.g., 21)
- Build: Maven / Gradle (choose one)
- IDE: IntelliJ IDEA

## Workshop 1 – Electronic Devices 

**Focus:** Interfaces, Abstract Classes, Comparable, MVC

In this workshop, I designed and implemented an electronic device management application to demonstrate object-oriented programming concepts.

### Key Features
- Implemented interfaces (`IDeviceOperable`, `IDeviceMaintainable`) and abstract classes
- Created a device hierarchy including SmartPhone, Tablet, GamingConsole, SmartTV, and SmartSpeaker
- Used inheritance and polymorphism to model device behavior
- Applied `Comparable` / `Comparator` to sort devices by price
- Displayed the most expensive device and sorted devices in descending order
- Followed MVC design principles and clean modular code structure

## Workshop 2 – Vehicle Maintenance & Usage Management System

**Focus:** JavaFX, MVC, Data Structures

This workshop involved building a JavaFX desktop application to manage vehicle details, maintenance records, and usage logs.

### Key Features
- Developed a JavaFX application following the MVC design pattern
- Implemented model classes for vehicles, maintenance records, and usage logs
- Used `Map` and `ArrayList` to associate records with vehicles
- Designed forms using TextField, DatePicker, ComboBox, and TableView
- Implemented save, clear, and summary viewing functionality
- Displayed summarized data in a separate window based on user selection

## Workshop 3 – Auto Loan Application

**Focus:** JavaFX, Manual Dependency Injection, Repository Pattern

In this workshop, I developed a JavaFX-based auto loan application with authentication and loan calculation features.

### Key Features
- Implemented login and signup functionality with input validation
- Applied Manual Dependency Injection to wire controllers, services, and repositories
- Used the Repository Pattern for in-memory data management
- Designed multi-scene JavaFX UI using GridPane, HBox, and AnchorPane
- Implemented loan calculation logic and amortization schedules
- Used JavaFX properties and bindings for real-time UI updates

## Workshop 4 & 5 – Inventory Management System

**Focus:** JavaFX, MVC, Repository & Service Layers

This workshop focused on developing a full inventory management system with a graphical user interface.

### Key Features
- Built a JavaFX application with login, main, add/modify part, and add/modify product screens
- Followed MVC architecture with clear separation of concerns
- Implemented repository and service layers for managing parts and products
- Used dependency injection to manage controllers and services
- Added search, add, modify, and delete functionality for parts and products
- Implemented validation rules and user confirmation dialogs

## Workshop 6 & 7 – File and Database Persistence

**Focus:** Persistence, Serialization, Database Integration

This workshop extended the inventory management system by adding data persistence features.

### Key Features
- Implemented object serialization to save and load data from files
- Added functionality to persist data to a database
- Implemented loading data from both file storage and database
- Ensured data integrity across persistence operations
- Enhanced the existing JavaFX application without changing the UI design

## Final Milestone – Hotel Reservation System

**Focus:** JavaFX, MVC, DI, ORM, Design Patterns

The final milestone project is a complete desktop-based hotel reservation system that replaces a manual booking process.

### Key Features
- Developed a kiosk-based reservation flow with step-by-step user guidance
- Implemented admin module with authentication and role-based access
- Applied MVC architecture with service and repository layers
- Used ORM-backed repositories for database persistence
- Implemented multiple design patterns: Strategy, Observer, Factory, Decorator, Singleton
- Supported dynamic pricing, discounts, loyalty points, and add-on services
- Implemented feedback collection and tabular reporting with export options
- Added logging, validation, and secure password handling using BCrypt
