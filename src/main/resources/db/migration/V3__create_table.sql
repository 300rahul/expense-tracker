CREATE TABLE public.user_detail (
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
	first_name varchar(128) NOT NULL,
	last_name varchar(128) NOT NULL,
	phone_number varchar(128) NOT NULL,
	password varchar(128) NOT NULL,
	monthly_income decimal(10, 2) NOT NULL,
	created_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT user_detail_pkey PRIMARY KEY (id)
);

CREATE TABLE public.expense (
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
	user_id integer NOT NULL,
	amount integer NOT NULL,
	category varchar(128) NOT NULL,
	type varchar(128) NOT NULL,
	start_time timestamp DEFAULT NULL,
    end_time timestamp DEFAULT NULL,
	created_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT expense_pkey PRIMARY KEY (id)
);

CREATE TABLE public.expense_limit (
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
	user_id integer NOT NULL,
	amount decimal(10, 2) NOT NULL,
	category varchar(128) NOT NULL,
    type varchar(128) NOT NULL,
	created_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT expense_limit_pkey PRIMARY KEY (id)
);