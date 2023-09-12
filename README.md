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

3. MySQL 實作Rdbms資料庫設計

## 其他技術
```
Docker, WebSocket, JavaScript, AJAX, Spring Mail, Tomcat, EcPay integration
```

## **[前後端接口規範參照](https://github.com/f2e-journey/treasure/blob/master/api.md#%E6%8E%A5%E5%8F%A3%E8%BF%94%E5%9B%9E%E7%9A%84%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84)**

## 技術介紹
### SpringSecurity
1. ```以Spring Security AuthenticationManager驗證登入帳密是否正確後回傳一組加密後的JWT放在Browser的LocalStorage裡，之後呼叫API時就只需驗證此JWT便能找到對應的使用者，免除伺服記憶體佔用(Session儲存方式)，同時避免在LocalStorage裡放置重要個資以遭竊取```
![image](etc/JwtToken.png?raw=true "JwtToken")

2. ```以Role權限驗證，區分部分頁面，如銷售額頁面是否能夠讀取```
![image](etc/RoleSetting.png?raw=true "RoleSetting")

#### ROLE_BOSS
![image](etc/RolePermit.png?raw=true "RolePermit")

#### ROLE_EMPLOYEE
![image](etc/RoleDenied.png?raw=true "RoleDenied")

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
### 一般開啟專案方式
1. #### 將dbContent.sql匯入MySQL
資料夾: sqls<BR>
埠號: 3306<BR>
帳密: root

2. #### 開啟專案
使用Spring Boot maven plugin安裝後 (`mvn spring-boot:run`), 專案會跑在 [http://localhost:8080](http://localhost:8080).


### Docker開啟專案方式
1. #### 如果是Windows作業系統用戶，需先將腳本格式調整為CRLF，[原因點這裡](https://blog.clarence.tw/2022/06/26/%E7%99%BC%E7%94%9F-bin-bashm-bad-interpreter-no-such-file-or-directory-%E6%80%8E%E9%BA%BC%E8%A7%A3%E6%B1%BA/)
![image](etc/ReviseBashFormat.png?raw=true "ReviseBashFormat")

2. #### 在專案根目錄打開終端機，輸入`docker compose up`，Docker會自動幫你安裝`mysql`和`jdk`鏡像，同時將sql檔匯入，最後一樣跑在 [http://localhost:8080](http://localhost:8080)

### ROLE_BOSS
廠商: 極鮮家<BR>
帳號: foo1234<BR>
密碼: 1234<BR>

廠商: 千萬屋<BR>
帳號: eric2345<BR>
密碼: 2345<BR>

廠商: 魚吃魚海產<BR>
帳號: dou3456<BR>
密碼: 3456<BR>

### ROLE_EMPLOYEE
廠商: 無名海產<BR>
帳號: woo4567<BR>
密碼: 4567<BR>

廠商: 陳家海鮮<BR>
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

3. MySQL Rdbms DB design

## Others
```
Docker, WebSocket, JavaScript, AJAX, Spring Mail, Tomcat, EcPay integration 
```

## **[Restful Refer To](https://github.com/f2e-journey/treasure/blob/master/api.md#%E6%8E%A5%E5%8F%A3%E8%BF%94%E5%9B%9E%E7%9A%84%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84)**

## Function
### SpringSecurity
1. ```Return encoded JWT after Spring Security AuthenticationManager's verification and store it inside browser's local storage, after that, it is able to call API whenever JWT is still valid and return user's information.```
![image](etc/JwtToken.png?raw=true "JwtToken")

2. ```Use role's authority to verify who is able to access the sensitive page.(ex. revenue page)```
![image](etc/RoleSetting.png?raw=true "RoleSetting")

#### ROLE_BOSS
![image](etc/RolePermit.png?raw=true "RolePermit")

#### ROLE_EMPLOYEE
![image](etc/RoleDenied.png?raw=true "RoleDenied")

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
### Standard application running
1. #### Import dbContent.sql into MySQL
Folder: sqls<BR>
Port: 3306<BR>
Account & Password: root

2. #### Run the app
Start the application with the Spring Boot maven plugin (`mvn spring-boot:run`), the application will be running at [http://localhost:8080](http://localhost:8080).

### Run application with Docker
1. #### If you are Windows OS, please alter to CRLF format before docker composing, [here is why](https://willi.am/blog/2016/08/11/docker-for-windows-dealing-with-windows-line-endings/)
![image](etc/ReviseBashFormat.png?raw=true "ReviseBashFormat")

2. #### Command `docker compose up` at application root path, Docker will pull `mysql` and `jdk` images then import `sql file` automatically. It will be running at [http://localhost:8080](http://localhost:8080) as well.

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
