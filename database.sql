create schema if not exists shopdb default character set utf8mb4 collate utf8mb4_unicode_ci;

use shopdb;

create table order_main (
    order_id bigint not null primary key,
    buyer_address varchar(255) null,
    buyer_email varchar(255) null,
    buyer_name varchar(255) null,
    buyer_phone varchar(255) null,
    create_time datetime(6) null,
    order_amount decimal(38, 2) null,
    order_status int default 0 null,
    update_time datetime(6) null
);

create table product_brand (
    brand_id int not null primary key,
    brand_name varchar(255) null,
    brand_type int null,
    create_time datetime(6) null,
    update_time datetime(6) null,
    constraint UK_r7659wi9ypk79p0rfb2npskgg unique (brand_type)
);

create table products (
    product_id bigint auto_increment primary key,
    battery_capacity varchar(255) null,
    brand int null,
    color varchar(255) null,
    cpu varchar(255) null,
    description longtext null,
    filename varchar(255) null,
    model varchar(255) null,
    os varchar(255) null,
    price decimal(38, 2) null,
    product_name varchar(255) null,
    product_status int default 0 null,
    quantity_in_stock int null,
    ram varchar(255) null,
    screen_size varchar(255) null,
    storage_capacity varchar(255) null
);

create table role (
    id int auto_increment primary key,
    role varchar(255) null
);

create table users (
    id bigint auto_increment primary key,
    activation_code varchar(255) null,
    active bit null,
    address varchar(255) null,
    city varchar(255) null,
    country varchar(255) null,
    email varchar(255) not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    password varchar(255) not null,
    password_reset_code varchar(255) null,
    phone_number varchar(255) null,
    post_index varchar(255) null,
    constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email)
);

create table cart (
    user_id bigint not null primary key,
    constraint FKg5uhi8vpsuy0lgloxk2h4w5o6 foreign key (user_id) references users (id)
);

create table product_in_order (
    id bigint auto_increment primary key,
    battery_capacity varchar(255) null,
    brand int null,
    color varchar(255) null,
    count int null,
    cpu varchar(255) null,
    description longtext null,
    filename varchar(255) null,
    model varchar(255) null,
    os varchar(255) null,
    price decimal(38, 2) null,
    product_id bigint null,
    product_name varchar(255) null,
    quantity_in_stock int null,
    ram varchar(255) null,
    screen_size varchar(255) null,
    storage_capacity varchar(255) null,
    cart_user_id bigint null,
    order_id bigint null,
    constraint UK_ryh74thrnje6kpp2l7pt13e7g unique (product_id),
    constraint FKhnivo3fl2qtco3ulm4mq0mbr5 foreign key (cart_user_id) references cart (user_id),
    constraint FKt0sfj3ffasrift1c4lv3ra85e foreign key (order_id) references order_main (order_id)
);

create table users_role (
    users_id bigint not null,
    role_id int not null,
    constraint UK_cdpd2ix59qroxmqubyjqplxn1 unique (role_id),
    constraint FK3qjq7qsiigxa82jgk0i0wuq3g foreign key (role_id) references role (id),
    constraint FKiu0xsee0dmwa28nffgyf4bcvc foreign key (users_id) references users (id)
);
