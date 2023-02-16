DROP TABLE IF EXISTS category, users, events, requests, compilations, events_compilations;

CREATE TABLE IF NOT EXISTS category
(
    id   serial     not null
        constraint pk_category
            primary key,
    name varchar(50) not null
);

ALTER TABLE  category
    owner to root;

CREATE TABLE IF NOT EXISTS users
(
    id    serial
        constraint pk_user
            primary key,
    email varchar(50)  not null
        constraint uq_user_email
            unique,
    name  varchar(100) not null
);

ALTER TABLE  users
    owner to root;

CREATE TABLE IF NOT EXISTS events
(
    id  serial not null
        constraint pk_event
            primary key,
    annotation         varchar(500),
    category           integer                                              not null
        constraint category_fk
            references category,
    confirmed_requests integer                                              not null,
    created_on         timestamp with time zone                             not null,
    description        varchar(1000),
    event_date         timestamp with time zone                             not null,
    initiator          integer                                              not null
        constraint user_fk
            references users,
    location_lat       double precision,
    location_lon       double precision,
    paid               boolean                                              not null,
    participant_limit  integer default 0                                    not null,
    published_on       timestamp with time zone,
    request_moderation boolean default true                                 not null,
    state              varchar(20)                                          not null,
    title              varchar(100)                                         not null,
    views              integer
);

ALTER TABLE events
    owner to root;

CREATE TABLE IF NOT EXISTS requests
(
    id        serial     not null  constraint pk_request primary key,
    created   timestamp with time zone  not null,
    event     integer    not null constraint event_fk references events,
    requester integer    not null constraint requester_fk references users,
    status    varchar(20) not null
);

ALTER TABLE  requests
    owner to root;

CREATE TABLE IF NOT EXISTS compilations
(
    id        serial     not null  constraint pk_compilation primary key,
    pinned    boolean,
    title     varchar(255)
);

ALTER TABLE  compilations
    owner to root;

CREATE TABLE IF NOT EXISTS events_compilations
(
    compilation_id  integer   not null CONSTRAINT compilation_fk REFERENCES compilations,
    event_id        integer   NOT NULL CONSTRAINT eventComp_fk REFERENCES events
);

ALTER TABLE  events_compilations
    owner to root;

TRUNCATE events_compilations,
    compilations,
    requests,
    events, users, category RESTART IDENTITY;

