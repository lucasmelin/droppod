# DropPod
A podcasting application built in JavaEE usinng Servlets/JSP, AJAX and MySQL.

## Purpose

Droppod was a 5 person team project for my CST8218 Web Enterprise Applications class at Algonquin College.

This project was an opportunity to learn how to build scalable, enterprise-ready web applications, with a design focused around internationalization and horizontal scalability.

## What we learned
To achieve site-wide translation, we used a combination of resource bundles for menu strings, and Google Translation API for dynamic content such as podcast names and descriptions. In order to minimize the number of requests to the Google API, the translated strings were stored in the database so that future requests could be served using only the database.

# Screenshots


![Login Screen](screenshots/login.png?raw=true "Login Screen")

![Create Account (French)](screenshots/createaccountfrench.png?raw=true "Create Account (French)")

![Droppod Main Page](screenshots/droppod.png?raw=true "Droppod Main Page")

![Viewing a single podcast](screenshots/emojimap.png?raw=true "Viewing a single podcast")

![Recommended network graph](screenshots/recommended.png?raw=true "Recommended network graph")

![Admin console](screenshots/admin.png?raw=true "Admin console")

![Admin console editing a user](screenshots/adminuseredit.png?raw=true "Admin console editing a user")
