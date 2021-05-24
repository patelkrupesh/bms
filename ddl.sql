CREATE DATABASE IF NOT EXISTS `bms`;

USE `bms`;

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
                         `username` varchar(255) NOT NULL ,
                         `password` varchar(255) NOT NULL,
                         `name` varchar(255) NOT NULL,
                         `address` varchar(255),
                         `type` varchar(255) NOT NULL,
                         PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `movies`;

CREATE TABLE `movies` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `language` varchar(255) NOT NULL,
                          `name` varchar(255) NOT NULL,
                          `length` int(5) NOT NULL,
                          `release_date` date NOT NULL,
                          `starcast` varchar(255),
                          `genre` varchar(255) NOT NULL ,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `theaters`;

CREATE TABLE `theaters` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `username` varchar(255) NOT NULL UNIQUE ,
                            `password` varchar(255) NOT NULL,
                            `name` varchar(255) NOT NULL,
                            `address` varchar(255) ,
                            `city` varchar(255) NOT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `screens`;

CREATE TABLE `screens` (
                                 `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                 `screenname` varchar(255) NOT NULL,
                                 `noofseats` int(4) NOT NULL,
                                 `theater_id` bigint(20) DEFAULT NULL,
                                 PRIMARY KEY (`id`),
                                 FOREIGN KEY (`theater_id`) REFERENCES `theaters` (`id`)
                                     ON UPDATE CASCADE ON DELETE CASCADE,
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `shows`;

CREATE TABLE `shows` (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `date` date NOT NULL,
                         `time` int(5) NOT NULL,
                         `movie_id` bigint(20) DEFAULT NULL,
                         `screen_id` bigint(20) DEFAULT NULL ,
                         PRIMARY KEY (`id`),
                         FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`)
                             ON UPDATE CASCADE ON DELETE CASCADE,
                         FOREIGN KEY (`screen_id`) REFERENCES `screens` (`id`)
                             ON UPDATE CASCADE ON DELETE CASCADE,
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `seats`;

CREATE TABLE `seats` (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `seatnumber` int(3) NOT NULL,
                         `rate` int(5) NOT NULL,
                         `seattype` varchar(255) NOT NULL,
                         `is_booked` bit(1) NOT NULL DEFAULT b'0',
                         `screen_id` bigint(20) DEFAULT NULL ,
                         `show_id` bigint(20) DEFAULT NULL,
                         `movie_id` bigint(20) DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         FOREIGN KEY (`screen_id`) REFERENCES `screen` (`id`)
                             ON UPDATE CASCADE ON DELETE CASCADE,
                         FOREIGN KEY (`show_id`) REFERENCES `show` (`id`)
                             ON UPDATE CASCADE ON DELETE CASCADE,
                         FOREIGN KEY (`booking_id`) REFERENCES `booking` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `bookings`;

CREATE TABLE `bookings` (
                           `id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `booking_time` datetime NOT NULL,
                           `alloted_seats` varchar(255) NOT NULL,
                           `amount` double NOT NULL,
                           `ticketDownloadlink` varchar(255) NOT NULL,
                           `show_id` bigint(20) DEFAULT NULL,
                           `username` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           FOREIGN KEY (`show_id`) REFERENCES `shows` (`id`)
                               ON UPDATE CASCADE ON DELETE CASCADE,
                           FOREIGN KEY (`username`) REFERENCES `users` (`username`),
                               ON UPDATE CASCADE ON DELETE CASCADE,
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `payments`;

CREATE TABLE `payments` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT,
                              `amount` double NOT NULL,
                              `status` varchar(255) NOT NULL,
                              `gateway` varchar(255) NOT NULL,
                              `booking_id` bigint(20) DEFAULT NULL,
                              PRIMARY KEY (`id`),
                              FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`),
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
