-- users
INSERT INTO users (name, lastname, email, cpf, birth_date, password, is_active)
VALUES ('Ana', 'Silva', 'as@email.com', '12345678901', '1990-05-15', '123', TRUE);

INSERT INTO users (name, lastname, email, cpf, birth_date, password, is_active)
VALUES ('João', 'Pereira', 'jo@email.com', '09876543210', '1985-08-25', 'teste', TRUE);

-- persons
INSERT INTO persons (name, lastname, email, cpf, birth_date, user_id, is_active)
VALUES ('Carlos', 'Mendes', 'cm@email.com', '56789012345', '1988-03-12', 5, TRUE);

INSERT INTO persons (name, lastname, email, cpf, birth_date, user_id, is_active)
VALUES ('Juliana', 'Costa', 'jc@email.com', '56729012347', '1995-09-30', null, TRUE);
