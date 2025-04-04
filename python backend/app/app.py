import os
from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from routes import health, operadoras
from models import db

app = Flask(__name__)

# Usar a variável de ambiente corretamente
app.config["SQLALCHEMY_DATABASE_URI"] = os.getenv("DATABASE_URL")
app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = False

db.init_app(app)

app.register_blueprint(health)
app.register_blueprint(operadoras)

if __name__ == '__main__':
    with app.app_context():
        db.create_all()  # Criar tabelas se não existirem
    app.run(host='0.0.0.0', port=8000, debug=True)
