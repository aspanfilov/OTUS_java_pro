create table client (
    id bigint generated by default as identity primary key,
    name varchar(255),
    address_id bigint
);

create table address (
    id bigint generated by default as identity primary key,
    apartment_number integer,
    building_number integer,
    city varchar(255),
    country varchar(255) not null,
    house_number integer,
    region varchar(255),
    street varchar(255)
);

create table phone (
    id bigint generated by default as identity primary key,
    number varchar(255) not null,
    client_id bigint
);

--alter table address
--    add constraint UK_fx5oeyyotqc1jurjbmt9pwgjq unique (country);

--alter table phone
--    add constraint UK_jpobbsduo00bgyro8gurj7for unique (number);

alter table client
    add constraint FKb137u2cl2ec0otae32lk5pcl2
    foreign key (address_id)
    references address;

alter table phone
    add constraint FK3o48ec26lujl3kf01hwqplhn2
    foreign key (client_id)
    references client;
