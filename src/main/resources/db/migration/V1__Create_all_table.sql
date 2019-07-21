drop table parking_lot if exists;
drop table parking_order if exists;
drop sequence if exists hibernate_sequence ;

create sequence hibernate_sequence start with 26 increment by 1 ;
create table parking_lot
(
    id       bigint       not null,
    capacity integer      not null check (capacity >= 0),
    name     varchar(255) not null,
    position varchar(255),
    primary key (id)
) ;
create table parking_order
(
    id bigint not null,
    created_time bigint,
    leaved_time bigint,
    number_plate integer not null,
    order_status boolean not null,
    parking_lot_name varchar(255),
    primary key (id)
);

alter table parking_lot add constraint UK_PARKING_NAME unique (name);
