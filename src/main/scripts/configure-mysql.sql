## Use to run mysql db docker image, optional if you're not using a local mysql db
# docker run --name recipe-mysql -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql

# connect to mysql and run as root user
#Create Databases
CREATE DATABASE recipe_dev;
CREATE DATABASE recipe_prod;

#Create database service accounts
CREATE USER 'dev_user'@'localhost' IDENTIFIED BY 'password';
CREATE USER 'prod_user'@'localhost' IDENTIFIED BY 'password';
CREATE USER 'dev_user'@'%' IDENTIFIED BY 'password';
CREATE USER 'prod_user'@'%' IDENTIFIED BY 'password';

#Database grants
GRANT SELECT ON recipe_dev.* to 'dev_user'@'localhost';
GRANT INSERT ON recipe_dev.* to 'dev_user'@'localhost';
GRANT DELETE ON recipe_dev.* to 'dev_user'@'localhost';
GRANT UPDATE ON recipe_dev.* to 'dev_user'@'localhost';
GRANT SELECT ON recipe_prod.* to 'prod_user'@'localhost';
GRANT INSERT ON recipe_prod.* to 'prod_user'@'localhost';
GRANT DELETE ON recipe_prod.* to 'prod_user'@'localhost';
GRANT UPDATE ON recipe_prod.* to 'prod_user'@'localhost';
GRANT SELECT ON recipe_dev.* to 'dev_user'@'%';
GRANT INSERT ON recipe_dev.* to 'dev_user'@'%';
GRANT DELETE ON recipe_dev.* to 'dev_user'@'%';
GRANT UPDATE ON recipe_dev.* to 'dev_user'@'%';
GRANT SELECT ON recipe_prod.* to 'prod_user'@'%';
GRANT INSERT ON recipe_prod.* to 'prod_user'@'%';
GRANT DELETE ON recipe_prod.* to 'prod_user'@'%';
GRANT UPDATE ON recipe_prod.* to 'prod_user'@'%';
