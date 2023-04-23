use cinema;

/*



drop table if exists `movie-category`;
drop table if exists `categories`;
drop table if exists `movies`;
*/

create table if not exists `categories` (
    `id` int(11) NOT NULL PRIMARY KEY,
    `type` varchar (255) NOT NULL DEFAULT ''
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


create table if not exists `movies`(
    `id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `cover` varchar (255) NOT NULL DEFAULT '',
    `title` varchar (50) NOT NULL DEFAULT '',
    `date` varchar (10) NOT NULL DEFAULT '',
    `rate` float DEFAULT 0,
    `director` varchar (100) NOT NULL DEFAULT '',
    `scriptwriter` varchar(100) NOT NULL DEFAULT '',
    `actors` text,
    `district` varchar(255) DEFAULT '',
    `language` varchar (255) DEFAULT '',
    `duration` varchar (100) DEFAULT '',
    `abs` text,
    UNIQUE (`title`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;


create table if not exists `movie-category` (
    `id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `mid` int(11) NOT NULL,
    `cid` int(11) NOT NULL,
    KEY `fk_on_movie_id` (`mid`),
    CONSTRAINT `fk_on_movie_id` FOREIGN KEY (`mid`) REFERENCES `movies` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    KEY `fk_on_category_id` (`cid`),
    CONSTRAINT `fk_on_category_id` FOREIGN KEY (`cid`) REFERENCES `categories` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;



# records of category
INSERT INTO `categories` VALUES (1,'plot');
INSERT INTO `categories` VALUES (2,'comedy');
INSERT INTO `categories` VALUES (3,'action');
INSERT INTO `categories` VALUES (4,'love');
INSERT INTO `categories` VALUES (5,'sci-fi');
INSERT INTO `categories` VALUES (6,'cartoon');
INSERT INTO `categories` VALUES (7,'suspenseful');
INSERT INTO `categories` VALUES (8,'thriller');
INSERT INTO `categories` VALUES (9,'fear');
INSERT INTO `categories` VALUES (10,'crime');
INSERT INTO `categories` VALUES (11,'homosexual');
INSERT INTO `categories` VALUES (12,'music');
INSERT INTO `categories` VALUES (13,'song');
INSERT INTO `categories` VALUES (14,'biography');
INSERT INTO `categories` VALUES (15,'history');
INSERT INTO `categories` VALUES (16,'war');
INSERT INTO `categories` VALUES (17,'west');
INSERT INTO `categories` VALUES (18,'fantasy');
INSERT INTO `categories` VALUES (19,'adventure');
INSERT INTO `categories` VALUES (20,'disaster');
INSERT INTO `categories` VALUES (21,'martial');
INSERT INTO `categories` VALUES (22,'erotic');


# -------
# --records of movies
# -------
INSERT INTO `movies` VALUES (1,'https://img1.doubanio.com/view/photo/raw/public/p2621219978.jpg','姜子牙','2020',6.9,'程腾/李炜','谢茜颖','郑希/杨凝/图特哈蒙/阎么么/季冠霖/姜广涛','中国','汉语普通话',110,'故事发生于封神大战后。姜子牙率领众神伐纣，赢得封神大战胜利，以为可以唤回世间安宁。然而，一切并未结束。一个偶然，姜子牙发现了原来“封神大战”之下酝酿着更大的阴谋。姜子牙开始踏上探寻真相的旅途......');

# ------
# --records of movie-category
# ------
INSERT INTO `movie-category` VALUES (1,1,1);
INSERT INTO `movie-category` VALUES (2,1,6);
INSERT INTO `movie-category` VALUES (3,1,18);
INSERT INTO `movie-category` VALUES (4,2,1);
INSERT INTO `movie-category` VALUES (5,2,6);
INSERT INTO `movie-category` VALUES (6,2,18);
INSERT INTO `movie-category` VALUES (7,2,2);
INSERT INTO `movie-category` VALUES (8,3,2);
INSERT INTO `movie-category` VALUES (9,4,6);
INSERT INTO `movie-category` VALUES (10,5,6);
INSERT INTO `movie-category` VALUES (11,6,1);
