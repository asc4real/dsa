CREATE EXTENSION IF NOT EXISTS btree_gist;
CREATE TABLE user_profile (
 id uuid PRIMARY KEY, issuer text NOT NULL, subject text NOT NULL,
 display_name text, email text, created_at timestamptz NOT NULL DEFAULT now(),
 UNIQUE(issuer, subject)
);
