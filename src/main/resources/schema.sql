CREATE TABLE equipos (
    id INT PRIMARY KEY,
    nombre VARCHAR(55),
    liga VARCHAR(55),
    pais VARCHAR(55)
);

CREATE SEQUENCE equipos_seq START WITH 1 INCREMENT BY 1;
