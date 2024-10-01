create database evaluaciones;
use evaluaciones;

create table cursos (
	id int unsigned auto_increment primary key,
	curso varchar(150) not null
);

create table estudiantes (
	id int unsigned auto_increment primary key,
	nombre varchar(200) not null,
	edad int not null,
	telefono varchar(16) not null
);

create table evaluaciones (
	id int unsigned auto_increment primary key,
	fecha varchar(50) not null,
	calificacion FLOAT not null,
	idEstudiante int unsigned,
	idCurso int unsigned,
	foreign key (idEstudiante) references estudiantes(id),
	foreign key (idCurso) references cursos(id)
);

  
insert into cursos (curso) values
	("Desarrollo de Aplicaciones Móbiles"),
	("Desarrollo de Aplicaciones Web"),
	("Desarrollo del Pensamiento Lógico y Matemático"),
	("Gestión de Herramientas Web"),
	("Utilización de Frameworks para el Desarrollo de Software");
	
INSERT INTO estudiantes (nombre, edad, telefono) VALUES
("Carlos Pérez", 25, "123-456-7890"),
("María González", 22, "234-567-8901"),
("Ana López", 28, "345-678-9012"),
("Juan Rodríguez", 20, "456-789-0123"),
("Lucía Fernández", 26, "567-890-1234");


INSERT INTO evaluaciones (fecha, calificacion, idEstudiante, idCurso) VALUES
("2024-09-15", 8.5, 1, 1),  
("2024-09-16", 9.0, 2, 2),  
("2024-09-17", 7.8, 3, 3),  
("2024-09-18", 8.0, 4, 4),  
("2024-09-19", 9.5, 5, 5);

select * from estudiantes;
select * from evaluaciones;


