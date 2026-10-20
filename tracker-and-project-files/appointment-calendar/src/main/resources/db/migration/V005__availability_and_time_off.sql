CREATE TABLE availability_rule (
 id uuid PRIMARY KEY, organization_id uuid NOT NULL, resource_id uuid NOT NULL,
 day_of_week smallint NOT NULL CHECK(day_of_week BETWEEN 1 AND 7),
 starts_at time NOT NULL, ends_at time NOT NULL,
 active boolean NOT NULL DEFAULT true, version bigint NOT NULL DEFAULT 1,
 CHECK(starts_at < ends_at), UNIQUE(organization_id,id),
 FOREIGN KEY(organization_id,resource_id) REFERENCES calendar_resource(organization_id,id)
);
CREATE TABLE availability_exception (
 id uuid PRIMARY KEY, organization_id uuid NOT NULL, resource_id uuid NOT NULL,
 start_at timestamptz NOT NULL, end_at timestamptz NOT NULL,
 kind varchar(20) NOT NULL CHECK(kind IN ('UNAVAILABLE','AVAILABLE')),
 version bigint NOT NULL DEFAULT 1, CHECK(start_at<end_at),
 UNIQUE(organization_id,id),
 FOREIGN KEY(organization_id,resource_id) REFERENCES calendar_resource(organization_id,id)
);
CREATE TABLE staff_time_off (
 id uuid PRIMARY KEY, organization_id uuid NOT NULL, staff_id uuid NOT NULL,
 start_at timestamptz NOT NULL, end_at timestamptz NOT NULL,
 status varchar(16) NOT NULL DEFAULT 'ACTIVE' CHECK(status IN ('ACTIVE','CANCELLED')),
 version bigint NOT NULL DEFAULT 1, CHECK(start_at<end_at),
 UNIQUE(organization_id,id),
 FOREIGN KEY(organization_id,staff_id) REFERENCES staff_member(organization_id,id)
);
