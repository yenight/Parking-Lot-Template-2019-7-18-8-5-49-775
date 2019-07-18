drop table parking_lot if exists;

drop sequence if exists hibernate_sequence ;
create sequence hibernate_sequence start with 1 increment by 1 ;
create table parking_lot
(
    id       bigint       not null,
    capacity integer      not null check (capacity >= 0),
    name     varchar(255) not null,
    position varchar(255),
    primary key (id)
) ;
alter table parking_lot add constraint UK_PARKING_NAME unique (name);