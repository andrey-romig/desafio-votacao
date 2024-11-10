CREATE TABLE associado (
                           id SERIAL PRIMARY KEY,
                           nome VARCHAR(100) NOT NULL,
                           cpf VARCHAR(11) NOT NULL UNIQUE
);
CREATE TABLE pauta (
                       id SERIAL PRIMARY KEY,
                       titulo VARCHAR(255) NOT NULL,
                       descricao TEXT NOT NULL
);

CREATE TABLE pauta_sessao (
                              id SERIAL PRIMARY KEY,
                              pauta_id INT NOT NULL,
                              data_hora_inicio TIMESTAMP NOT NULL,
                              data_hora_fim TIMESTAMP NOT NULL,
                              status VARCHAR(20) NOT NULL DEFAULT 'FECHADO',
                              FOREIGN KEY (pauta_id) REFERENCES pauta (id) ON DELETE CASCADE
);

CREATE TABLE voto_sessao (
                             id SERIAL PRIMARY KEY,
                             voto VARCHAR(10) NOT NULL,
                             sessao_id INT NOT NULL,
                             associado_id INT NOT NULL,
                             FOREIGN KEY (sessao_id) REFERENCES pauta_sessao (id) ON DELETE CASCADE,
                             FOREIGN KEY (associado_id) REFERENCES associado (id) ON DELETE CASCADE
);

INSERT INTO associado (nome, cpf) VALUES
                                      ('John Doe', '12345678901'),
                                      ('Jane Smith', '23456789012'),
                                      ('Bob Johnson', '34567890123'),
                                      ('Alice Brown', '45678901234');

INSERT INTO pauta (titulo, descricao) VALUES
                                          ('Title 1', 'Description for Pauta 1'),
                                          ('Title 2', 'Description for Pauta 2'),
                                          ('Title 3', 'Description for Pauta 3');

INSERT INTO pauta_sessao (pauta_id, data_hora_inicio, data_hora_fim, status) VALUES
                                                                                 (1, NOW(), NOW() + INTERVAL '1 hour', 'ABERTO'),
                                                                                 (2, NOW() - INTERVAL '2 hours', NOW() - INTERVAL '1 hour', 'FECHADO'),
                                                                                 (3, NOW() - INTERVAL '3 hours', NOW() - INTERVAL '2 hours', 'FECHADO');

INSERT INTO voto_sessao (voto, sessao_id, associado_id) VALUES
                                                            ('SIM', 1, 1),
                                                            ('NAO', 1, 2),
                                                            ('SIM', 2, 3),
                                                            ('NAO', 2, 4),
                                                            ('SIM', 3, 1),
                                                            ('NAO', 3, 2);
