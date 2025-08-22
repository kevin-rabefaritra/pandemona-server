create table if not exists drugstore (
    id          serial primary key,
    name        varchar(100) not null,
    address     varchar(255) not null,
    contacts    varchar(255) default null,
    latitude    double precision default 0,
    longitude   double precision default 0,
    features    varchar(255) default null,
    city        varchar(50),
    created_at  timestamp,
    updated_at  timestamp,
    deleted     boolean default false
);

create table if not exists onduty_drugstores (
    id          serial primary key,
    start_date  date not null,
    end_date    date not null,
    created_at  timestamp,
    updated_at  timestamp,
    deleted     boolean default false
);

create table if not exists onduty_drugstores_drugstore (
    id                      serial primary key,
    onduty_drugstores_id    integer not null,
    drugstore_id            integer not null
);

create table if not exists emergency_number (
    id          serial primary key,
    name        varchar(100) not null,
    address     varchar(255),
    contacts    varchar(255) not null,
    latitude    double precision default 0,
    longitude   double precision default 0,
    city        varchar(50),
    type        varchar(50),
    created_at  timestamp,
    updated_at  timestamp,
    deleted     boolean default false
);

create table if not exists health_center (
    id          serial primary key,
    name        varchar(100) not null,
    address     varchar(255) not null,
    contacts    varchar(255) not null,
    latitude    double precision default 0,
    longitude   double precision default 0,
    city        varchar(50),
    type        varchar(50),
    created_at  timestamp,
    updated_at  timestamp,
    deleted     boolean default false
);

create table if not exists report (
    id          serial primary key,
    title       varchar(500) not null,
    comment     varchar(500) not null,
    created_at  timestamp
);


-- CREATE SEQUENCE IF NOT EXISTS drugstore_seq START 1030;
-- CREATE SEQUENCE IF NOT EXISTS onduty_drugstores_seq START 50;
-- CREATE SEQUENCE IF NOT EXISTS onduty_drugstores_drugstore_seq START 100;
-- CREATE SEQUENCE IF NOT EXISTS emergency_number_seq START 1;
-- CREATE SEQUENCE IF NOT EXISTS health_center_seq START 1;

alter table onduty_drugstores_drugstore drop constraint if exists fk_onduty_drugstores_id;
alter table onduty_drugstores_drugstore add constraint fk_onduty_drugstores_id foreign key (onduty_drugstores_id) references onduty_drugstores(id) on delete cascade;

alter table onduty_drugstores_drugstore drop constraint if exists fk_drugstore_id;
alter table onduty_drugstores_drugstore add constraint fk_drugstore_id foreign key (drugstore_id) references drugstore(id) on delete cascade;

alter table drugstore alter column city type varchar(50);

SELECT SETVAL((SELECT PG_GET_SERIAL_SEQUENCE('"drugstore"', 'id')), (SELECT (MAX("id") + 1) FROM "drugstore"), FALSE);