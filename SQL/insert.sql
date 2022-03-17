DELETE from info.trips;
DELETE from info.stations;
DELETE from info.stations_on_trip;
DELETE from info.clients;
DELETE from info.orders;

INSERT INTO info.trips(trip_id, company, price_coef) VALUES
	(1, 'Umbrella', 1.2);

INSERT INTO info.stations(station_id, station_name) VALUES
	(1, 'Helheim'),
	(2, 'Midgard'),
	(3, 'Asgard'),
	(4, 'Jotunheim');

INSERT INTO info.stations_on_trip(entry_id, trip_id, station_id, station_number, avail_seats, date) VALUES
	(1, 1, 1, 4, 19, '2022-01-01'),
	(2, 1, 2, 3, 18, '2022-01-01'),
	(3, 1, 3, 2, 18, '2022-01-01'),
	(4, 1, 4, 1, 19, '2022-01-01');

INSERT INTO info.clients(client_id, name, address, phone_number, email, admin, login, password) VALUES
	(1, 'Max Payne', 'Pushkin street', '8800553535', '123@gmail.com', false, 'admin', 'admin'),
	(2, 'Vito Scaletto', 'Lomonosov street', '900', '321@gmail.com', true, 'admin', 'admin');

INSERT INTO info.orders(client_id, trip_id, start_st_number, end_st_number, price) VALUES
	(1, 1, 1, 4, 100),
	(2, 1, 2, 3, 50);

