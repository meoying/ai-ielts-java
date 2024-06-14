-- create the databases
CREATE DATABASE IF NOT EXISTS `ielts`;

-- create the users for each database
CREATE USER 'ielts'@'%' IDENTIFIED BY 'ielts';
GRANT CREATE, ALTER, INDEX, LOCK TABLES, REFERENCES, UPDATE, DELETE, DROP, SELECT, INSERT ON `ielts`.* TO 'ielts'@'%';

FLUSH PRIVILEGES;