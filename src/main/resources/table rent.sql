-- создание и заполнение таблицы rent
-- rent_id smallserial - уникальный идентификатор аренды
-- car_id smallserial - идентификатор автомобиля
-- user_id smallserial - идентификатор арендатора
-- from_date date - первый день аренды
-- to_date date - последний день аренды



CREATE TABLE rent (
	rent_id smallserial PRIMARY KEY, 
	car_id smallserial REFERENCES cars ON DELETE RESTRICT,
	user_id smallserial REFERENCES users ON DELETE RESTRICT,
	from_date timestamp,
	to_date timestamp,
	CONSTRAINT valid_date CHECK (to_date >= from_date)
);

INSERT INTO rent VALUES
	(DEFAULT, 1, 1, '2021-01-14', '2021-01-18'),
	(DEFAULT, 6, 3, '2021-02-10', '2021-02-23'),
	(DEFAULT, 4, 5, '2021-05-18', '2021-06-23'),
	(DEFAULT, 7, 1, '2020-10-13', '2020-10-14'),
	(DEFAULT, 9, 2, '2021-01-18', '2021-01-23'),
	(DEFAULT, 2, 7, '2020-06-05', '2020-07-01'),
	(DEFAULT, 5, 9, '2020-11-02', '2020-11-12'),
	(DEFAULT, 4, 10, '2020-07-24', '2020-08-03'),
	(DEFAULT, 3, 6, '2021-03-08', '2021-03-20'),
	(DEFAULT, 8, 5, '2021-04-27', '2021-04-29');