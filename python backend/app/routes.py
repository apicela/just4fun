from flask import Blueprint, jsonify
from models import db, Operadora

operadoras = Blueprint('operadoras', __name__)

@operadoras.route('/operadoras', methods=['GET'])
def get_operadoras():
    operadoras_list = Operadora.query.limit(20).all()
    result = [
        {
            "registro_ans": op.registro_ans,
            "cnpj": op.cnpj,
            "razao_social": op.razao_social,
            "nome_fantasia": op.nome_fantasia,
            "modalidade": op.modalidade,
            "cidade": op.cidade,
            "uf": op.uf
        } for op in operadoras_list
    ]
    return jsonify(result)

health = Blueprint('health', __name__)

@health.route('/health', methods=['GET'])
def ping():
    return 'hello'

