import os
from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from routes import health, operadoras, demonstracoes
from models import db
from flask_cors import CORS

app = Flask(__name__)
CORS(app)

app.config["SQLALCHEMY_DATABASE_URI"] = os.getenv("DATABASE_URL")
app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = False

db.init_app(app)

app.register_blueprint(health)
app.register_blueprint(operadoras)
app.register_blueprint(demonstracoes)

if __name__ == '__main__':
    with app.app_context():
        db.create_all()  
    app.run(host='0.0.0.0', port=8000, debug=True)
