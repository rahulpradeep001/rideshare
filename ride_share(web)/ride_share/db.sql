/*
SQLyog Community v13.0.1 (64 bit)
MySQL - 5.5.20-log : Database - ride_sharing
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ride_sharing` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `ride_sharing`;

/*Table structure for table `cab` */

DROP TABLE IF EXISTS `cab`;

CREATE TABLE `cab` (
  `Cab_Id` int(11) NOT NULL AUTO_INCREMENT,
  `lid` int(11) DEFAULT NULL,
  `Firstname` varchar(50) DEFAULT NULL,
  `lastname` varchar(50) DEFAULT NULL,
  `gender` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `post` varchar(50) DEFAULT NULL,
  `pin` int(11) DEFAULT NULL,
  `contact` bigint(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `proof` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`Cab_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `cab` */

insert  into `cab`(`Cab_Id`,`lid`,`Firstname`,`lastname`,`gender`,`place`,`post`,`pin`,`contact`,`email`,`proof`) values 
(1,3,'fairoos','m','male','naduvattam','nadu',676930,9846109022,'fairoos@gmail.com','storage_emulated_0_Pictures_Screenshots_Screenshot_20230215-122741.jpg');

/*Table structure for table `cab_booking` */

DROP TABLE IF EXISTS `cab_booking`;

CREATE TABLE `cab_booking` (
  `b_id` int(11) NOT NULL AUTO_INCREMENT,
  `c_id` int(11) DEFAULT NULL,
  `lid` int(11) DEFAULT NULL,
  `from` varchar(50) DEFAULT NULL,
  `to` varchar(50) DEFAULT NULL,
  `date` varchar(399) DEFAULT NULL,
  `time` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`b_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `cab_booking` */

insert  into `cab_booking`(`b_id`,`c_id`,`lid`,`from`,`to`,`date`,`time`,`status`) values 
(1,3,4,'kozhikode','tirur','2023-02-15','12:04','accepted'),
(2,3,2,'tirur','kozhikode','2023-02-18','12:04','accepted'),
(3,3,4,'thrithala','trivandrum','2023-02-18','4:05','pending');

/*Table structure for table `complaint` */

DROP TABLE IF EXISTS `complaint`;

CREATE TABLE `complaint` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `lid` int(11) DEFAULT NULL,
  `complaint` varchar(200) DEFAULT NULL,
  `reply` varchar(200) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

/*Data for the table `complaint` */

insert  into `complaint`(`c_id`,`lid`,`complaint`,`reply`,`date`) values 
(1,2,'hello','hello','2023-02-15'),
(2,3,'hello','pending','2023-02-15'),
(3,4,'poodaa','set','2023-02-18'),
(4,5,'good job','pending','2023-02-18'),
(5,4,'very bad service','pending','2023-02-18'),
(6,3,'very bad app','pending','2023-02-18'),
(7,5,'hello','pending','2023-02-18'),
(8,5,'very bad app','pending','2023-02-18'),
(9,3,'hello','pending','2023-02-18'),
(10,5,'hello','pending','2023-02-18'),
(11,2,'hello','pending','2023-02-18'),
(12,2,'hello','pending','2023-02-18'),
(13,3,'good app','pending','2023-02-18');

/*Table structure for table `feedback` */

DROP TABLE IF EXISTS `feedback`;

CREATE TABLE `feedback` (
  `fd_id` int(11) NOT NULL AUTO_INCREMENT,
  `u_id` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `feedback` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`fd_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `feedback` */

insert  into `feedback`(`fd_id`,`u_id`,`date`,`feedback`) values 
(1,2,'2023-02-15','hello'),
(2,4,'2023-02-18','good service'),
(3,2,'2023-02-18','good app');

/*Table structure for table `location` */

DROP TABLE IF EXISTS `location`;

CREATE TABLE `location` (
  `location_id` int(11) NOT NULL AUTO_INCREMENT,
  `lid` int(11) DEFAULT NULL,
  `latitude` varchar(50) DEFAULT NULL,
  `longitude` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`location_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `location` */

insert  into `location`(`location_id`,`lid`,`latitude`,`longitude`) values 
(1,3,'11.25780855','75.7845407');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `lid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`lid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`lid`,`username`,`password`,`type`) values 
(1,'admin','admin','admin'),
(2,'rahul','rahul123','user'),
(3,'fairoos','fairoos123','cab'),
(4,'shibla','shibla123','user'),
(5,'ramsons','ramsons123','workshop');

/*Table structure for table `rating` */

DROP TABLE IF EXISTS `rating`;

CREATE TABLE `rating` (
  `r_id` int(11) NOT NULL AUTO_INCREMENT,
  `lid` int(11) DEFAULT NULL,
  `w_id` int(11) DEFAULT NULL,
  `rating` float DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`r_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `rating` */

insert  into `rating`(`r_id`,`lid`,`w_id`,`rating`,`date`) values 
(1,3,6,4,'2023-02-18'),
(2,3,5,4,'2023-02-18'),
(3,3,5,3,'2023-02-18'),
(4,3,5,1.5,'2023-02-18'),
(5,3,5,2,'2023-02-18');

/*Table structure for table `request` */

DROP TABLE IF EXISTS `request`;

CREATE TABLE `request` (
  `req_id` int(11) NOT NULL AUTO_INCREMENT,
  `u_id` int(11) DEFAULT NULL,
  `rid` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`req_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `request` */

insert  into `request`(`req_id`,`u_id`,`rid`,`date`,`status`) values 
(1,4,1,'2023-02-15','accepted'),
(2,4,1,'2023-02-15','accepted'),
(3,4,3,'2023-02-18','accepted'),
(4,2,4,'2023-02-18','accepted'),
(5,4,3,'2023-02-18','accepted');

/*Table structure for table `ride_info` */

DROP TABLE IF EXISTS `ride_info`;

CREATE TABLE `ride_info` (
  `r_id` int(11) NOT NULL AUTO_INCREMENT,
  `u_id` int(11) DEFAULT NULL,
  `from` varchar(50) DEFAULT NULL,
  `to` varchar(50) DEFAULT NULL,
  `date` varchar(300) DEFAULT NULL,
  `status` varchar(40) DEFAULT NULL,
  `time` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`r_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `ride_info` */

insert  into `ride_info`(`r_id`,`u_id`,`from`,`to`,`date`,`status`,`time`) values 
(1,2,'Kozhikode ','tirur','23/10/23','completed','23:04'),
(2,4,'tirur','edappal','10/04/23','completed','05:23'),
(3,2,'edappal','thrissur','23/10/2001','completed','23:04'),
(4,4,'tirur','kannur','23/10/23','pending','09:04'),
(5,4,'tirur','trivandrum','23/11/23','pending','4:05');

/*Table structure for table `service` */

DROP TABLE IF EXISTS `service`;

CREATE TABLE `service` (
  `s_id` int(11) NOT NULL AUTO_INCREMENT,
  `w_id` int(11) DEFAULT NULL,
  `service` varchar(100) DEFAULT NULL,
  `details` varchar(500) DEFAULT NULL,
  `cost` int(11) DEFAULT NULL,
  PRIMARY KEY (`s_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `service` */

insert  into `service`(`s_id`,`w_id`,`service`,`details`,`cost`) values 
(1,5,'car wash','full body wash',300),
(2,5,'oil change','for car',2000);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `u_id` int(11) NOT NULL AUTO_INCREMENT,
  `lid` int(11) DEFAULT NULL,
  `firstname` varchar(50) DEFAULT NULL,
  `lastname` varchar(50) DEFAULT NULL,
  `gender` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `post` varchar(50) DEFAULT NULL,
  `pin` int(11) DEFAULT NULL,
  `contact` bigint(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `photo` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`u_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`u_id`,`lid`,`firstname`,`lastname`,`gender`,`place`,`post`,`pin`,`contact`,`email`,`photo`) values 
(1,2,'rahul','pradeep','male','tirur','mangattiri',676105,7306015465,'rahul@gmail.com','storage_emulated_0_Pictures_Instagram_IMG_20221117_184833_678.jpg'),
(2,4,'shibla','pv','female','edappal','vattamkulam',679578,9633749686,'shibla@gmail.com','storage_emulated_0_Pictures_Screenshots_Screenshot_20221121-081210.jpg');

/*Table structure for table `w_booking` */

DROP TABLE IF EXISTS `w_booking`;

CREATE TABLE `w_booking` (
  `b_id` int(11) NOT NULL AUTO_INCREMENT,
  `lid` int(11) DEFAULT NULL,
  `service_id` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `time` varchar(20) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`b_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `w_booking` */

insert  into `w_booking`(`b_id`,`lid`,`service_id`,`date`,`time`,`status`) values 
(1,3,1,'2023-02-18','11:40:25','accepted'),
(2,2,1,'2023-02-18','12:03:22','accepted'),
(3,2,1,'2023-02-18','12:42:10','accepted'),
(4,3,1,'2023-02-18','12:45:05','pending'),
(5,4,1,'2023-02-18','15:45:25','accepted'),
(6,3,1,'2023-02-18','15:47:28','pending');

/*Table structure for table `workshop` */

DROP TABLE IF EXISTS `workshop`;

CREATE TABLE `workshop` (
  `w_id` int(11) NOT NULL AUTO_INCREMENT,
  `lid` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `post` varchar(50) DEFAULT NULL,
  `pin` int(11) DEFAULT NULL,
  `contact` bigint(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `latitude` varchar(300) DEFAULT NULL,
  `longitude` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`w_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `workshop` */

insert  into `workshop`(`w_id`,`lid`,`name`,`place`,`post`,`pin`,`contact`,`email`,`latitude`,`longitude`) values 
(1,5,'ramsons','tirur','koorg',676321,7306016541,'ramsons@gmail.com','11.25785019','75.78453037');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
