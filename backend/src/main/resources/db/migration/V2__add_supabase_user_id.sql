-- Adds the Supabase Auth user UUID to the users table.
-- Existing rows (if any) will have NULL; new users created through the API will always have this set.
ALTER TABLE users
    ADD COLUMN supabase_user_id VARCHAR(255) UNIQUE;

