CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TABLE IF NOT EXISTS income
(
    id         UUID PRIMARY KEY         NOT NULL DEFAULT gen_random_uuid(),
    version    BIGINT                   NOT NULL DEFAULT 0,
    name       VARCHAR(255)             NOT NULL CHECK (btrim(name) <> ''),
    amount     DECIMAL(12, 2)           NOT NULL CHECK (amount > 0),
    date       DATE                     NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_income_lower_name_trgm ON income USING gin (LOWER(name) gin_trgm_ops);

CREATE INDEX IF NOT EXISTS idx_income_date ON income (date);

DROP TRIGGER IF EXISTS update_incomeupdated_at ON income;

CREATE TRIGGER update_income_updated_at
    BEFORE UPDATE
    ON income
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();