create table if not exists drugstore (
    id          serial primary key,
    name        varchar(100) not null,
    address     varchar(255) not null,
    contacts    varchar(255) not null,
    latitude    double precision default 0,
    longitude   double precision default 0,
    features    varchar(255) default null,
    city        smallint,
    created_at  timestamp,
    updated_at  timestamp,
    is_deleted  boolean default false
);

create table if not exists onduty_drugstores (
    id          serial primary key,
    start_date  date not null,
    end_date    date not null,
    created_at  timestamp,
    updated_at  timestamp,
    is_deleted  boolean default false
);

create table if not exists onduty_drugstores_drugstore (
    id                      serial primary key,
    onduty_drugstores_id    integer not null,
    drugstore_id            integer not null
);

alter table onduty_drugstores_drugstore drop constraint if exists fk_onduty_drugstores_id;
alter table onduty_drugstores_drugstore add constraint fk_onduty_drugstores_id foreign key (onduty_drugstores_id) references onduty_drugstores(id) on delete cascade;

alter table onduty_drugstores_drugstore drop constraint if exists fk_drugstore_id;
alter table onduty_drugstores_drugstore add constraint fk_drugstore_id foreign key (drugstore_id) references drugstore(id) on delete cascade;