create database ads_service character set utf8 collate utf8_general_ci;

use ads_service;

create table region(
  id int not null auto_increment primary key ,
  name varchar(255) not null
)engine InnoDB character set utf8 collate utf8_general_ci;

create table city(
  id int not null auto_increment primary key ,
  name varchar(255) not null ,
  region_id int not null ,
  foreign key (region_id) references region(id)on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

create table user(
  id int not null auto_increment primary key ,
  name varchar(255) not null ,
  surname varchar(255) not null ,
  username varchar(255) not null ,
  password varchar(255) not null ,
  telephone varchar(9) not null ,
  register_date datetime not null ,
  image varchar(255) not null ,
  role varchar(255) not null ,
  city_id int not null ,
  foreign key (city_id) references city(id) on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

create table message(
  id int not null auto_increment primary key ,
  message varchar(255) not null ,
  send_date datetime not null ,
  status varchar(255) not null ,
  from_user int not null ,
  to_user int not null ,
  foreign key (from_user) references user(id) on delete cascade ,
  foreign key (to_user) references user(id) on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

create table category(
  id int not null auto_increment primary key ,
  name varchar(255) not null ,
  name_ru varchar(255) not null ,
  name_arm varchar(255) not null ,
  parent_id int,
  foreign key (parent_id) references category(id)on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

create table category_attribute(
  id int not null auto_increment primary key ,
  name varchar(255) not null ,
  name_arm varchar(255) not null ,
  name_ru varchar(255) not null ,
  category_id int not null ,
  foreign key (category_id) references category(id) on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

create table ad(
  id int not null auto_increment primary key ,
  title varchar(255) not null ,
  description text not null ,
  price double not null ,
  image varchar(255) not null ,
  created_date datetime not null ,
  category_id int not null ,
  user_id int not null ,
  foreign key (category_id) references category(id) on delete cascade ,
  foreign key (user_id) references user(id) on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

create table attribute_ad(
  id int not null auto_increment primary key ,
  value varchar(255) not null ,
  ad_id int not null ,
  category_attribute_id int not null ,
  foreign key (ad_id) references ad(id) on delete cascade ,
  foreign key (category_attribute_id) references category_attribute(id) on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

create table image(
  id int not null auto_increment primary key ,
  name varchar(255) not null ,
  ad_id int not null ,
  foreign key (ad_id) references ad(id) on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

create table comment (
  id int not null auto_increment primary key ,
  comment text not null ,
  send_date datetime not null ,
  ad_id int not null ,
  user_id int not null ,
  foreign key (ad_id) references ad(id) on delete cascade ,
  foreign key (user_id) references user(id) on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

create table likes(
  user_id int not null ,
  ad_id int not null ,
  foreign key (user_id) references user(id) on delete cascade ,
  foreign key (ad_id) references ad(id) on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

create table dislikes(
  user_id int not null ,
  ad_id int not null ,
  foreign key (user_id) references user(id) on delete cascade ,
  foreign key (ad_id) references ad(id) on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;