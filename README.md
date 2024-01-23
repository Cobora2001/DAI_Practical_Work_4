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
curl -i -X GET https://dai.timou.ch/dashboard/
```

Please do note that you can put a slash at the end of the URL, but it is not necessary. That will be the case for all the following commands as well.

#### GET /dashboard/{filmId}

Returns a specific movie from the database.

```shell
curl -i -X GET https://dai.timou.ch/dashboard/movies/{filmId}
```

#### GET /dashboard/{filmId}/reviews

Returns a list of all reviews for a specific movie.

```shell
curl -i -X GET https://dai.timou.ch/dashboard/movies/{filmId}/reviews/
```

#### GET /dashboard/{filmId}/reviews/{reviewId}

Returns a specific review for a specific movie.

```shell
curl -i -X GET https://dai.timou.ch/dashboard/{filmId}/reviews/{reviewId}
```

#### POST /dashboard

Adds a new movie to the database.

```shell
curl -i -X POST -d '{"title":"SomeTitleIWant","description":"The description for my film","genres":["FirstGenre","SecondGenre"]}' https://dai.timou.ch/dashboard/
```

Do note that the genres field can be left with no content (like this: "genres":[]). But in that case, you may want to add some genres later on or just use the genre "Other".

Genres are not strings, they are an object from an enum. The possible genres are: Action, Adventure, Comedy, Crime, Drama, Fantasy, Historical, Horror, Mystery, Philosophical, Political, Romance, Saga, Satire, ScienceFiction, Thriller, Urban, Western, Other.

#### POST /dashboard/{filmId}/reviews

Adds a new review for a specific movie.

```shell
curl -i -X POST -d '{"rating":number,"comment":"My comment goes here"}' https://dai.timou.ch/dashboard/{filmId}/reviews/
```

Number is an integer between 1 and 5.

#### PUT /dashboard/{filmId}

Updates a specific movie in the database.

```shell
curl -i -X PUT -d '{"title":"My new Title","description":"My new description","genres":["MyNewGenre"]}' https://dai.timou.ch/dashboard/{filmId}
```

#### PUT /dashboard/{filmId}/reviews/{reviewId}

Updates a specific review for a specific movie.

```shell
curl -i -X PUT -d '{"rating":newRating,"comment":"My new comment"}' https://dai.timou.ch/dashboard/{filmId}/reviews/{reviewId}
```

#### DELETE /dashboard/{filmId}

Deletes a specific movie from the database.

```shell
curl -i -X DELETE https://dai.timou.ch/dashboard/{filmId}
```

#### DELETE /dashboard/{filmId}/reviews/{reviewId}

Deletes a specific review for a specific movie.

```shell
curl -i -X DELETE https://dai.timou.ch/dashboard/{filmId}/reviews/{reviewId}
```

### Example

```shell
# Tests:

# The ID is different depending on the database

# Get all film
curl -i -X GET https://dai.timou.ch/dashboard/

# Add a new film
curl -i -X POST -d '{"title":"LaPlaneteDesSinge","description":"Revolte%20des%20Singes","genres":["Action"]}' https://dai.timou.ch/dashboard/

# Get a film
curl -i -X GET https://dai.timou.ch/dashboard/3

# Correct a film
curl -i -X PUT -d '{"title":"La Planete Des Singe","description":"Revolte des Singes","genres":["Action"]}' https://dai.timou.ch/dashboard/3

# Add a review to the film
curl -i -X POST -d '{"rating":1,"comment":"Not Enough Monkeys"}' https://dai.timou.ch/dashboard/3/reviews/

# Get the reviews of the film
curl -i -X GET https://dai.timou.ch/dashboard/3/reviews

# Update the review
curl -i -X PUT -d '{"rating":5,"comment":"Veni Vidi Vici"}' https://dai.timou.ch/dashboard/3/reviews/1

# Delete the review
curl -i -X DELETE https://dai.timou.ch/dashboard/3/reviews/1

# Delete the film "La Planète des Singes"
curl -i -X DELETE https://dai.timou.ch/dashboard/3

# Get the film
curl -i -X GET https://dai.timou.ch/dashboard/3

# Add the film "La Planète des Singes" again
curl -i -X POST -d '{"title":"La Planete Des Singes","description":"Revolte des Singes","genres":["Action"]}' https://dai.timou.ch/dashboard/

# Get it one last time
curl -i -X GET https://dai.timou.ch/dashboard/4
```

Expected output (In our case, we had added 2 movies before doing this test, so our output represents that):

```txt
200 OK
Date: Mon, 22 Jan 2024 20:49:45 GMT
Content-Type: application/json
Content-Length: 298

[{"id":1,"title":"L'Arch de Noe","description":"Un film sur un element majeur de la religion","genres":["Animation"],"nbReviews":3,"meanReviews":3.3333333333333335},{"id":2,"title":"San Gohan","description":"Un film de dragon ball","genres":["Animation","Fantasy"],"nbReviews":0,"meanReviews":3.0}]

---------------------------------------

201 Created
Date: Mon, 22 Jan 2024 20:50:56 GMT
Content-Type: application/json
Content-Length: 127

{"id":3,"title":"LaPlaneteDesSinge","description":"Revolte%20des%20Singes","genres":["Action"],"nbReviews":0,"meanReviews":3.0}

---------------------------------------

200 OK
Date: Mon, 22 Jan 2024 20:51:24 GMT
Content-Type: application/json
Content-Length: 127

{"id":3,"title":"LaPlaneteDesSinge","description":"Revolte%20des%20Singes","genres":["Action"],"nbReviews":0,"meanReviews":3.0}

---------------------------------------

200 OK
Date: Mon, 22 Jan 2024 20:51:48 GMT
Content-Type: application/json
Content-Length: 126

{"id":3,"title":"La Planete Des Singe","description":"Revolte des Singes","genres":["Action"],"nbReviews":0,"meanReviews":3.0}

---------------------------------------

201 Created
Date: Mon, 22 Jan 2024 20:53:28 GMT
Content-Type: application/json
Content-Length: 48

{"id":0,"rating":1,"comment":"NotEnoughtMonkey"}

---------------------------------------

200 OK
Date: Mon, 22 Jan 2024 20:53:52 GMT
Content-Type: application/json
Content-Length: 50

[{"id":0,"rating":1,"comment":"NotEnoughtMonkey"}]

---------------------------------------

200 OK
Date: Mon, 22 Jan 2024 20:54:36 GMT
Content-Type: application/json
Content-Length: 46

{"id":0,"rating":5,"comment":"Veni Vidi Vici"}

---------------------------------------

204 No Content
Date: Mon, 22 Jan 2024 20:55:27 GMT
Content-Type: text/plain

---------------------------------------

204 No Content
Date: Mon, 22 Jan 2024 20:56:04 GMT
Content-Type: text/plain

---------------------------------------

404 Not Found
Date: Mon, 22 Jan 2024 20:56:29 GMT
Content-Type: text/plain
Content-Length: 9

Not Found

---------------------------------------

201 Created
Date: Mon, 22 Jan 2024 20:57:00 GMT
Content-Type: application/json
Content-Length: 127

{"id":4,"title":"La Planete Des Singes","description":"Revolte des Singes","genres":["Action"],"nbReviews":0,"meanReviews":3.0}

---------------------------------------

200 OK
Date: Mon, 22 Jan 2024 20:57:22 GMT
Content-Type: application/json
Content-Length: 127

{"id":4,"title":"La Planete Des Singes","description":"Revolte des Singes","genres":["Action"],"nbReviews":0,"meanReviews":3.0}
```

## Team Composition and Roles
Our group is composed of Thomas Vuilleumier, Sebastian Diaz, Arthur Menétrey and Lionel Pollien. Here are the roles and responsibilities that we have assigned ourselves :

- Thomas Vuilleumier : Created the program with the requests.
- Sebastian Diaz : Served as the project supervisor, managing the communication with the HEIG-VD staff, and did the testing.
- Arthur Menétrey : Created the Dockerfile and the docker-compose.yml file, and installed the application on the server.
- Lionel Pollien : Created the documentation and helped the other members of the group.

## Server Installation and Configuration

If you want to use you app outside a local network you will need to deploy a server that can be accessible from the internet.
The server 80 and 443 ports should be opened in order for the http and https to be received.

When your server is ready, you can clone this repository and run it using docker compose or use our docker image.
These topics will be depicted in the next chapters.
    
### Guide for deploying the application

To deploy our image, we first need to connect to our server (which in our case is a virtual machine) using the command (in a command prompt):

```
ssh heiguser@10.190.132.64
```

Then the virtual machine will ask you to enter a password in order to continue.

Note: In our case, we must be connected to the HEIG VPN.

Next, we will need to clone the repository using the git clone command.

You will then need to create a folder named 'secrets' containing the file "auth-users.txt".
We strongly advise you to secure this file as this will store our traefk dashboard password.
This can be done using the following commands :

```
mkdir secrets
htpasswd -c secrets/auth-users.txt admin
```

To start our application, we use docker-compose by using this command:

```
sudo docker compose up -d
```

Result :

```
heiguser@IICT-MV364-DAI:~/DAI_Practical_Work_4% sudo docker compose up
[+] Running 3/3
 ✔ Network dai_practical_work_4_default      Created                                                               0.1s
 ✔ Container dai_practical_work_4-traefik-1  Created                                                               0.1s
 ✔ Container dai_practical_work_4-api-1      Created                                                               0.1s
```

Our application is then deployed and usable.

### Accessing the application
To access our application after deploying it, it's very simple. Just open a browser and go to the following website

```
https://dai.timou.ch/dashboard/
```

## DNS and Domain Name Configuration
The DNS zone configuration depends on the DNS provider you will use. In our case we accessed the domain name provider's website (Infomaniak), and configured the DNS through the admin dashboard. We added two A records pointing to our server. 
One of them will be used for Traefik dashboard and the other one for the api.
We are using A records because our server's public address is using IPv4.
Additionally, it may be important to note that this setup requires some basic knowledge of DNS management. We chose Infomaniak as it offers reliable DNS hosting services.

In the context of our application, configuring the .env file correctly is vital as it includes environment-specific variables which Traefik uses to route requests to the appropriate containers.
This setup ensures that our web application is accessible via the chosen domain name and that the routing is handled efficiently.

For the app to work properly you may need to change these environments variables to suit your needs :

- API_DOMAIN_NAME => The api fully qualified domain name

- TRAEFIK_ACME_EMAIL => The email address provided to Let's Encrypt

- TRAEFIK_DOMAIN_NAME => The traefik dashboard fully qualified domain name

- TRAEFIK_IMAGE_VERSION => The image version of traefik (you can keep it to "latest" by default)


## Building and Publishing with Docker

### Using Docker to build the application
On a un dockerfile pour build l'image

docker build nomimage


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
