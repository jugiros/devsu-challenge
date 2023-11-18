-- Tabla Persona
CREATE TABLE IF NOT EXISTS persona (
                                       id BIGINT NOT NULL AUTO_INCREMENT,
                                       nombre VARCHAR(255),
    genero VARCHAR(50),
    edad INT,
    identificacion VARCHAR(100),
    direccion VARCHAR(255),
    telefono VARCHAR(50),
    PRIMARY KEY (id)
    );

-- Tabla Cliente (que hereda de Persona)
CREATE TABLE IF NOT EXISTS cliente (
                                       id BIGINT NOT NULL,
                                       clienteId BIGINT NOT NULL AUTO_INCREMENT,
                                       contraseña VARCHAR(255),
    estado VARCHAR(50),
    PRIMARY KEY (clienteId),
    FOREIGN KEY (id) REFERENCES persona(id)
    );

-- Tabla Cuenta
CREATE TABLE IF NOT EXISTS cuenta (
                                      id BIGINT NOT NULL AUTO_INCREMENT,
                                      numero_cuenta VARCHAR(100),
    tipo_cuenta VARCHAR(50),
    saldo_inicial DECIMAL(10, 2),
    estado VARCHAR(50),
    PRIMARY KEY (id)
    );

-- Tabla Movimientos
CREATE TABLE IF NOT EXISTS movimientos (
                                           id BIGINT NOT NULL AUTO_INCREMENT,
                                           fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                           tipo_movimiento VARCHAR(50),
    valor DECIMAL(10, 2),
    saldo DECIMAL(10, 2),
    PRIMARY KEY (id)
    );

ALTER TABLE `cliente`
    CHANGE `estado` `estado` BOOLEAN NULL;