CREATE TABLE IF NOT EXISTS `flights` (
       `id` int NOT NULL AUTO_INCREMENT,
       `flight` varchar(50) NOT NULL,
       `departure` datetime NOT NULL,
       PRIMARY KEY (`id`)
);

INSERT INTO flights(flight, departure)
VALUES ('Air Canada 8099', '2021-01-01 07:30:00'),
       ('United Airline 6115', '2021-01-01 10:30:00'),
       ('WestJet 6456', '2021-01-01 12:30:00'),
       ('Delta 3833', '2021-01-01 15:00:00');