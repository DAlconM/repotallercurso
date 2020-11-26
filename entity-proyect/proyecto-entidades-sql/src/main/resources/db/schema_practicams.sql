CREATE DATABASE IF NOT EXISTS practicams;

ALTER DATABASE practicams
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

USE practicams;

CREATE TABLE IF NOT EXISTS clientes (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(50),
  apellido VARCHAR(50),
  estado VARCHAR(20),
  INDEX(apellido)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS direcciones (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  provincia VARCHAR(50),
  poblacion VARCHAR(50),
  domicilio VARCHAR(50),
  estado VARCHAR(20),
  cliente INT(4) UNSIGNED NOT NULL,
  INDEX(id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS visita (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  estado VARCHAR(20),
  cliente INT(4) UNSIGNED NOT NULL,
  importe DOUBLE,
  factura VARCHAR(50),
  INDEX(id)
) engine=InnoDB;

