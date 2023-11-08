# Portfolio Analysis Web Application (IS442 G1T2)

This project is a portfolio analysis web application, where users can search for stocks, add it to their portfolios and view its performance over weeks, months, quarters or years. Users can also view the market exposure of each portfolio based on Industry, Country or Currency.

### Team Members

| Members               | School Email     | 
| --------------------- | ---------------- |
| Alexis Seow Li Ting | alexis.seow.2019@scis.smu.edu.sg | 
| Goh Choon Shane     | csgoh.2021@scis.smu.edu.sg | 
| Muhammad Faez Bin Abdul Latiff | muhammadal.2021@scis.smu.edu.sg | 
| Toh Ming Jun | mingjun.toh.2021@scis.smu.edu.sg | 
| Teo Jun Wee Ryan | ryan.teo.2021@scis.smu.edu.sg | 
| Tjandra Putra | tjandrap.2021@scis.smu.edu.sg  | 

## Technologies Used

| Frontend               | Backend   | Others |
| --------------------- | ---------------- | ----- | 
|  <img width="40px" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/react/react-original.svg" /> | <img width="40px" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original-wordmark.svg" /> | JWT |
|  <img width="40px" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/materialui/materialui-original.svg" /> |  <img width="40px" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original-wordmark.svg" />
|  <img width="40px" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/google/google-original.svg" /> <b> Charts </b> |  <img width="40px" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mysql/mysql-original-wordmark.svg" />     
|  <img width="40px" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/redux/redux-original.svg" />
|  <img width="40px" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/javascript/javascript-original.svg" />

## Prerequisites
List any software, tools, or dependencies that users need to have installed before they can use your project. For example:
- Node.js and npm
- Java JDK17

## Installation

### Frontend
1. Navigate into `frontend` folder 
2. Run `npm install` in terminal
3. Run `npm start` in terminal
   
### Backend
1. Navigate into `backend/src/main/resources/application.properties`.
2. Update these values to match your local environment:
  - `spring.datasource.url=jdbc:mysql://localhost:3306/gs`
  - `spring.datasource.username= USERNAME HERE`
  - `spring.datasource.password= PASSWORD HERE`
  - `apiKey = YOUR API KEY HERE`
3. Navigate to `backend/src/main/java/gs/BackendApplication.java` and run Java
