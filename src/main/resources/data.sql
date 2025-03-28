-- Inserir marcas de carros
INSERT INTO marca (id, nome) VALUES (1, 'Toyota');
INSERT INTO marca (id, nome) VALUES (2, 'Honda');
INSERT INTO marca (id, nome) VALUES (3, 'Ford');
INSERT INTO marca (id, nome) VALUES (4, 'Chevrolet');
INSERT INTO marca (id, nome) VALUES (5, 'Volkswagen');

-- Inserir modelos de carros
INSERT INTO modelo (id, nome, marca_id) VALUES (1, 'Corolla', 1);
INSERT INTO modelo (id, nome, marca_id) VALUES (2, 'Hilux', 1);
INSERT INTO modelo (id, nome, marca_id) VALUES (3, 'Civic', 2);
INSERT INTO modelo (id, nome, marca_id) VALUES (4, 'Accord', 2);
INSERT INTO modelo (id, nome, marca_id) VALUES (5, 'Focus', 3);
INSERT INTO modelo (id, nome, marca_id) VALUES (6, 'Ranger', 3);
INSERT INTO modelo (id, nome, marca_id) VALUES (7, 'Onix', 4);
INSERT INTO modelo (id, nome, marca_id) VALUES (8, 'S10', 4);
INSERT INTO modelo (id, nome, marca_id) VALUES (9, 'Gol', 5);
INSERT INTO modelo (id, nome, marca_id) VALUES (10, 'Polo', 5);

-- Inserir marcas de motos
INSERT INTO marca (id, nome) VALUES (6, 'Yamaha');
INSERT INTO marca (id, nome) VALUES (7, 'Suzuki');
INSERT INTO marca (id, nome) VALUES (8, 'Kawasaki');
INSERT INTO marca (id, nome) VALUES (9, 'Harley-Davidson');
INSERT INTO marca (id, nome) VALUES (10, 'Ducati');

-- Inserir modelos de motos
INSERT INTO modelo (id, nome, marca_id) VALUES (11, 'YZF-R3', 6);
INSERT INTO modelo (id, nome, marca_id) VALUES (12, 'MT-07', 6);
INSERT INTO modelo (id, nome, marca_id) VALUES (13, 'GSX-R750', 7);
INSERT INTO modelo (id, nome, marca_id) VALUES (14, 'V-Strom 650', 7);
INSERT INTO modelo (id, nome, marca_id) VALUES (15, 'Ninja 400', 8);
INSERT INTO modelo (id, nome, marca_id) VALUES (16, 'Z900', 8);
INSERT INTO modelo (id, nome, marca_id) VALUES (17, 'Iron 883', 9);
INSERT INTO modelo (id, nome, marca_id) VALUES (18, 'Fat Boy', 9);
INSERT INTO modelo (id, nome, marca_id) VALUES (19, 'Panigale V4', 10);
INSERT INTO modelo (id, nome, marca_id) VALUES (20, 'Monster 821', 10);