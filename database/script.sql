CREATE TABLE usuarios (
	id SERIAL PRIMARY KEY,
	usuario VARCHAR(50) NOT NULL UNIQUE,
	contrasena VARCHAR(50) NOT NULL,
	rol VARCHAR(20) NOT NULL CHECK(rol in ('ADMINISTRADOR', 'ENTRENADOR', 'CLIENTE'))
);

INSERT INTO usuarios(usuario, contrasena, rol) VALUES ('admin', '123456', 'ADMINISTRADOR');

SELECT * FROM usuarios;

CREATE TABLE clientes (
	id SERIAL PRIMARY KEY,
	usuario_id INT NOT NULL UNIQUE,
	cedula VARCHAR(10) NOT NULL UNIQUE,
	nombre VARCHAR(50) NOT NULL,
	apellido VARCHAR(50) NOT NULL,
	telefono VARCHAR(10) NOT NULL UNIQUE,
	correo VARCHAR(50) NOT NULL UNIQUE,
	estado VARCHAR(10) NOT NULL CHECK (estado in ('ACTIVO', 'INACTIVO')),

	FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE entrenadores(
	id SERIAL PRIMARY KEY,
	usuario_id INT NOT NULL UNIQUE,
	cedula VARCHAR(10) NOT NULL UNIQUE,
	nombre VARCHAR(50) NOT NULL,
	apellido VARCHAR(50) NOT NULL,
	especialidad VARCHAR(50) NOT NULL CHECK (especialidad IN ('Musculacion', 'Calistenia', 'Powerlifting')),
	telefono VARCHAR(10) NOT NULL UNIQUE,

	FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE planes(
	id SERIAL PRIMARY KEY,
	nombre VARCHAR(50) NOT NULL UNIQUE,
	precio DECIMAL(10,2) NOT NULL CHECK (precio > 0),
	duracion_meses INT NOT NULL CHECK (duracion_meses > 0)
);

CREATE TABLE membresias(
	id SERIAL PRIMARY KEY,
	cliente_id INT NOT NULL,
	plan_id INT NOT NULL,
	fecha_inicio DATE NOT NULL,
	fecha_fin DATE NOT NULL,
	estado VARCHAR(10) NOT NULL CHECK (estado IN ('ACTIVO', 'INACTIVO')),

	FOREIGN KEY (cliente_id) REFERENCES clientes(id),
	FOREIGN KEY (plan_id) REFERENCES planes(id),
	CHECK (fecha_fin >= fecha_inicio)
);

CREATE TABLE rutinas(
	id SERIAL PRIMARY KEY,
	nombre VARCHAR(50) NOT NULL,
	descripcion TEXT NOT NULL,
	nivel VARCHAR(20) NOT NULL CHECK(nivel IN ('PRINCIPIANTE', 'INTERMEDIO', 'AVANZADO'))
);

CREATE TABLE asignacion_rutinas(
	id SERIAL PRIMARY KEY,
	cliente_id INT NOT NULL,
	rutina_id INT NOT NULL,
	entrenador_id INT NOT NULL,
	fecha_asignacion DATE NOT NULL,
	FOREIGN KEY (cliente_id) REFERENCES clientes(id),
	FOREIGN KEY (rutina_id) REFERENCES rutinas(id),
	FOREIGN KEY (entrenador_id) REFERENCES entrenadores(id)
	
);

INSERT INTO usuarios(usuario, contrasena, rol)
VALUES
('entrenador', '123456', 'ENTRENADOR'),
('cliente', '123456', 'CLIENTE');