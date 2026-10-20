CREATE TABLE reminder_definition (
 id uuid PRIMARY KEY, organization_id uuid NOT NULL REFERENCES organization(id),
 name varchar(160) NOT NULL, lead_minutes int NOT NULL CHECK(lead_minutes BETWEEN 0 AND 525600),
 channel varchar(20) NOT NULL DEFAULT 'EMAIL', active boolean NOT NULL DEFAULT true,
 version bigint NOT NULL DEFAULT 1, UNIQUE(organization_id,id)
);
CREATE TABLE reminder_instance (
 id uuid PRIMARY KEY, organization_id uuid NOT NULL, appointment_id uuid NOT NULL,
 definition_id uuid NOT NULL, due_at timestamptz NOT NULL,
 status varchar(16) NOT NULL DEFAULT 'PENDING', attempts int NOT NULL DEFAULT 0,
 claimed_until timestamptz,
 FOREIGN KEY(organization_id,appointment_id) REFERENCES appointment(organization_id,id),
 FOREIGN KEY(organization_id,definition_id) REFERENCES reminder_definition(organization_id,id),
 UNIQUE(appointment_id,definition_id)
);
CREATE TABLE notification (
 id uuid PRIMARY KEY, organization_id uuid NOT NULL, appointment_id uuid NOT NULL,
 kind varchar(40) NOT NULL, channel varchar(20) NOT NULL,
 status varchar(16) NOT NULL DEFAULT 'PENDING', attempts int NOT NULL DEFAULT 0, next_attempt_at timestamptz NOT NULL DEFAULT now(), claimed_until timestamptz, created_at timestamptz NOT NULL DEFAULT now(),
 FOREIGN KEY(organization_id,appointment_id) REFERENCES appointment(organization_id,id)
);
CREATE TABLE notification_delivery (
 id uuid PRIMARY KEY, notification_id uuid NOT NULL REFERENCES notification(id),
 provider varchar(40) NOT NULL, attempts int NOT NULL DEFAULT 0,
 status varchar(16) NOT NULL DEFAULT 'PENDING', last_error varchar(512),
 created_at timestamptz NOT NULL DEFAULT now()
);
