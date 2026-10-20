CREATE TABLE outbox_event (
 id uuid PRIMARY KEY, organization_id uuid NOT NULL REFERENCES organization(id),
 event_type varchar(100) NOT NULL, aggregate_id uuid NOT NULL,
 payload jsonb NOT NULL, status varchar(16) NOT NULL DEFAULT 'PENDING'
  CHECK(status IN ('PENDING','CLAIMED','SENT','DEAD')),
 attempts int NOT NULL DEFAULT 0, next_attempt_at timestamptz NOT NULL DEFAULT now(),
 claimed_until timestamptz, created_at timestamptz NOT NULL DEFAULT now(),
 last_error varchar(512)
);
CREATE INDEX outbox_due_idx ON outbox_event(status,next_attempt_at) WHERE status IN ('PENDING','CLAIMED');
