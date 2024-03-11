/*DROP DATABASE "BusInfo";

CREATE DATABASE "BusInfo";*/

\connect "BusInfo"

CREATE SCHEMA Info;

SET search_path = Info;

CREATE TABLE trips (
    trip_ID SERIAL PRIMARY KEY,
    company text NOT NULL,
    price_coef numeric NOT NULL
);

CREATE TABLE clients (
    client_ID SERIAL PRIMARY KEY,
    Name text NOT NULL,
    Address text NOT NULL,
    Phone_number text NOT NULL,
    email text NOT NULL,
    admin boolean NOT NULL,
    login text NOT NULL,
    password text NOT NULL
);

CREATE TABLE stations (
    station_ID SERIAL PRIMARY KEY,
    station_name text NOT NULL
);

CREATE TABLE stations_on_trip (
    trip_ID integer REFERENCES trips ON DELETE CASCADE NOT NULL,
    entry_ID SERIAL PRIMARY KEY,
    station_ID integer REFERENCES stations ON DELETE CASCADE NOT NULL,
    station_number integer NOT NULL,
    avail_seats integer NOT NULL,
    date timestamp NOT NULL
);

CREATE TABLE orders (
    client_ID integer REFERENCES clients ON DELETE CASCADE NOT NULL,
    order_ID SERIAL PRIMARY KEY,
    trip_ID integer REFERENCES stations_on_trip ON DELETE CASCADE NOT NULL,
    start_st_number integer NOT NULL,
    end_st_number integer NOT NULL,
    price numeric NOT NULL
);
