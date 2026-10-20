CREATE TABLE location (
 id uuid PRIMARY KEY, organization_id uuid NOT NULL REFERENCES organization(id),
 name varchar(160) NOT NULL, zone_id varchar(64) NOT NULL,
 active boolean NOT NULL DEFAULT true, version bigint NOT NULL DEFAULT 1,
 UNIQUE(organization_id,id)
);
CREATE TABLE staff_member (
 id uuid PRIMARY KEY, organization_id uuid NOT NULL REFERENCES organization(id),
 display_name varchar(160) NOT NULL, user_profile_id uuid REFERENCES user_profile(id),
 active boolean NOT NULL DEFAULT true, version bigint NOT NULL DEFAULT 1,
 UNIQUE(organization_id,id)
);
CREATE TABLE calendar_resource (
 id uuid PRIMARY KEY, organization_id uuid NOT NULL REFERENCES organization(id),
 name varchar(160) NOT NULL,
 type varchar(32) NOT NULL CHECK(type IN ('STAFF_MEMBER','ROOM','CHAIR','DEVICE','VEHICLE','EQUIPMENT','OTHER')),
 location_id uuid NOT NULL, staff_id uuid,
 active boolean NOT NULL DEFAULT true, version bigint NOT NULL DEFAULT 1,
 UNIQUE(organization_id,id),
 FOREIGN KEY(organization_id,location_id) REFERENCES location(organization_id,id),
 FOREIGN KEY(organization_id,staff_id) REFERENCES staff_member(organization_id,id)
);
CREATE UNIQUE INDEX only_one_resource_per_staff ON calendar_resource(staff_id) WHERE staff_id IS NOT NULL;
CREATE TABLE location_opening_hours (
 id uuid PRIMARY KEY, organization_id uuid NOT NULL, location_id uuid NOT NULL,
 day_of_week smallint NOT NULL CHECK(day_of_week BETWEEN 1 AND 7),
 opens_at time NOT NULL, closes_at time NOT NULL, CHECK(opens_at < closes_at),
 FOREIGN KEY(organization_id,location_id) REFERENCES location(organization_id,id)
);
