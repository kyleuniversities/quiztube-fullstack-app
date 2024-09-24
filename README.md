# QuizTube v1.1.0

The QuizTube Application (formerly called Quizzical) is a Spring Boot web application designed for users to be able to quiz
themselves by making their own quizzes. The app was developed using TypeScript React for
the frontend and Spring Boot for the backend.

&nbsp;

&nbsp;

# Release 1.1.0

Updated QuizTube Homepage quiz card UI.  Loading interface is smoother and quiz card UI interact on hover.  Utilized fade
left Transitions and Semantic UI React Loaders.  

&nbsp;

&nbsp;

## Note 1.1.0

Conducted a performance test on multiple API requests for homepage quizzes vs a singular request for a quiz catalog.  The
singular request performed slower than the original multiple API requests (an unexpected result).  More investigation will
be performed in future releases.

&nbsp;

&nbsp;

# Frontend Technologies Utilized

Throughout the development of this website, I incorporated various frontend technologies including:

- **TypeScript React:** Invoked the use of Typescript React classes, functions, and types.
- **Semantic UI:** Invoked Semantic UI as a framework on top of React for components.
- **React Markdown:** Invoked React Markdown to display MD files within components.
- **React Responsive:** Invoked React Responsive to adjust for different devices, such as iPhone usage
- **JWT Decode:** Invoked JWT Decode to decode JWT Tokens for user logins

&nbsp;

&nbsp;

# Practices Utilized in Frontend Development

Throughout the frontend development of this application, I incorporated various coding practices including:

- **Separating Functions and Components:** Separated request functions as their own section in the file infrastructure as services to help with organization
- **Code Reuse:** Several components are made as a product of two or more very similar components, such the AddModifyQuizPage, which acts as a parent to the similar AddQuizPage and EditQuizPage.
- **Code Organization:** Made user that all methods and code are neatly organized and readable
- **Unit Testing:** Made several unit tests for several utility helper classes.
- **Linting:** Ran linting regularly to make sure there were no wastefulness in symbol usage

&nbsp;

&nbsp;

&nbsp;

&nbsp;

# Backend Technologies Utilized

Throughout the development of this website, I incorporated various backend technologies including:

- **Spring Boot:** Invoked an Spring Boot Backend API.
- **Spring Security:** Invoked Spring Security to allow for authentication functionality.
- **Spring JDBC:** Invoked Spring JDBC to allow for SQL queries related to User.
- **MySQL Connector Java:** Invoked MySQL Connector Java to connect to RDS Database
- **JWT Token:** Invoked the use of JWT Tokens to provide secure authentication for users.
- **AWS S3 Client:** Invoked an AWS Client to be able to retrieve files from S3 via Spring Boot

&nbsp;

&nbsp;

# Practices Utilized during Backend Development

Throughout the development of this application, I incorporated various coding practices including:

- **Securing Endpoints:** Used Spring Security configurations to help permit endpoints or have endpoints require athentication
- **JWT Token Usage:** Required proper credentials using JWTs to ensure secure requests
- **Request Validation:** Supplied validation for users entering faulty text data or data conflicting with existing resources.
- **Proper Exception Handling:** Used a default exception handler to retrun an informative error response during an exception.
- **Unit Testing:** Made several unit tests for several utility helper classes.
- **Endpoint Testing:** Made several endpoint tests for the main API controllers.
- **Microservice Architecture:** Divided classes into several services rather than a single or few tightly couple classes

&nbsp;

&nbsp;

&nbsp;

&nbsp;

# Deployment

Throughout this project, I definitely learned a lot about deployment technologies. The technologies I gained experience in were Docker, Kubernetes, and Jenkins.

&nbsp;

&nbsp;

## Note 1.1.0

For cost optimization, the following CI / CD pipeline is deprecated.  The following CI / CD is used for released 1.0.0. 

&nbsp;

&nbsp;

## My CI/CD Pipeline

The step-by-step process for which my CI/CD pipeline is as follows:

1. Perform a Git Push on my "app/deployment" branch
2. Jenkins detects the change via SCM Polling and runs a build
3. Jenkins checks out the branch
4. Then Jenkins compiles the Spring Boot API application into a runnable jar file
5. Then Jenkins uses my supplied frontend Dockerfile to build and push the image to start the frontend app and start an NGINX server
6. Then Jenkins uses my supplied backend Dockerfile to build and push the image to import the backend jar file and start the backend app
7. Finally, Jenkins runs my supplied frontend and backend deployment YAML files for Kubernetes as well as the external dns deployment to have QuizTube host on a public domain

&nbsp;

&nbsp;

## Key Steps in Configuration

Setting up all the aspects in this CI/CD pipeline was not an easy feat (at least for me). I had to juggle many extra challenges such as finding a way to connect AWS EKS to RDS, and how would I incorporate utilizing my SSL certificates for my containers in EKS. Several challenging steps to learning these technologies in order to build the components for this pipeline include the following:

&nbsp;

**Setting up AWS EKS to Deploy at a Static Domain**

Deploying a public website on EKS was not widely documented. I had to closely follow two webpages and improvise along the way in order to set up the VPC (virtual private cloud) routes on AWS and configure the IAM roles to launch my EKS application publicly.

&nbsp;

**Setting up NGINX to Reverse Proxy Applications with SSL Certificates**

This challenge was again not widely documented. I had to improvise based on information I gathered from several sources. My solution involved deploying NGINX and the frontend together, while upstreaming the backend, and copying the SSL certificates into a folder I can point to for HTTPS.

&nbsp;

**Setting up Jenkins to Deploy with all the Required Components**

This project was, admittedly, the first time I had used Jenkins. I definitely had an experience trying to install it and getting it to work to incorporate Docker and Kubernetes. In the end, I found the ideal solution for me was to deploy Jenkins as a service on a fast-running Amazon Linux EC2 instance and install the necessary dependencies from there.

&nbsp;

&nbsp;

## Overall

There were several other challenges to this project as well, but fortunately I was able to pull through and deliver this website as QuizTube v1.1.0! Overall, I learned a lot from this project, and hope to help deploy good products in the future!

&nbsp;

&nbsp;

## Github Repositories

[https://github.com/kyleuniversities/quiztube-fullstack-app](https://github.com/kyleuniversities/quiztube-fullstack-app)

[https://github.com/kyleuniversities/quizzical-frontend-app-beta](https://github.com/kyleuniversities/quizzical-frontend-app-beta)

[https://github.com/kyleuniversities/quizzical-api-app-beta](https://github.com/kyleuniversities/quizzical-api-app-beta)
