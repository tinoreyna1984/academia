INSERT INTO usuarios (username, password, nombre_usuario, apellidos_usuario, email, rol) values ('admin', '$2a$10$QSXVQbkqsa/k87R.YTSeAeHePHY.LESHNT8WL5RU3GLII6Wcae.76', 'Administrador', 'CRM', 'admin@mail.com', 'ADMINISTRATOR');
INSERT INTO usuarios (username, password, nombre_usuario, apellidos_usuario, email, rol) values ('treyna', '$2a$10$ajrJiSEZbDBLnHsMJJz5i.iqdxmLRWeAC3vmQ9D5fxwreDb4hK6p6', 'Tino', 'Reyna', 'treyna@mail.com', 'USER');
INSERT INTO usuarios (username, password, nombre_usuario, apellidos_usuario, email, rol) values ('glorentzen', '$2a$10$ajrJiSEZbDBLnHsMJJz5i.iqdxmLRWeAC3vmQ9D5fxwreDb4hK6p6', 'Gabriela', 'Lorentzen', 'glorentzen@mail.com', 'USER');

INSERT INTO profesores (nombre_profesor, apellidos_profesor, correo) VALUES ('Juan', 'Perez', 'jperez@academia.com');
INSERT INTO profesores (nombre_profesor, apellidos_profesor, correo) VALUES ('Maria', 'Suarez', 'msuarez@academia.com');
INSERT INTO profesores (nombre_profesor, apellidos_profesor, correo) VALUES ('Jose', 'Gonzalez', 'jgonzalez@academia.com');

INSERT INTO materias (nombre_materia, desc_materia) VALUES ('Matematicas', 'Ciencias matematicas');
INSERT INTO materias (nombre_materia, desc_materia) VALUES ('Lenguaje', 'Idioma espannol');
INSERT INTO materias (nombre_materia, desc_materia) VALUES ('Ciencias', 'Ciencias naturales');


INSERT INTO aulas (cod_aula, fecha_hora_registro, id_profesor, id_materia, tema) VALUES ('AULA0001', '2023-04-25 08:00:00', 1, 1, 'Las cuatro operaciones');
INSERT INTO aulas (cod_aula, fecha_hora_registro, id_profesor, id_materia, tema) VALUES ('AULA0002', '2023-04-25 09:00:00', 2, 1, 'Matematicas avanzadas');
INSERT INTO aulas (cod_aula, fecha_hora_registro, id_profesor, id_materia, tema) VALUES ('AULA0003', '2023-04-25 08:00:00', 3, 2, 'Ortografia basica');
INSERT INTO aulas (cod_aula, fecha_hora_registro, id_profesor, id_materia, tema) VALUES ('AULA0004', '2023-04-25 10:00:00', 2, 2, 'Gramatica basica');
INSERT INTO aulas (cod_aula, fecha_hora_registro, id_profesor, id_materia, tema) VALUES ('AULA0005', '2023-04-25 11:00:00', 1, 3, 'Fisica elemental');
