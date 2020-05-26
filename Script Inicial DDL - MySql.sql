CREATE DATABASE `datad` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE datad;


CREATE TABLE `documentos` (
  `id_documento` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  PRIMARY KEY (`id_documento`),
  UNIQUE KEY `id_documento_UNIQUE` (`id_documento`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `terminos` (
  `id_termino` int NOT NULL AUTO_INCREMENT,
  `palabra` varchar(150) NOT NULL,
  PRIMARY KEY (`id_termino`),
  UNIQUE KEY `id_termino_UNIQUE` (`id_termino`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='			';

CREATE TABLE `posteos` (
  `id_documento` int NOT NULL,
  `id_termino` int NOT NULL,
  `frecuencia` int NOT NULL,
  PRIMARY KEY (`id_documento`,`id_termino`),
  KEY `id_termino_idx` (`id_termino`),
  CONSTRAINT `id_documento` FOREIGN KEY (`id_documento`) REFERENCES `documentos` (`id_documento`),
  CONSTRAINT `id_termino` FOREIGN KEY (`id_termino`) REFERENCES `terminos` (`id_termino`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DELIMITER $$
CREATE PROCEDURE `getIdDocumento`(IN nombreDocumentoIN VARCHAR(50), OUT idDocumentoOUT int)
BEGIN

INSERT INTO documentos (nombre) VALUES (nombreDocumentoIN);
SET idDocumentoOUT = LAST_INSERT_ID();

END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `getIDtermino`(IN palabraIN VARCHAR(150), OUT idTerminoOUT int)
BEGIN

SET idTerminoOUT = (SELECT id_termino FROM terminos where palabra = palabraIN);

IF (idTerminoOUT IS  NUll)
	THEN 
		INSERT INTO terminos(palabra) VALUES (palabraIN);
		SET idTerminoOUT = LAST_INSERT_ID();
END IF;

END$$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE `insertPosting`(IN idDocumentoIN int, IN palabraIN VARCHAR(150), IN frecuenciaIN int)
BEGIN

CALL getIDtermino(palabraIN,@id_termino);


INSERT INTO posteos(id_documento,id_termino,frecuencia)VALUES(idDocumentoIN,@id_termino,frecuenciaIN);

END$$
DELIMITER ;


