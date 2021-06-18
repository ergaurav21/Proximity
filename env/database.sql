create database proximity-db;

CREATE TABLE `tbl_courses` (
                               `course_id` bigint(20) NOT NULL,
                               `is_active` tinyint(1) DEFAULT NULL,
                               `course_name` varchar(255) NOT NULL,
                               `creation_date` datetime NOT NULL,
                               `description` varchar(255) DEFAULT NULL,
                               `last_modified` datetime NOT NULL,
                               `last_modified_by` bigint(20) DEFAULT NULL,
                               PRIMARY KEY (`course_id`),
                               UNIQUE KEY `UK_qkh8hi6dqo6styjn67uwma2hg` (`course_name`),
                               KEY `FKp8e28jrq3cbtnp0do5w2a3543` (`last_modified_by`),
                               CONSTRAINT `FKp8e28jrq3cbtnp0do5w2a3543` FOREIGN KEY (`last_modified_by`) REFERENCES `tbl_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tbl_course_subjects` (
                                       `course_id` bigint(20) NOT NULL,
                                       `subject_id` bigint(20) NOT NULL,
                                       PRIMARY KEY (`course_id`,`subject_id`),
                                       UNIQUE KEY `UK_2hdl8sdk4lnjf7ddi3c22s81o` (`subject_id`),
                                       CONSTRAINT `FKhj2d95dhvdclem931hpnhak4e` FOREIGN KEY (`subject_id`) REFERENCES `tbl_subjects` (`subject_id`),
                                       CONSTRAINT `FKsqjabhc5jowfwuexo1b89lut9` FOREIGN KEY (`course_id`) REFERENCES `tbl_courses` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tbl_lesson` (
                              `lesson_id` bigint(20) NOT NULL,
                              `is_active` bit(1) DEFAULT NULL,
                              `creation_date` datetime DEFAULT NULL,
                              `description` varchar(255) DEFAULT NULL,
                              `last_modified` datetime DEFAULT NULL,
                              `title` varchar(255) DEFAULT NULL,
                              `last_modified_by` bigint(20) DEFAULT NULL,
                              PRIMARY KEY (`lesson_id`),
                              KEY `FKqr2l0nr6hvjfvv7fa1ucf3oc5` (`last_modified_by`),
                              CONSTRAINT `FKqr2l0nr6hvjfvv7fa1ucf3oc5` FOREIGN KEY (`last_modified_by`) REFERENCES `tbl_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tbl_lesson_courses` (
                                      `course_id` bigint(20) NOT NULL,
                                      `lesson_id` bigint(20) NOT NULL,
                                      `tag_id` bigint(20) NOT NULL,
                                      `video_id` bigint(20) NOT NULL,
                                      PRIMARY KEY (`lesson_id`,`course_id`),
                                      UNIQUE KEY `UK_ouhwtevwlaxs0crc1nepga6qp` (`course_id`),
                                      CONSTRAINT `FKnyudsnpvwcsbny12exo8ptwae` FOREIGN KEY (`lesson_id`) REFERENCES `tbl_lesson` (`lesson_id`),
                                      CONSTRAINT `FKraxej4n1hrchamjoi8bpahxlm` FOREIGN KEY (`course_id`) REFERENCES `tbl_courses` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tbl_subjects` (
                                `subject_id` bigint(20) NOT NULL,
                                `is_active` tinyint(1) DEFAULT NULL,
                                `creation_date` datetime DEFAULT NULL,
                                `description` varchar(255) DEFAULT NULL,
                                `last_modified` datetime DEFAULT NULL,
                                `subject_name` varchar(255) NOT NULL,
                                `last_modified_by` bigint(20) DEFAULT NULL,
                                PRIMARY KEY (`subject_id`),
                                UNIQUE KEY `UK_n51y00ykyv7n79u3wp62ea5c7` (`subject_name`),
                                KEY `FKbn3p4n8nda5gdfj3u32er8fcw` (`last_modified_by`),
                                CONSTRAINT `FKbn3p4n8nda5gdfj3u32er8fcw` FOREIGN KEY (`last_modified_by`) REFERENCES `tbl_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tbl_tags` (
                            `tag_id` bigint(20) NOT NULL,
                            `is_active` tinyint(1) DEFAULT NULL,
                            `creation_date` datetime DEFAULT NULL,
                            `description` varchar(255) DEFAULT NULL,
                            `last_modified` datetime DEFAULT NULL,
                            `tag_name` varchar(255) NOT NULL,
                            `last_modified_by` bigint(20) DEFAULT NULL,
                            PRIMARY KEY (`tag_id`),
                            UNIQUE KEY `UK_wqj5l6u59v7vrwjjvrltftbf` (`tag_name`),
                            KEY `FKcya7xhql1in6quqkgls1gbfff` (`last_modified_by`),
                            CONSTRAINT `FKcya7xhql1in6quqkgls1gbfff` FOREIGN KEY (`last_modified_by`) REFERENCES `tbl_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tbl_user` (
                            `user_id` bigint(20) NOT NULL,
                            `is_active` bit(1) DEFAULT NULL,
                            `email` varchar(255) DEFAULT NULL,
                            `first_name` varchar(255) NOT NULL,
                            `is_instructor` bit(1) DEFAULT NULL,
                            `last_name` varchar(255) NOT NULL,
                            `password` varchar(255) DEFAULT NULL,
                            `user_name` varchar(255) NOT NULL,
                            PRIMARY KEY (`user_id`),
                            UNIQUE KEY `UK_6jr81l5qqpxjp72fgi23ubqc9` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE `tbl_video` (
                             `video_id` bigint(20) NOT NULL,
                             `is_active` bit(1) DEFAULT NULL,
                             `creation_date` datetime DEFAULT NULL,
                             `description` varchar(255) DEFAULT NULL,
                             `last_modified` datetime DEFAULT NULL,
                             `link` varchar(255) DEFAULT NULL,
                             `title` varchar(255) DEFAULT NULL,
                             `last_modified_by` bigint(20) DEFAULT NULL,
                             PRIMARY KEY (`video_id`),
                             KEY `FKn9wnce028j2vf2ud82laa5oxm` (`last_modified_by`),
                             CONSTRAINT `FKn9wnce028j2vf2ud82laa5oxm` FOREIGN KEY (`last_modified_by`) REFERENCES `tbl_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tbl_video_lessons` (
                                     `video_id` bigint(20) NOT NULL,
                                     `lesson_id` bigint(20) NOT NULL,
                                     PRIMARY KEY (`video_id`,`lesson_id`),
                                     UNIQUE KEY `UK_4gwq3cnajlstvetlxo22kyo23` (`lesson_id`),
                                     CONSTRAINT `FK36fckx6n79r5m6lv0muc12xv7` FOREIGN KEY (`lesson_id`) REFERENCES `tbl_lesson` (`lesson_id`),
                                     CONSTRAINT `FKjysynqv9mg2jr92yjh5eca3dc` FOREIGN KEY (`video_id`) REFERENCES `tbl_video` (`video_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tbl_video_tags` (
                                  `tag_id` bigint(20) NOT NULL,
                                  `video_id` bigint(20) NOT NULL,
                                  PRIMARY KEY (`video_id`,`tag_id`),
                                  UNIQUE KEY `UK_gdyk63bvj50eyku7m3ryfjbwq` (`tag_id`),
                                  CONSTRAINT `FK19xaj9vs8ntacq05retswlx1w` FOREIGN KEY (`video_id`) REFERENCES `tbl_video` (`video_id`),
                                  CONSTRAINT `FKks7s2mwpmiol11v4i6nqesxuw` FOREIGN KEY (`tag_id`) REFERENCES `tbl_tags` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

























