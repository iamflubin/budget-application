CREATE TABLE IF NOT EXISTS expense
(
    id         UUID PRIMARY KEY         NOT NULL DEFAULT gen_random_uuid(),
    version    BIGINT                   NOT NULL DEFAULT 0,
    name       VARCHAR(255)             NOT NULL CHECK (btrim(name) <> ''),
    amount     DECIMAL(12, 2)           NOT NULL CHECK (amount > 0),
    date       DATE                     NOT NULL,
    category   VARCHAR(255)             NOT NULL CHECK (category IN ('NEEDS', 'WANT', 'SAVINGS')),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_expense_lower_name_trgm ON expense USING gin (LOWER(name) gin_trgm_ops);

CREATE INDEX IF NOT EXISTS idx_expense_date ON expense (date);

CREATE INDEX IF NOT EXISTS idx_expense_category ON expense (category);

DROP TRIGGER IF EXISTS update_expenseupdated_at ON expense;

CREATE TRIGGER update_expense_updated_at
    BEFORE UPDATE
    ON expense
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();