DROP TABLE APPLICATION_USER;
DROP TABLE GROUP_USER_NAMES;
DROP TABLE GROUP_USERS;
DROP TABLE RESOURCES;
DROP TABLE GROUP_RESOURCE_NAMES;
DROP TABLE GROUP_RESOURCES;
DROP TABLE GROUP_USERS_RESOURCES_PERMISSIONS;

CREATE TABLE APPLICATION_USER (
         USER_ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
         LOGIN VARCHAR(100) NOT NULL UNIQUE,
         EMAIL VARCHAR(100) NOT NULL UNIQUE,
         USER_PASSWORD VARCHAR(100) NOT NULL,
         PIN_CODE VARCHAR(100) NOT NULL,
         ATTEMPT_COUNT INT(11) NOT NULL,
         LAST_ATTEMPT_DATE TIMESTAMP,
         PIN_ATTEMPT_COUNT INT(11) NOT NULL,
         PIN_LAST_ATTEMPT_DATE TIMESTAMP,
         USER_ROLE INT 
       );
CREATE TABLE GROUP_USER_NAMES (
         GROUP_USER_NAME_ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
         GROUP_NAME VARCHAR(100) NOT NULL
       );
CREATE TABLE GROUP_USERS (
         GROUP_USERS_ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
		 GROUP_USER_NAME_ID INT NOT NULL,
         USER_ID INT NOT NULL,
         FOREIGN KEY (USER_ID) REFERENCES APPLICATION_USER(USER_ID),
		 FOREIGN KEY (GROUP_USER_NAME_ID) REFERENCES GROUP_USER_NAMES(GROUP_USER_NAME_ID)
       );
CREATE TABLE RESOURCES (
         RESOURCES_ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
         PROJECT_NAME VARCHAR(100) NOT NULL,
         PROJECT_INFO VARCHAR(100) NOT NULL
       );
CREATE TABLE GROUP_RESOURCE_NAMES (
         GROUP_RESOURCES_NAME_ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
         GROUP_NAME VARCHAR(100) NOT NULL
       );
CREATE TABLE GROUP_RESOURCES (
         GROUP_RESOURCES_ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
         GROUP_RESOURCES_NAME_ID INT NOT NULL,
         RESOURCES_ID INT NOT NULL,
         FOREIGN KEY (RESOURCES_ID) REFERENCES RESOURCES(RESOURCES_ID),
		 FOREIGN KEY (GROUP_RESOURCES_NAME_ID) REFERENCES GROUP_RESOURCE_NAMES(GROUP_RESOURCES_NAME_ID)
       );
CREATE TABLE GROUP_USERS_RESOURCES_PERMISSIONS (
         GROUP_USERS_RESOURCES_ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
         GROUP_USER_NAME_ID INT NOT NULL,
         GROUP_RESOURCES_NAME_ID INT NOT NULL,
         FULL_PERMISSION BIT,
         FOREIGN KEY (GROUP_USER_NAME_ID) REFERENCES GROUP_USER_NAMES(GROUP_USER_NAME_ID),
		 FOREIGN KEY (GROUP_RESOURCES_NAME_ID) REFERENCES GROUP_RESOURCE_NAMES(GROUP_RESOURCES_NAME_ID)
       );