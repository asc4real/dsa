-- Ensure records cannot change ownership of the primary tenant by accident.
CREATE OR REPLACE FUNCTION reject_tenant_change() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
 IF OLD.organization_id IS DISTINCT FROM NEW.organization_id THEN
   RAISE EXCEPTION 'organization_id is immutable';
 END IF;
 RETURN NEW;
END $$;
CREATE TRIGGER appointment_immutable_tenant BEFORE UPDATE ON appointment
 FOR EACH ROW EXECUTE FUNCTION reject_tenant_change();
CREATE TRIGGER reservation_immutable_tenant BEFORE UPDATE ON resource_reservation
 FOR EACH ROW EXECUTE FUNCTION reject_tenant_change();
CREATE TRIGGER hold_immutable_tenant BEFORE UPDATE ON booking_hold
 FOR EACH ROW EXECUTE FUNCTION reject_tenant_change();
