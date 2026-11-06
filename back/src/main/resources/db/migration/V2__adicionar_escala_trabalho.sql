-- A coluna permanece anulável para preservar colaboradores antigos sem escala.
ALTER TABLE usuarios
    ADD COLUMN IF NOT EXISTS escala VARCHAR(10);

-- Revise os registros retornados antes de prosseguir com a criação do CHECK.
SELECT id, nome, escala
FROM usuarios
WHERE escala IS NOT NULL
  AND escala NOT IN ('5x2', '4x3', '12x36');

-- Interrompe a aplicação da restrição se houver dados que precisam de revisão manual.
DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM usuarios
        WHERE escala IS NOT NULL
          AND escala NOT IN ('5x2', '4x3', '12x36')
    ) THEN
        RAISE EXCEPTION 'Existem escalas antigas inválidas. Revise os registros listados antes de reaplicar esta migração.';
    END IF;
END
$$;

ALTER TABLE usuarios
    ADD CONSTRAINT usuarios_escala_check
        CHECK (escala IS NULL OR escala IN ('5x2', '4x3', '12x36'));
