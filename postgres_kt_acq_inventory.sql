CREATE SCHEMA IF NOT EXISTS kitchen_talk;

DROP TABLE IF EXISTS kitchen_talk.acq_inventory;
DROP SEQUENCE IF EXISTS kitchen_talk.acq_inventory_seq;

CREATE SEQUENCE IF NOT EXISTS kitchen_talk.acq_inventory_seq;

CREATE TABLE kitchen_talk.acq_inventory
(
    seq bigint DEFAULT nextval('kitchen_talk.acq_inventory_seq'::regclass) NOT NULL,
    serial_number int8 NOT NULL,
    description VARCHAR(30) NOT NULL,
    category VARCHAR(20) NOT NULL,
    assigned BOOLEAN DEFAULT false NOT NULL,
    employee_id INTEGER,
    employee_first_name VARCHAR(30),
    employee_last_name VARCHAR(40),
    employee_dt_assignment TIMESTAMP WITH TIME ZONE,
    dt_admission TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL,
	dt_egress TIMESTAMP WITH TIME ZONE,
    enable boolean DEFAULT true NOT NULL
);

CREATE UNIQUE INDEX key_acq_inventory ON kitchen_talk.acq_inventory (seq);
CREATE UNIQUE INDEX ix_acq_inventory_unique_business ON kitchen_talk.acq_inventory (serial_number);

INSERT INTO kitchen_talk.acq_inventory (serial_number, description, category, assigned, employee_id, employee_first_name, employee_last_name, employee_dt_assignment)
VALUES (9999999, 'DELL Latitude E5550', 'Notebook Computer', true, 135718, 'Thedrick', 'Flippen', now());

INSERT INTO kitchen_talk.acq_inventory (serial_number, description, category, assigned, employee_id, employee_first_name, employee_last_name, employee_dt_assignment)
VALUES (9999998, 'DELL Latitude E5550', 'Notebook Computer', true, 135719, 'Baudoin', 'Josilevich', now());

INSERT INTO kitchen_talk.acq_inventory (serial_number, description, category, assigned, employee_id, employee_first_name, employee_last_name, employee_dt_assignment)
VALUES (9999997, 'DELL Latitude E5550', 'Notebook Computer', true, 135720, 'Gabbey', 'Stradling', now());

INSERT INTO kitchen_talk.acq_inventory (serial_number, description, category, assigned)
VALUES (9999996, 'DELL Latitude E5550', 'Notebook Computer', false);

INSERT INTO kitchen_talk.acq_inventory (serial_number, description, category, assigned)
VALUES (9999995, 'DELL Latitude E5550', 'Notebook Computer', false);

INSERT INTO kitchen_talk.acq_inventory (serial_number, description, category, assigned)
VALUES (9999994, 'DELL Latitude E5550', 'Notebook Computer', false);