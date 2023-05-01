# 水產商自動叫貨系統

Click here for **[Live demo](https://immargin.asuscomm.com/oms/)** or **[English Desc](#seafood-wholesaler-oms)**

![image](etc/SystemPage.png?raw=true "JwtToken")

## 關於專案
```
專案主要目的是展示自己在Java裡所學到的技術
```

## 主要技術與框架
1. **[Spring Security](https://spring.io/projects/spring-security)** + **[JWT (JSON Web Token)](https://jwt.io)** + 
Restful API + JSON 實作前後端分離開發<BR>

2. **[Spring Boot](https://spring.io/projects/spring-boot)** + Spring MVC + Spring Data Jpa + Hibernate ORM 實作CRUD與前端網頁呈現<BR>

3. MySQL 實作Rdbms資料庫設計 (採2NF開發，因為資料調度需求不用3NF)

## 其他技術
```
JavaScript, Node.js, AJAX, Spring Mail, Tomcat, EcPay integration, Docker-compose, WebSocket
```

## 技術介紹
### SpringSecurity
1. ```以Spring Security AuthenticationManager驗證登入帳密是否正確後回傳一組加密後的JWT放在Browser的LocalStorage裡，之後呼叫API時就只需驗證此JWT便能找到對應的使用者，免除伺服記憶體佔用(Session儲存方式)，同時避免在LocalStorage裡放置重要個資以遭竊取```
![image](etc/JwtToken.png?raw=true "JwtToken")

2. ```以Role權限驗證，區分部分頁面(如: 銷售額頁面)是否能夠讀取```
![image](etc/RoleSetting.png?raw=true "RoleSetting")

#### ROLE_BOSS
![image](etc/RolePermit.png?raw=true "RolePermit")

#### ROLE_EMPLOYEE
![image](etc/RoleDenied.png?raw=true "RoldeDenied")

### WebSocket
```
實作WebSocket傳輸協定，透過一次性連結保持連線，讓前端訂單通知功能不用一直發送request與server要值，也能即時更新資訊
```
![image](etc/SystemNotice.png?raw=true "SystemNotice")

## 專案需求
```
此專案是由Maven和Java 17所建構
```

## 操作指南

### 將dbContent.sql匯入MySQL
資料夾: sqls<BR>
埠號: 3306<BR>
帳密: root

### 開啟專案
使用Spring Boot maven plugin安裝後 (`mvn spring-boot:run`).
專案會跑在 [http://localhost:8080](http://localhost:8080).

### Docker開啟方式
如有安裝Docker，可直接使用終端機cd到本專案後，輸入`docker-compose up`指令，專案便會跑在 [http://localhost:8080](http://localhost:8080).<BR>
Docker會幫你安裝`mysql`和`jdk`，同時匯入sql檔

### ROLE_BOSS
帳號: foo1234<BR>
密碼: 1234<BR>

帳號: eric2345<BR>
密碼: 2345<BR>

帳號: dou3456<BR>
密碼: 3456<BR>

### ROLE_EMPLOYEE
帳號: woo4567<BR>
密碼: 4567<BR>

帳號: chen5678<BR>
密碼: 5678<BR>

***
<p align="center" style="font-weight:bold; font-size:20px;">English Desc</p>

***

# Seafood Wholesaler OMS

![image](etc/SystemPage.png?raw=true "JwtToken")

## About
```
This project is used to demonstrate what I've learned in Java.
```

## Main Structure
1. **[Spring Security](https://spring.io/projects/spring-security)** + **[JWT (JSON Web Token)](https://jwt.io)** +
   Restful API + JSON<BR>

2. **[Spring Boot](https://spring.io/projects/spring-boot)** + Spring MVC + Spring Data Jpa + Hibernate ORM<BR>

3. MySQL Rdbms DB design (Use 2NF design rather than 3NF as it fit more in project)

## Others
```
JavaScript, Node.js, AJAX, Spring Mail, Tomcat, EcPay integration, Docker-compose, WebSocket
```

## Function
### SpringSecurity
1. ```Return encoded JWT after Spring Security AuthenticationManager's verification and store it inside browser's local storage, after that, it is able to call API whenever JWT is still valid and return user's information.```
![image](etc/JwtToken.png?raw=true "JwtToken")

2. ```Use role's authority to verify who is able to access the sensitive page.(ex. revenue page)```
![image](etc/RoleSetting.png?raw=true "RoleSetting")

#### ROLE_BOSS
![image](etc/RolePermit.png?raw=true "RolePermit")

#### ROLE_EMPLOYEE
![image](etc/RoleDenied.png?raw=true "RoldeDenied")

### WebSocket
```
By implementing WebSocket protocol, enable front-end to get instant message from server without continuously sending request.
```
![image](etc/SystemNotice.png?raw=true "SystemNotice")

## Requirement
```
This project is build with Maven and Java 17.
```

## Guide
### Import dbContent.sql into MySQL
Folder: sqls<BR>
Port: 3306<BR>
Account & Password: root

### Run the app
Start the application with the Spring Boot maven plugin (`mvn spring-boot:run`).
The application is running at [http://localhost:8080](http://localhost:8080).

### Docker 
If you had installed Docker, `cd` to this project by using terminal, then enter `docker-compose up`, application will be directly running at  [http://localhost:8080](http://localhost:8080).<BR>Docker will help you install `mysql` and `jdk`, then import `sql file` automatically.

### ROLE_BOSS
Account: foo1234<BR>
Password: 1234<BR>

Account: eric2345<BR>
Password: 2345<BR>

Account: dou3456<BR>
Password: 3456<BR>

### ROLE_EMPLOYEE
Account: woo4567<BR>
Password: 4567<BR>

Account: chen5678<BR>
Password: 5678<BR>
