ALTER TABLE professional
ADD COLUMN active boolean DEFAULT TRUE;

UPDATE professional
SET
    active = TRUE;

ALTER TABLE professional
ALTER COLUMN active
SET
    NOT NULL;


ALTER TABLE contact
ADD COLUMN active boolean DEFAULT TRUE;

UPDATE contact
SET
    active = TRUE;

ALTER TABLE contact
ALTER COLUMN active
SET
    NOT NULL;