# Usar uma imagem oficial do Python
FROM python:3.11

# Definir o diretório de trabalho no contêiner
WORKDIR /app

# Copiar arquivos para dentro do contêiner
COPY . .

# Instalar dependências
RUN pip install --no-cache-dir -r app/requirements.txt

# Expor a porta da aplicação
EXPOSE 5000

# Comando para rodar a aplicação
CMD ["python", "app.py"]
