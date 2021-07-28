# FoodDeliveryApp
Food Delivery App is app that will let you browse various restaurants and order food. Is is based on microservices architecture.





## System architecture

![image](https://user-images.githubusercontent.com/31183856/127387618-b9035390-c649-47df-805e-97948bf5ac36.png)

- **Frontend Service** </br> Is application written using Angular and Bootstrap to represent actual data for end user. 
- **Composite Service**
- **Restaurant Service**
- **Dish Service**
- **Order Service**


| Component  | Ports |
| ------------- | ------------- |
| composite-service  | 8080 (explosed)  |
| dish-service  | 8080 (inside docker)  |
| order-service  | 8080 (inside docker)  |
| restaurant-service  | 8080 (inside docker)  |
| frontend  | 4200 (Web server)  |
| mongoDb  | 27017  |
| MySQL  | 3306  |


## Running app with docker
### Prerequisites
- Docker
- Git

### Getting started
To get started run commands below.
````
git clone https://github.com/Marbys/FoodDeliveryApp.git
docker-compose up -d
````
Then wait untill everything is done and navigate to http://localhost:4200 in your browser.


