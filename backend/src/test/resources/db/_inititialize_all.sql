USE test;
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `picture` varchar(255) NOT NULL,
  `thumbnail` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
LOCK TABLES `user` WRITE;
UNLOCK TABLES;
--
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject` (
  `id` varchar(255) NOT NULL,
  `picture` varchar(255) NOT NULL,
  `text` varchar(255) NOT NULL,
  `thumbnail` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
LOCK TABLES `subject` WRITE;
UNLOCK TABLES;
--
DROP TABLE IF EXISTS `quiz`;
CREATE TABLE `quiz` (
  `id` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `picture` varchar(255) NOT NULL,
  `thumbnail` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `subject_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2bn6jb1u6wox8j2oh77k4vyp4` (`subject_id`),
  KEY `FK1tofsm1qynhakggx7ttqh8ihu` (`user_id`),
  CONSTRAINT `FK1tofsm1qynhakggx7ttqh8ihu` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK2bn6jb1u6wox8j2oh77k4vyp4` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
LOCK TABLES `quiz` WRITE;
UNLOCK TABLES;
--
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` varchar(255) NOT NULL,
  `answer` varchar(255) NOT NULL,
  `number_of_milliseconds` int NOT NULL,
  `question` varchar(255) NOT NULL,
  `quiz_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb0yh0c1qaxfwlcnwo9dms2txf` (`quiz_id`),
  CONSTRAINT `FKb0yh0c1qaxfwlcnwo9dms2txf` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
LOCK TABLES `question` WRITE;
UNLOCK TABLES;
--
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` varchar(255) NOT NULL,
  `text` varchar(255) NOT NULL,
  `quiz_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKde1ek5t1lq3k1un12f237iwl` (`quiz_id`),
  KEY `FK8kcum44fvpupyw6f5baccx25c` (`user_id`),
  CONSTRAINT `FK8kcum44fvpupyw6f5baccx25c` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKde1ek5t1lq3k1un12f237iwl` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
LOCK TABLES `comment` WRITE;
UNLOCK TABLES;
--
DROP TABLE IF EXISTS `_like`;
CREATE TABLE `_like` (
  `id` varchar(255) NOT NULL,
  `quiz_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsjc1v98ki7hy4gbabpx59ig36` (`quiz_id`),
  KEY `FK37g9rhcdaic3toi8dwcnpfl2i` (`user_id`),
  CONSTRAINT `FK37g9rhcdaic3toi8dwcnpfl2i` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKsjc1v98ki7hy4gbabpx59ig36` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
LOCK TABLES `_like` WRITE;
UNLOCK TABLES;