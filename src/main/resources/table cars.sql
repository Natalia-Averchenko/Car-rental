-- создание и заполнение таблицы cars
-- car_id - уникальный идентификатор автомобиля. Диапазон значений: 1-32767
-- make_of_car - марка автомобиля
-- model - модель автомобиля
-- price_per_day - стоимость проката автомобиля в сутки
-- transmission - коробка передач. Допустимые значения - 'automatic', ‘manual’
-- navigator boolean - наличие навигатора


--CREATE TYPE transmission_type AS ENUM ('automatic', 'manual');


-- USE car_rental;
CREATE TABLE cars (
	car_id smallserial PRIMARY KEY,					
	make_of_car text,
	model text,
	price_per_day smallint,
	transmission text,
	navigator boolean,
	CHECK (price_per_day > 0)
	
);

INSERT INTO cars VALUES
	(DEFAULT, 'Ford', 'Focus', 22, 'manual', false),
	(DEFAULT, 'Hyundai', 'Solaris', 24, 'manual', false),
	(DEFAULT, 'Kia', 'Sportage', 35, 'automatic', true),
	(DEFAULT, 'Chevrolet', 'Lacetti', 28, 'manual', false),
	(DEFAULT, 'Hyundai', 'Creta', 39, 'automatic', true),
	(DEFAULT, 'Audi', 'A4', 67, 'automatic', true),
	(DEFAULT, 'Volkswagen', 'Caravelle', 110, 'automatic', true),
	(DEFAULT, 'Citroen', 'Spacetourer', 120, 'automatic', true),
	(DEFAULT, 'Chevrolet', 'Camaro', 124, 'automatic', false),
	(DEFAULT, 'Porsche', 'Boxter', 173, 'automatic', true),
	(DEFAULT, 'Ford', 'Mustang', 150, 'automatic', true);
