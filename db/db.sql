-- Database: bbbs

-- DROP DATABASE bbbs;

CREATE DATABASE bbbs
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'C'
    LC_CTYPE = 'C'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
    
-- SCHEMA: public

-- DROP SCHEMA public ;

CREATE SCHEMA public
    AUTHORIZATION postgres;

COMMENT ON SCHEMA public
    IS 'standard public schema';

GRANT ALL ON SCHEMA public TO postgres;

GRANT ALL ON SCHEMA public TO PUBLIC;

CREATE SEQUENCE public.checkins_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.checkins_id_seq
    OWNER TO postgres;

CREATE SEQUENCE public.little_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.little_id_seq
    OWNER TO postgres;

CREATE SEQUENCE public.password_resets_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.password_resets_id_seq
    OWNER TO postgres;

CREATE SEQUENCE public.points_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.points_id_seq
    OWNER TO postgres;

CREATE SEQUENCE public.users_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.users_id_seq
    OWNER TO postgres;

-- Table: public.users

-- DROP TABLE public.users;

CREATE TABLE public.users
(
    id integer NOT NULL DEFAULT nextval('users_id_seq'::regclass),
    name character varying(255) COLLATE "default".pg_catalog NOT NULL,
    mail_address character varying(255) COLLATE "default".pg_catalog NOT NULL,
    pass character varying(255) COLLATE "default".pg_catalog NOT NULL,
    admin boolean NOT NULL DEFAULT FALSE,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT users_mail_address_key UNIQUE (mail_address)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.users
    OWNER to postgres;

-- Table: public.little

-- DROP TABLE public.little;

CREATE TABLE public.little
(
    name character varying(255) COLLATE "default".pg_catalog NOT NULL,
    id integer NOT NULL DEFAULT nextval('little_id_seq'::regclass),
    CONSTRAINT little_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.little
    OWNER to postgres;

-- Table: public.points

-- DROP TABLE public.points;

CREATE TABLE public.points
(
    id integer NOT NULL DEFAULT nextval('points_id_seq'::regclass),
    "position" point NOT NULL,
    comment text COLLATE "default".pg_catalog NOT NULL,
    CONSTRAINT points_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.points
    OWNER to postgres;

-- Table: public.password_resets

-- DROP TABLE public.password_resets;

CREATE TABLE public.password_resets
(
    id integer NOT NULL DEFAULT nextval('password_resets_id_seq'::regclass),
    key character varying(255) COLLATE "default".pg_catalog NOT NULL,
    "user" integer NOT NULL,
    mark timestamp without time zone NOT NULL,
    CONSTRAINT password_resets_pkey PRIMARY KEY (id),
    CONSTRAINT password_resets_user_fkey FOREIGN KEY ("user")
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.password_resets
    OWNER to postgres;

-- Table: public.checkins

-- DROP TABLE public.checkins;

CREATE TABLE public.checkins
(
    mark timestamp without time zone NOT NULL,
    type boolean NOT NULL,
    big integer NOT NULL,
    little integer NOT NULL,
    id integer NOT NULL DEFAULT nextval('checkins_id_seq'::regclass),
    CONSTRAINT checkins_pkey PRIMARY KEY (id),
    CONSTRAINT checkins_id_key UNIQUE (id),
    CONSTRAINT checkins_big_fkey FOREIGN KEY (big)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT checkins_little_fkey FOREIGN KEY (little)
        REFERENCES public.little (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

-- Table: public.big_littles

-- DROP TABLE public.big_littles;

CREATE TABLE public.big_littles
(
    id integer NOT NULL DEFAULT nextval('big_littles_id_seq'::regclass),
    big integer NOT NULL,
    little integer NOT NULL,
    CONSTRAINT big_littles_pkey PRIMARY KEY (id),
    CONSTRAINT big_littles_big_key UNIQUE (big),
    CONSTRAINT big_littles_little_key UNIQUE (little),
    CONSTRAINT big_littles_big_fkey FOREIGN KEY (big)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT big_littles_little_fkey FOREIGN KEY (little)
        REFERENCES public.little (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.big_littles
    OWNER to postgres;

ALTER TABLE public.checkins
    OWNER to postgres;

-- FUNCTION: public.check_checkin_checkout()

-- DROP FUNCTION public.check_checkin_checkout();

CREATE FUNCTION public.check_checkin_checkout()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100.0
AS $BODY$
DECLARE
  checkin_count integer;
  checkout_count integer;
  association_count integer;
BEGIN
-- Make sure a big only checks out their associated little
  SELECT COUNT(*) INTO association_count FROM big_littles WHERE big_littles.big = checkins.big AND big_littles.little = NEW.little;
  IF association_count <> 1 THEN
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

ALTER FUNCTION public.check_checkin_checkout()
    OWNER TO postgres;

COMMENT ON FUNCTION public.check_checkin_checkout()
    IS 'Make sure a checkout follows all the necessary constraints
* A big can only checkout their associated little
* A big can only checkin when they aren''t already checked in
* A big can only checkout when they''re checked in';


-- Trigger: ensure_checkin_checkout_sanity

-- DROP TRIGGER ensure_checkin_checkout_sanity ON public.checkins;

CREATE TRIGGER ensure_checkin_checkout_sanity
    BEFORE INSERT
    ON public.checkins
    FOR EACH ROW
    EXECUTE PROCEDURE check_checkin_checkout();
