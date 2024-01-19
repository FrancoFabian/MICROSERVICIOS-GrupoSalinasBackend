CREATE TABLE Usuarioorq (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(255),
    curp CHAR(18) NOT NULL UNIQUE,
    codigo_postal CHAR(5)
);
INSERT INTO Usuarioorq (nombre, email, telefono, curp, codigo_postal)
VALUES ('Felipe Franco', 'francoslide@gmail.com', '9512560018', 'FAMF990421HOCBNL04', '71506');
SELECT current_database();
SELECT * FROM Usuarioorq;
