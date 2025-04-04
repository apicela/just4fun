from flask import Blueprint, jsonify, request
from models import db, Operadora
from sqlalchemy import text
from datetime import date, timedelta
from models import db

                    ########################## operadoras ###########################
operadoras = Blueprint('operadoras', __name__)

@operadoras.route('/operadoras', methods=['GET'])
def get_all_operadoras():
    query_get_all = """
    SELECT *
    FROM operadoras
    LIMIT 10;
    """

    result = db.session.execute(text(query_get_all)).fetchall()

    data = [
        dict(row._mapping) for row in result
    ]

    return jsonify(data), 200

@operadoras.route('/operadoras/despesas/ano', methods=['GET'])
def maiores_despesas_ano():
    query_ano = """
    SELECT 
        o.razao_social,
        o.nome_fantasia,
        SUM(dc.vl_saldo_final - dc.vl_saldo_inicial) AS total_despesa
    FROM demonstracoes_contabeis dc
    JOIN operadoras o ON dc.reg_ans = o.registro_ans
    WHERE 
        dc.descricao ILIKE :descricao
        AND dc.data >= :data_inicio
    GROUP BY o.razao_social, o.nome_fantasia
    ORDER BY total_despesa DESC
    LIMIT 10;
    """

    descricao_filtro = '%EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS  DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR%'
    data_inicio = date.today() - timedelta(days=365)

    result = db.session.execute(
        text(query_ano),
        {"descricao": descricao_filtro, "data_inicio": data_inicio}
    ).fetchall()

    data = [
        {
            "razao_social": row[0],
            "nome_fantasia": row[1],
            "total_despesa": float(row[2])
        } for row in result
    ]

    return jsonify(data), 200


@operadoras.route('/operadoras/despesas/trimestre', methods=['GET'])
def maiores_despesas_trimestre():
    query_trimestre = """
    SELECT 
        o.razao_social,
        o.nome_fantasia,
        SUM(dc.vl_saldo_final - dc.vl_saldo_inicial) AS total_despesa
    FROM demonstracoes_contabeis dc
    JOIN operadoras o ON dc.reg_ans = o.registro_ans
    WHERE 
        dc.descricao ILIKE :descricao
        AND dc.data >= :data_inicio
    GROUP BY o.razao_social, o.nome_fantasia
    ORDER BY total_despesa DESC
    LIMIT 10;
    """

    descricao_filtro = '%EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS  DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR%'
    data_inicio = date.today() - timedelta(days=90)

    result = db.session.execute(
        text(query_trimestre),
        {"descricao": descricao_filtro, "data_inicio": data_inicio}
    ).fetchall()

    data = [
        {
            "razao_social": row[0],
            "nome_fantasia": row[1],
            "total_despesa": float(row[2])
        } for row in result
    ]

    return jsonify(data), 200

@operadoras.route('/operadoras/busca', methods=['GET'])
def buscar_operadoras():
    termo = request.args.get('termo', '')  # Pega o termo da URL
    termo = f"%{termo}%"  # Adiciona '%' para busca parcial no banco

    query_busca = """
    SELECT *
    FROM operadoras
    WHERE razao_social ILIKE :termo OR nome_fantasia ILIKE :termo
    LIMIT 200;
    """

    result = db.session.execute(
        text(query_busca), {"termo": termo}
    ).fetchall()

    data = [dict(row._mapping) for row in result]

    return jsonify(data), 200

                    ###############################  demonstracoes ###########################
demonstracoes = Blueprint('demonstracoes', __name__)

@demonstracoes.route('/demonstracoes', methods=['GET'])
def get_all_demonstracoes():
    query_get_all = """
    SELECT *
    FROM demonstracoes_contabeis
    WHERE descricao ILIKE  '%EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS  DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR%'
    LIMIT 200;
    """

    result = db.session.execute(text(query_get_all)).fetchall()

    data = [
        dict(row._mapping) for row in result
    ]

    return jsonify(data), 200

                    ########################### health ###########################

health = Blueprint('health', __name__)

@health.route('/health', methods=['GET'])
def ping():
    return jsonify ('server is online :)'), 200

