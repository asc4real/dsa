CREATE TABLE idempotency_record (
 id uuid PRIMARY KEY, organization_id uuid NOT NULL REFERENCES organization(id),
 principal_id uuid NOT NULL REFERENCES user_profile(id), operation varchar(120) NOT NULL,
 idempotency_key varchar(128) NOT NULL, request_hash varchar(64) NOT NULL,
 state varchar(16) NOT NULL CHECK(state IN ('PROCESSING','COMPLETED')),
 response_status int, response_body jsonb,
 created_at timestamptz NOT NULL DEFAULT now(), expires_at timestamptz NOT NULL,
 UNIQUE(organization_id,principal_id,operation,idempotency_key)
);
CREATE TABLE audit_event (
 id uuid PRIMARY KEY, organization_id uuid NOT NULL REFERENCES organization(id),
 actor_user_profile_id uuid, event_type varchar(80) NOT NULL,
 target_type varchar(80) NOT NULL, target_id uuid,
 details jsonb NOT NULL DEFAULT '{}'::jsonb,
 occurred_at timestamptz NOT NULL DEFAULT now()
);
CREATE INDEX audit_tenant_recent_idx ON audit_event(organization_id,occurred_at DESC,id DESC);
