CREATE TABLE resource_reservation (
 id uuid PRIMARY KEY, organization_id uuid NOT NULL REFERENCES organization(id),
 resource_id uuid NOT NULL, hold_id uuid, appointment_id uuid,
 reserved_start_at timestamptz NOT NULL, reserved_end_at timestamptz NOT NULL,
 state varchar(16) NOT NULL DEFAULT 'ACTIVE' CHECK(state IN ('ACTIVE','RELEASED')),
 created_at timestamptz NOT NULL DEFAULT now(),
 CHECK(reserved_start_at<reserved_end_at),
 CHECK((hold_id IS NOT NULL) <> (appointment_id IS NOT NULL)),
 FOREIGN KEY(organization_id,resource_id) REFERENCES calendar_resource(organization_id,id),
 FOREIGN KEY(organization_id,hold_id) REFERENCES booking_hold(organization_id,id),
 FOREIGN KEY(organization_id,appointment_id) REFERENCES appointment(organization_id,id)
);
ALTER TABLE resource_reservation ADD CONSTRAINT prevent_double_booking
 EXCLUDE USING gist (
   organization_id WITH =, resource_id WITH =,
   tstzrange(reserved_start_at,reserved_end_at,'[)') WITH &&
 ) WHERE (state='ACTIVE');
CREATE INDEX active_hold_reservation_idx ON resource_reservation(hold_id) WHERE state='ACTIVE';
CREATE INDEX active_appointment_reservation_idx ON resource_reservation(appointment_id) WHERE state='ACTIVE';
