CREATE TABLE operadoras (
    registro_ans VARCHAR(255) PRIMARY KEY,
    cnpj VARCHAR(18) UNIQUE NOT NULL,
    razao_social VARCHAR(255) NOT NULL,
    nome_fantasia VARCHAR(255),
    modalidade VARCHAR(50),
    logradouro VARCHAR(255),
    numero VARCHAR(64),
    complemento VARCHAR(255),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    uf CHAR(2),
    cep VARCHAR(10),
    ddd VARCHAR(3),
    telefone VARCHAR(20),
    fax VARCHAR(20),
    endereco_eletronico VARCHAR(255),
    representante VARCHAR(255),
    cargo_representante VARCHAR(100),
    regiao_de_comercializacao VARCHAR(255),
    data_registro_ans DATE
);

CREATE TABLE demonstracoes_contabeis (
    id SERIAL PRIMARY KEY,
    data DATE NOT NULL,
    reg_ans VARCHAR(255),
    cd_conta_contabil VARCHAR(50) NOT NULL,
    descricao TEXT NOT NULL,
    vl_saldo_inicial NUMERIC(18,2) NOT NULL,
    vl_saldo_final NUMERIC(18,2) NOT NULL
);

COPY operadoras FROM '/data_import/Relatorio_cadop.csv' 
DELIMITER ';' 
CSV HEADER 
ENCODING 'UTF8';

CREATE TEMP TABLE temp_demonstracoes_contabeis (
    data DATE,
    reg_ans VARCHAR(255),
    cd_conta_contabil VARCHAR(50),
    descricao TEXT,
    vl_saldo_inicial TEXT,  -- Importando como texto para tratar depois
    vl_saldo_final TEXT     -- Importando como texto para tratar depois
);
-- 2023
COPY temp_demonstracoes_contabeis FROM '/data_import/1T2023.csv' DELIMITER ';' CSV HEADER ENCODING 'UTF8';
COPY temp_demonstracoes_contabeis FROM '/data_import/2T2023.csv' DELIMITER ';' CSV HEADER ENCODING 'UTF8';
COPY temp_demonstracoes_contabeis FROM '/data_import/3T2023.csv' DELIMITER ';' CSV HEADER ENCODING 'UTF8';
COPY temp_demonstracoes_contabeis FROM '/data_import/4T2023.csv' DELIMITER ';' CSV HEADER ENCODING 'UTF8';
-- 2024
COPY temp_demonstracoes_contabeis FROM '/data_import/1T2024.csv' DELIMITER ';' CSV HEADER ENCODING 'UTF8';
COPY temp_demonstracoes_contabeis FROM '/data_import/2T2024.csv' DELIMITER ';' CSV HEADER ENCODING 'UTF8';
COPY temp_demonstracoes_contabeis FROM '/data_import/3T2024.csv' DELIMITER ';' CSV HEADER ENCODING 'UTF8';
COPY temp_demonstracoes_contabeis FROM '/data_import/4T2024.csv' DELIMITER ';' CSV HEADER ENCODING 'UTF8';

INSERT INTO demonstracoes_contabeis (data, reg_ans, cd_conta_contabil, descricao, vl_saldo_inicial, vl_saldo_final)
SELECT 
    data,
    reg_ans,
    cd_conta_contabil,
    descricao,
    REPLACE(vl_saldo_inicial, ',', '.')::NUMERIC,
    REPLACE(vl_saldo_final, ',', '.')::NUMERIC
FROM temp_demonstracoes_contabeis;
