# Doctor Appointment web application 🏥

This project was developed for the purpose of being presented as a Bachelor Thesis demo at the Faculty of Automatic Control and Computer Science within Politehnica National University of Science and Technology, Bucharest, Romania in 2024. 
I retain all rights to my source code and no one may reproduce, distribute, or create derivative works from my project.

## 📑 Table of Contents

**[📃 First Time Installation](#-first-time-installation)**<br>
**[📱Running The App](#-running-the-app)**<br>
**[₿ Features](#-features)**<br>

### ⚡ Technologies and Utilities

![JavaScript](https://img.shields.io/badge/-JavaScript-black?style=flat-square&logo=javascript)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Bootstrap](https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white)



## 📃 First Time Installation

- Make a copy of the repo in a location of your choosing
- Open a terminal inside of the code base and run `docker-compose up` to launch the Docker container containing the storage volume
- Installation Completed!

## 📱 Running the app
- Run the application using the IDE of your choice
- Access the application at `localhost:8080/` or any other default domain specified in the application properties.

## ₿ Features
- Responsive and very accesible design throughout the entire UI.
- Three types of user: NON-USER, USER and ADMIN with respective permissions.
- Relatively robust security due to the use of the Spring Security library.
- Making an appointment with any doctor present in the database, within the clinic and department previously selected.
- Personalized page containing the user's appointments that displays a table with details and possible actions.
- Management pages for the application's dynamic data accessible by users with an ADMIN role.
