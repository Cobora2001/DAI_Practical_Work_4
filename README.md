# <u>DAI-Practical work 4</u>

## Table of Contents
1. [Introduction](#introduction)
2. [Team Composition and Roles](#team-composition-and-roles)
3. [Server Installation and Configuration](#server-installation-and-configuration)
4. [Deployment and Usage](#deployment-and-usage)
5. [DNS Zone Configuration](#dns-zone-configuration)
6. [Building and Publishing with Docker](#building-and-publishing-with-docker)
7. [Interacting with the Application via CURL](#interacting-with-the-application-via-curl)

## Introduction

### Description of the web application
Welcome to our film review application. We developed this project as part of the Internet Application Development course at HEIG-VD. The goal of this project was to create a web application that utilizes the HTTP protocol. The web application is defined by an application protocol interface (API) and will be deployed on a virtual machine, accessible over the internet via a domain name. Interaction with the application can be done through a web browser and/or a command line tool, using commands such as curl.

### Purpose and main features
The purpose of our application is to provide a platform for sharing personal opinions about movies.

Features of Our Application:
- Read Existing Movie Reviews: Browse through a collection of reviews for various films.
- Write Your Own Review: Share your thoughts and critique on any movie.
- Rate a Movie: Provide a rating for the films you've watched.

### Documented API

#### GET /movies

Returns a list of all movies in the database.
```shell
curl.exe -i -X GET http://127.0.0.1:8080/dashboard/
```

#### GET /movies/{id}

Returns a specific movie from the database.
```shell
curl.exe -i -X GET http://127.0.0.1:8080/dashboard/movies/{id}
```

## Team Composition and Roles
Our group is composed of Thomas Vuilleumier, Sebastian Diaz, Arthur Menétrey and Lionel Pollien. Here are the roles and responsibilities that we have assigned ourselves :

- Thomas Vuilleumier : Created the program with the requests.
- Sebastian Diaz : Served as the project supervisor, managing the communication with the HEIG-VD staff, and did the testing.
- Arthur Menétrey : Created the Dockerfile and the docker-compose.yml file, and installed the application on the server.
- Lionel Pollien : Created the documentation and helped the other members of the group.

## Server Installation and Configuration
    Necessary prerequisites
    Detailed installation steps
    Server configuration and environment setup

## Deployment and Usage
    Guide for deploying the application
    Starting and accessing the application
    User interface and navigation

## DNS Zone Configuration
    Explanation of necessary DNS configuration
    Instructions for linking the domain to the application

## Building and Publishing with Docker
### Using Docker to build the application
### Publishing the application with Docker

We published our application on Github Container Registry. <URL>

To do so, we renamed our image according to the following format:
```
ghcr.io/<username>/<image>:<tag>. 
```

Then, we published our image using the following command:
```
docker push ghcr.io/<username>/<image>:<tag> 
```

You can retrieve our image on Github using the command:
```
docker pull ghcr.io/<username>/<image>:<tag>
```
### Instructions for Docker images and containers

## Interacting with the Application via CURL
    Examples of interactions with the API
    CURL commands and their expected responses
    Common use case scenarios

## Conclusion
(eventually)