CREATE INDEX appointment_org_start_idx ON appointment(organization_id,start_at,id);
CREATE INDEX appointment_org_customer_idx ON appointment(organization_id,customer_user_profile_id,start_at,id);
CREATE INDEX hold_expiration_idx ON booking_hold(expires_at,id) WHERE status='ACTIVE';
CREATE INDEX reservation_resource_idx ON resource_reservation(organization_id,resource_id,reserved_start_at) WHERE state='ACTIVE';
CREATE INDEX membership_user_idx ON organization_membership(user_profile_id,organization_id) WHERE status='ACTIVE';
CREATE INDEX reminder_due_idx ON reminder_instance(due_at) WHERE status='PENDING';
CREATE INDEX idempotency_expiry_idx ON idempotency_record(expires_at);
