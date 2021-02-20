-- создание и заполнение таблицы users
-- user_id - уникальный идентификатор арендатора. Диапазон значений: 1-32767
-- first_name text - имя арендатора
-- last_name text - фамилия арендатора
-- middle_name text - отчество арендатора
-- date_of_birth date - дата рождения (Year - Month - Date)
-- passport_number bigint - серия + номер паспорта (если хранить отдельно серию и номер, то хватило бы smallint + integer = 6 байт)
-- date_of_issue date - дата выдачи паспорта (Year - Month - Date)


CREATE TABLE users (
	user_id smallserial PRIMARY KEY,
	first_name text,
	last_name text,
	middle_name text,
	date_of_birth date,
	passport_number bigint,
	date_of_issue date
);

INSERT INTO users VALUES
	(DEFAULT, 'Natalia', 'Averchenko', 'Aleksandrovna','1994-08-20', 4017951601,'2013-03-24'),
	(DEFAULT, 'Igor', 'Ivanov', 'Yanovich', '1994-06-09', 4008736295, '2012-06-26'),
	(DEFAULT, 'Ekaterina', 'Semushina','Yurievna', '1994-04-30', 4016326936, '2016-05-03'),
	(DEFAULT, 'Maksim', 'Anshukov', 'Olegovich', '1993-09-06', 4011892783, '2012-10-15'),
	(DEFAULT, 'Konstantin', 'Porshnev', 'Vladimirovich', '1981-03-25', 4017326735, '2000-01-23'),
	(DEFAULT, 'Ilia','Panteleev','Sergeevich','1983-05-17', 4009894782, '2015-06-17'),
	(DEFAULT, 'Ekaterina','Levandovskaya','Olegovna', '1986-09-30', 4007278564, '2006-02-14'),
	(DEFAULT, 'Victor','Minin','Konstantinovich', '1993-02-06', 4012923758, '2020-03-06' ),
	(DEFAULT, 'Julia','Eskova', 'Igorevna', '1994-12-03', 4014983501, '2014-10-15'),
	(DEFAULT, 'Julia','Revyakina','Valentinovna','1996-10-13', 4013895672, '2019-12-02');