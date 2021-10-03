CREATE TABLE allocation (
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	room_id BIGINT UNSIGNED NOT NULL,
	employee_name VARCHAR(20) NOT NULL,
	employee_email VARCHAR(64) NOT NULL,
	subject VARCHAR(60) NOT NULL,
	start_at DATETIME(3) NOT NULL,
	end_at DATETIME(3) NOT NULL,
	created_at DATETIME(3) NOT NULL,
	updated_at DATETIME(3) NOT NULL,
	PRIMARY KEY (id),
	foreign key (room_id) references room(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
