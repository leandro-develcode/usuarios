drop table if exists users;
create table users(
    id int(11) not null auto_increment,
    name varchar(255) not null,
    birthday date not null,
    image varchar(255) not null,
    primary key (id)
);