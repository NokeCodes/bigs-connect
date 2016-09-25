-- :mode=pl-sql:

CREATE TYPE privileges AS ENUM (
  'regular', 'admin'
);

CREATE TABLE users
(
    id SERIAL,
    name character varying(255) NOT NULL,
    mail_address character varying(255) NOT NULL,
    pass character varying(255) NOT NULL,
    admin privileges NOT NULL DEFAULT FALSE,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT users_mail_address_key UNIQUE (mail_address)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE users
    OWNER to postgres;

CREATE TABLE points
(
    id SERIAL,
    positon point NOT NULL,
    comment text NOT NULL,
    CONSTRAINT points_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE points
    OWNER to postgres;

CREATE TABLE password_resets
(
    id SERIAL,
    key character varying(255) NOT NULL,
    usr integer NOT NULL,
    mark timestamp without time zone NOT NULL,
    CONSTRAINT password_resets_pkey PRIMARY KEY (id),
    CONSTRAINT password_resets_user_fkey FOREIGN KEY ("usr")
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE password_resets
    OWNER to postgres;

CREATE TABLE checkins
(
    mark timestamp without time zone NOT NULL,
    type boolean NOT NULL,
    big integer NOT NULL,
    id SERIAL,
    CONSTRAINT checkins_pkey PRIMARY KEY (id),
    CONSTRAINT checkins_id_key UNIQUE (id),
    CONSTRAINT checkins_big_fkey FOREIGN KEY (big)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE checkins
    OWNER to postgres;

CREATE FUNCTION check_checkin_checkout()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100.0
AS $BODY$
DECLARE
  checkin_count integer;
  checkout_count integer;
BEGIN
-- Make sure we have some kind of location data
  IF NEW.location_text IS NULL AND NEW.location_gps IS NULL THEN
    RETURN NULL;
  END IF;

-- Make sure a big only checks in when they aren't checked in, and can't check out unless they're checked in
  SELECT COUNT(*) INTO checkin_count FROM checkins WHERE checkins.type = TRUE AND checkins.big = NEW.big AND checkins.little = NEW.little;
  SELECT COUNT(*) INTO checkout_count FROM checkins WHERE checkins.type = FALSE AND checkins.big = NEW.big AND checkins.little = NEW.little;
  IF NEW.type = TRUE THEN
    IF checkin_count + 1 <> checkout_count THEN
      RETURN NULL;
    END IF;
  ELSE
    IF checkin_count <> checkout_count THEN
      RETURN NULL;
    END IF;
  END IF;
  RETURN NEW;
END;

$BODY$;

ALTER FUNCTION check_checkin_checkout()
    OWNER TO postgres;

COMMENT ON FUNCTION check_checkin_checkout()
    IS 'Make sure a checkout follows all the necessary constraints
* A big can only checkout their associated little
* A big can only checkin when they aren''t already checked in
* A big can only checkout when they''re checked in';


CREATE TRIGGER ensure_checkin_checkout_sanity
    BEFORE INSERT
    ON checkins
    FOR EACH ROW
    EXECUTE PROCEDURE check_checkin_checkout();

GRANT EXECUTE ON FUNCTION check_checkin_checkout() TO bbbs;

GRANT ALL ON SEQUENCE checkins_id_seq TO bbbs;

GRANT ALL ON SEQUENCE password_resets_id_seq TO bbbs;

GRANT ALL ON SEQUENCE points_id_seq TO bbbs;

GRANT ALL ON SEQUENCE users_id_seq TO bbbs;

GRANT ALL ON TABLE checkins TO bbbs;

GRANT ALL ON TABLE password_resets TO bbbs;

GRANT ALL ON TABLE points TO bbbs;

GRANT ALL ON TABLE users TO bbbs;
