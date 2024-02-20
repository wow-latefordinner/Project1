drop table if exists product;
drop table if exists seller;

create table seller (
id serial primary key,
seller varchar(255) unique);

create table product (
id bigint primary key,
name varchar(255),
price numeric(10,2),
seller_id int
);

alter table product add foreign key(seller_id) references seller(id);
