CREATE TABLE booking_hold (
 id uuid PRIMARY KEY, organization_id uuid NOT NULL REFERENCES organization(id),
 service_id uuid NOT NULL, location_id uuid NOT NULL,
 customer_user_profile_id uuid NOT NULL REFERENCES user_profile(id),
 staff_id uuid, start_at timestamptz NOT NULL, end_at timestamptz NOT NULL,
 service_name_snapshot varchar(160) NOT NULL, duration_minutes_snapshot int NOT NULL,
 buffer_before_minutes_snapshot int NOT NULL DEFAULT 0, buffer_after_minutes_snapshot int NOT NULL DEFAULT 0,
 cancellation_deadline_minutes_snapshot int NOT NULL DEFAULT 0, reschedule_deadline_minutes_snapshot int NOT NULL DEFAULT 0,
 status varchar(16) NOT NULL DEFAULT 'ACTIVE'
   CHECK(status IN ('ACTIVE','CONFIRMED','EXPIRED','CANCELLED')),
 expires_at timestamptz NOT NULL, appointment_id uuid,
 version bigint NOT NULL DEFAULT 1, created_at timestamptz NOT NULL DEFAULT now(),
 CHECK(start_at<end_at), UNIQUE(organization_id,id),
 FOREIGN KEY(organization_id,service_id) REFERENCES bookable_service(organization_id,id),
 FOREIGN KEY(organization_id,location_id) REFERENCES location(organization_id,id),
 FOREIGN KEY(organization_id,staff_id) REFERENCES staff_member(organization_id,id)
);
CREATE TABLE appointment (
 id uuid PRIMARY KEY, organization_id uuid NOT NULL REFERENCES organization(id),
 service_id uuid NOT NULL, location_id uuid NOT NULL,
 customer_user_profile_id uuid NOT NULL REFERENCES user_profile(id), staff_id uuid,
 start_at timestamptz NOT NULL, end_at timestamptz NOT NULL,
 status varchar(20) NOT NULL DEFAULT 'CONFIRMED'
   CHECK(status IN ('CONFIRMED','CHECKED_IN','IN_PROGRESS','COMPLETED','CANCELLED','NO_SHOW')),
 service_name_snapshot varchar(160) NOT NULL, duration_minutes_snapshot int NOT NULL,
 buffer_before_minutes_snapshot int NOT NULL DEFAULT 0, buffer_after_minutes_snapshot int NOT NULL DEFAULT 0,
 cancellation_deadline_minutes_snapshot int NOT NULL DEFAULT 0, reschedule_deadline_minutes_snapshot int NOT NULL DEFAULT 0,
 version bigint NOT NULL DEFAULT 1,
 created_at timestamptz NOT NULL DEFAULT now(), updated_at timestamptz NOT NULL DEFAULT now(),
 CHECK(start_at<end_at), UNIQUE(organization_id,id),
 FOREIGN KEY(organization_id,service_id) REFERENCES bookable_service(organization_id,id),
 FOREIGN KEY(organization_id,location_id) REFERENCES location(organization_id,id),
 FOREIGN KEY(organization_id,staff_id) REFERENCES staff_member(organization_id,id)
);
ALTER TABLE booking_hold ADD CONSTRAINT booking_hold_appointment_tenant_fk
 FOREIGN KEY(organization_id,appointment_id) REFERENCES appointment(organization_id,id);
CREATE TABLE appointment_participant (
 id uuid PRIMARY KEY, organization_id uuid NOT NULL, appointment_id uuid NOT NULL,
 user_profile_id uuid NOT NULL REFERENCES user_profile(id), role varchar(30) NOT NULL,
 FOREIGN KEY(organization_id,appointment_id) REFERENCES appointment(organization_id,id)
);
CREATE TABLE appointment_status_history (
 id uuid PRIMARY KEY, organization_id uuid NOT NULL, appointment_id uuid NOT NULL,
 from_status varchar(20), to_status varchar(20) NOT NULL, reason varchar(512),
 actor_user_profile_id uuid REFERENCES user_profile(id), changed_at timestamptz NOT NULL DEFAULT now(),
 FOREIGN KEY(organization_id,appointment_id) REFERENCES appointment(organization_id,id)
);
