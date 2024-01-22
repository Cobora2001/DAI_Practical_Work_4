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

The API was tested using an Ubuntu terminal and the curl command line tool. The following commands can be used to interact with the API that way:

#### GET /dashboard

Returns a list of all movies in the database.

```shell
curl.exe -i -X GET http://127.0.0.1:8080/dashboard/
```

Please do note that you can put a slash at the end of the URL, but it is not necessary. That will be the case for all the following commands as well.

#### GET /dashboard/{filmId}

Returns a specific movie from the database.

```shell
curl.exe -i -X GET http://127.0.0.1:8080/dashboard/movies/{filmId}
```

#### GET /dashboard/{filmId}/reviews

Returns a list of all reviews for a specific movie.

```shell
curl.exe -i -X GET http://127.0.0.1:8080/dashboard/movies/{filmId}/reviews/
```

#### GET /dashboard/{filmId}/reviews/{reviewId}

Returns a specific review for a specific movie.

```shell
curl.exe -i -X GET http://127.0.0.1:8080/dashboard/{filmId}/reviews/{reviewId}
```

#### POST /dashboard

Adds a new movie to the database.

```shell
curl.exe -i -X POST -d '{"title":"SomeTitleIWant","description":"The description for my film","genres":["FirstGenre","SecondGenre"]}' http://127.0.0.1:8080/dashboard/
```

Do note that the genres field can be left with no content (like this: "genres":[]). But in that case, you may want to add some genres later on or just use the genre "Other".

Genres are not strings, they are an object from an enum. The possible genres are: Action, Adventure, Comedy, Crime, Drama, Fantasy, Historical, Horror, Mystery, Philosophical, Political, Romance, Saga, Satire, ScienceFiction, Thriller, Urban, Western, Other.

#### POST /dashboard/{filmId}/reviews

Adds a new review for a specific movie.

```shell
curl.exe -i -X POST -d '{"rating":number,"comment":"My comment goes here"}' http://127.0.0.1:8080/dashboard/{filmId}/reviews/
```

Number is an integer between 1 and 5.

#### PUT /dashboard/{filmId}

Updates a specific movie in the database.

```shell
curl.exe -i -X PUT -d '{"title":"My new Title","description":"My new description","genres":["MyNewGenre"]}' http://127.0.0.1:8080/dashboard/{filmId}
```

#### PUT /dashboard/{filmId}/reviews/{reviewId}

Updates a specific review for a specific movie.

```shell
curl.exe -i -X PUT -d '{"rating":newRating,"comment":"My new comment"}' http://127.0.0.1:8080/dashboard/{filmId}/reviews/{reviewId}
```

#### DELETE /dashboard/{filmId}

Deletes a specific movie from the database.

```shell
curl.exe -i -X DELETE http://127.0.0.1:8080/dashboard/{filmId}
```

#### DELETE /dashboard/{filmId}/reviews/{reviewId}

Deletes a specific review for a specific movie.

```shell
curl.exe -i -X DELETE http://127.0.0.1:8080/dashboard/{filmId}/reviews/{reviewId}
```

### Example

```shell
# Tests:

# The ID is different depending on the database

# Get all film
curl.exe -i -X GET http://127.0.0.1:8080/dashboard/

# Add a new film
curl.exe -i -X POST -d '{"title":"LaPlaneteDesSinge","description":"Revolte%20des%20Singes","genres":["Action"]}' http://127.0.0.1:8080/dashboard/

# Get a film
curl.exe -i -X GET http://127.0.0.1:8080/dashboard/3

# Correct a film
curl.exe -i -X PUT -d '{"title":"La Planete Des Singe","description":"Revolte des Singes","genres":["Action"]}' http://127.0.0.1:8080/dashboard/3

# Get a film
curl.exe -i -X GET http://127.0.0.1:8080/dashboard/3

# Add a review to the film
curl.exe -i -X POST -d '{"rating":1,"comment":"Not Enough Monkeys"}' http://127.0.0.1:8080/dashboard/3/reviews/

# Get the reviews of the film
curl.exe -i -X GET http://127.0.0.1:8080/dashboard/3/reviews

# Update the review
curl.exe -i -X PUT -d '{"rating":5,"comment":"Veni Vidi Vici"}' http://127.0.0.1:8080/dashboard/3/reviews/1

# Get the review of the film
curl.exe -i -X GET http://127.0.0.1:8080/dashboard/3/reviews/1

# Delete the review
curl.exe -i -X DELETE http://127.0.0.1:8080/dashboard/3/reviews/1

# Delete the film "La Planète des Singes"
curl.exe -i -X DELETE http://127.0.0.1:8080/dashboard/3

# Get the film
curl.exe -i -X GET http://127.0.0.1:8080/dashboard/3

# Add the film "La Planète des Singes" again
curl.exe -i -X POST -d '{"title":"La Planete Des Singes","description":"Revolte des Singes","genres":["Action"]}' http://127.0.0.1:8080/dashboard/

# Get it one last time
curl.exe -i -X GET http://127.0.0.1:8080/dashboard/4
```

Expected output (In our case, we had added 2 movies before doing this test):

```txt

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

We published our application on GitHub Container Registry. <URL>

To do so, we renamed our image according to the following format:
```
ghcr.io/<username>/<image>:<tag>. 
```

Then, we published our image using the following command:
```
docker push ghcr.io/<username>/<image>:<tag> 
```

You can retrieve our image on GitHub using the command:
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