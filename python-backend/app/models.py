from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()

class Operadora(db.Model):
    __tablename__ = 'operadoras'
    
    registro_ans = db.Column(db.String(255), primary_key=True)
    cnpj = db.Column(db.String(18), unique=True, nullable=False)
    razao_social = db.Column(db.String(255), nullable=False)
    nome_fantasia = db.Column(db.String(255))
    modalidade = db.Column(db.String(50))
    logradouro = db.Column(db.String(255))
    numero = db.Column(db.String(64))
    complemento = db.Column(db.String(255))
    bairro = db.Column(db.String(100))
    cidade = db.Column(db.String(100))
    uf = db.Column(db.String(2))
    cep = db.Column(db.String(10))
    ddd = db.Column(db.String(3))
    telefone = db.Column(db.String(20))
    fax = db.Column(db.String(20))
    endereco_eletronico = db.Column(db.String(255))
    representante = db.Column(db.String(255))
    cargo_representante = db.Column(db.String(100))
    regiao_de_comercializacao = db.Column(db.String(255))
    data_registro_ans = db.Column(db.Date)


class DemonstracaoContabil(db.Model):
    __tablename__ = 'demonstracoes_contabeis'
    
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    data = db.Column(db.Date, nullable=False)
    reg_ans = db.Column(db.String(255))
    cd_conta_contabil = db.Column(db.String(50), nullable=False)
    descricao = db.Column(db.Text, nullable=False)
    vl_saldo_inicial = db.Column(db.Numeric(18,2), nullable=False)
    vl_saldo_final = db.Column(db.Numeric(18,2), nullable=False)
