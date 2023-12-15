# codeQuarkusMysql
Api rest con JsonNode como request y response con conexion a mysql y quarkus 3 java 17 maven .39
Configurar mysql desde docker desktop:

Primero tener instalado Docker desktop, luego: en search poner mysql , el das en pull, 
 

Desde cmd:
*crear un contenedor funcional para conexiones externas:
docker run --name mysql-ext -e MYSQL_ROOT_PASSWORD=admin -p 3306:3306 -d mysql:latest
Aquí te explican mejor el comando:
 
*luego seguir con estos comandos para crear algún otro usuario o un esquema de BD:
//para entrar a mysql
docker exec -it container-mysql mysql -uroot -p
//Crear user
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin';
//Darle privilegios
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'localhost' WITH GRANT OPTION;
//Crear una base de datos
CREATE DATABASE prueba;
//Darle privilegios al usuario creado para la base de datos
GRANT ALL PRIVILEGES ON prueba.* TO 'admin'@'localhost';
//Hacer que surtan efecto los cambios
FLUSH PRIVILEGES;


NOTA: Esto para crear la base de datos:
use prueba;
CREATE TABLE persona (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255),
    edad INT,
    curp VARCHAR(18)
);

INSERT INTO persona (nombre, edad, curp) VALUES
('Juan', 25, 'ABC123456XYZ789012'),
('María', 30, 'DEF789012UVW345678');

select * from persona;
