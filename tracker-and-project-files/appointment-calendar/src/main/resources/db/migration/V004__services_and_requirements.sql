CREATE TABLE bookable_service (
 id uuid PRIMARY KEY, organization_id uuid NOT NULL REFERENCES organization(id),
 name varchar(160) NOT NULL, description varchar(2048) NOT NULL DEFAULT '',
 duration_minutes int NOT NULL CHECK(duration_minutes BETWEEN 1 AND 720),
 buffer_before_minutes int NOT NULL DEFAULT 0 CHECK(buffer_before_minutes BETWEEN 0 AND 180),
 buffer_after_minutes int NOT NULL DEFAULT 0 CHECK(buffer_after_minutes BETWEEN 0 AND 180),
 min_booking_notice_minutes int NOT NULL DEFAULT 0,
 max_booking_horizon_days int NOT NULL DEFAULT 365 CHECK(max_booking_horizon_days BETWEEN 1 AND 365),
 cancellation_deadline_minutes int NOT NULL DEFAULT 0,
 reschedule_deadline_minutes int NOT NULL DEFAULT 0,
 capacity int NOT NULL DEFAULT 1 CHECK(capacity=1),
 active boolean NOT NULL DEFAULT true, version bigint NOT NULL DEFAULT 1,
 UNIQUE(organization_id,id)
);
CREATE TABLE service_location (
 organization_id uuid NOT NULL, service_id uuid NOT NULL, location_id uuid NOT NULL,
 PRIMARY KEY(organization_id,service_id,location_id),
 FOREIGN KEY(organization_id,service_id) REFERENCES bookable_service(organization_id,id),
 FOREIGN KEY(organization_id,location_id) REFERENCES location(organization_id,id)
);
CREATE TABLE service_resource_requirement (
 id uuid PRIMARY KEY, organization_id uuid NOT NULL, service_id uuid NOT NULL,
 requirement_key varchar(100) NOT NULL, resource_type varchar(32) NOT NULL,
 quantity int NOT NULL CHECK (quantity BETWEEN 1 AND 8),
 UNIQUE(organization_id,service_id,requirement_key),
 FOREIGN KEY(organization_id,service_id) REFERENCES bookable_service(organization_id,id)
);
CREATE TABLE service_requirement_eligible_resource (
 organization_id uuid NOT NULL, requirement_id uuid NOT NULL, resource_id uuid NOT NULL,
 PRIMARY KEY(requirement_id,resource_id),
 FOREIGN KEY(organization_id,resource_id) REFERENCES calendar_resource(organization_id,id)
);
CREATE UNIQUE INDEX requirement_tenant_idx ON service_resource_requirement(organization_id,id);
ALTER TABLE service_requirement_eligible_resource ADD CONSTRAINT requirement_tenant_fk
 FOREIGN KEY(organization_id,requirement_id) REFERENCES service_resource_requirement(organization_id,id);
