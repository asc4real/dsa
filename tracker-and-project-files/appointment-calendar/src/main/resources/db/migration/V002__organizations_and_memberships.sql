CREATE TABLE organization (
 id uuid PRIMARY KEY, name varchar(160) NOT NULL,
 default_zone_id varchar(64) NOT NULL DEFAULT 'UTC',
 status varchar(16) NOT NULL DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE','SUSPENDED')),
 version bigint NOT NULL DEFAULT 1, created_at timestamptz NOT NULL DEFAULT now()
);
CREATE TABLE organization_membership (
 id uuid PRIMARY KEY, organization_id uuid NOT NULL REFERENCES organization(id),
 user_profile_id uuid NOT NULL REFERENCES user_profile(id),
 role varchar(32) NOT NULL CHECK (role IN ('CUSTOMER','STAFF','SCHEDULER','ORGANIZATION_ADMIN')),
 status varchar(16) NOT NULL DEFAULT 'ACTIVE' CHECK(status IN ('ACTIVE','SUSPENDED','REVOKED')),
 version bigint NOT NULL DEFAULT 1,
 UNIQUE(organization_id,user_profile_id), UNIQUE(organization_id,id)
);
CREATE TABLE organization_business_hours (
 id uuid PRIMARY KEY, organization_id uuid NOT NULL REFERENCES organization(id),
 day_of_week smallint NOT NULL CHECK(day_of_week BETWEEN 1 AND 7),
 opens_at time NOT NULL, closes_at time NOT NULL, CHECK(opens_at < closes_at),
 UNIQUE(organization_id,day_of_week,opens_at)
);
