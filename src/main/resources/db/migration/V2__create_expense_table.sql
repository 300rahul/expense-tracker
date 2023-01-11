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