<template>
  <div class="container">
    <h2 class="title">🔎 Buscar Operadoras</h2>

    <div class="search-box">
      <input 
        v-model="termoBusca" 
        type="text" 
        class="search-input" 
        placeholder="Digite o nome da operadora..." 
        @keyup.enter="buscarOperadoras"
      />
      <button class="search-button" @click="buscarOperadoras">Buscar</button>
    </div>

    <div v-if="carregando" class="loading">
      <div class="spinner"></div>
      <p>Carregando...</p>
    </div>

    <table v-if="operadoras.length" class="styled-table">
      <thead>
        <tr>
          <th>Razão Social</th>
          <th>Nome Fantasia</th>
          <th>Registro ANS</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="op in operadoras" :key="op.registro_ans">
          <td>{{ op.razao_social }}</td>
          <td>{{ op.nome_fantasia }}</td>
          <td>{{ op.registro_ans }}</td>
        </tr>
      </tbody>
    </table>

    <p v-if="!operadoras.length && !carregando" class="no-data">Nenhuma operadora encontrada.</p>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import axios from 'axios';

export default {
  name: 'OperadorasView',
  setup() {
    const termoBusca = ref('');
    const operadoras = ref([]);
    const carregando = ref(false);

    const buscarOperadoras = async () => {
      carregando.value = true;
      try {
        const response = await axios.get('http://localhost:8000/operadoras/busca', {
          params: termoBusca.value.trim() ? { termo: termoBusca.value } : {}
        });
        operadoras.value = response.data;
      } catch (error) {
        console.error('Erro ao buscar operadoras:', error);
      } finally {
        carregando.value = false;
      }
    };

    onMounted(buscarOperadoras);

    return { termoBusca, operadoras, buscarOperadoras, carregando };
  }
};
</script>

<style scoped>

.container {
  width: 100vw;
  height: 100vh;
  padding: 20px;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  text-align: center;
}

.title {
  font-size: 28px;
  font-weight: bold;
  color: #333;
  margin-bottom: 20px;
}

.search-box {
  display: flex;
  width: 80%;
  max-width: 600px;
  gap: 10px;
  margin-bottom: 20px;
}

.search-input {
  flex: 1;
  padding: 12px;
  border: 2px solid #007bff;
  border-radius: 8px;
  font-size: 16px;
  transition: 0.3s ease;
}

.search-input:focus {
  outline: none;
  border-color: #0056b3;
  box-shadow: 0px 0px 8px rgba(0, 123, 255, 0.5);
}

.search-button {
  padding: 12px 20px;
  font-size: 16px;
  font-weight: bold;
  border: none;
  border-radius: 8px;
  background-color: #007bff;
  color: white;
  cursor: pointer;
  transition: 0.3s ease;
}

.search-button:hover {
  background-color: #0056b3;
}

.loading {
  margin-top: 20px;
  font-size: 16px;
  color: #007bff;
}

.spinner {
  border: 4px solid rgba(0, 123, 255, 0.3);
  border-left-color: #007bff;
  border-radius: 50%;
  width: 30px;
  height: 30px;
  animation: spin 1s linear infinite;
  display: inline-block;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

.styled-table {
  width: 90%;
  max-width: 1200px;
  margin-top: 20px;
  border-collapse: collapse;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  overflow: hidden;
}

.styled-table thead {
  background-color: #343a40;
  color: white;
}

.styled-table th, .styled-table td {
  padding: 12px;
  text-align: center;
  border-bottom: 1px solid #ddd;
}

.styled-table tbody tr:nth-child(even) {
  background-color: #f8f9fa;
}

.styled-table tbody tr:hover {
  background-color: #e9ecef;
}

.no-data {
  margin-top: 20px;
  font-size: 16px;
  color: #666;
}
</style>
