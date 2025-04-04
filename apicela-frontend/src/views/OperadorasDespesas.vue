<template>
  <div class="container mt-5">
    <h2 class="title">📊 Maiores Despesas</h2>

    <div class="button-group">
      <button
        class="btn"
        :class="selecionado === 'ano' ? 'btn-danger selected' : 'btn-outline-danger'"
        @click="buscarDespesasAno"
      >
        Despesas do Ano
      </button>
      <button
        class="btn"
        :class="selecionado === 'trimestre' ? 'btn-warning selected' : 'btn-outline-warning'"
        @click="buscarDespesasTrimestre"
      >
        Despesas do Trimestre
      </button>
    </div>

    <div v-if="carregandoDespesas" class="text-center mt-3">
      <div class="spinner-border text-danger" role="status">
        <span class="visually-hidden">Carregando despesas...</span>
      </div>
    </div>

    <table v-else-if="despesas.length" class="styled-table">
      <thead>
        <tr>
          <th>Razão Social</th>
          <th>Nome Fantasia</th>
          <th>Total Despesa (R$)</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="despesa in despesas" :key="despesa.razao_social">
          <td>{{ despesa.razao_social }}</td>
          <td>{{ despesa.nome_fantasia }}</td>
          <td>{{ despesa.total_despesa.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) }}</td>
        </tr>
      </tbody>
    </table>

    <p v-else class="no-data">Nenhuma despesa encontrada.</p>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import axios from 'axios';

export default {
  name: 'OperadorasDespesas',
  setup() {
    const despesas = ref([]);
    const selecionado = ref('ano');
    const carregandoDespesas = ref(false);

    const buscarDespesasAno = async () => {
      carregandoDespesas.value = true;
      try {
        const response = await axios.get('http://localhost:8000/operadoras/despesas/ano');
        despesas.value = response.data;
        selecionado.value = 'ano';
      } catch (error) {
        console.error('Erro ao buscar despesas do ano:', error);
      } finally {
        carregandoDespesas.value = false;
      }
    };

    const buscarDespesasTrimestre = async () => {
      carregandoDespesas.value = true;
      try {
        const response = await axios.get('http://localhost:8000/operadoras/despesas/trimestre');
        despesas.value = response.data;
        selecionado.value = 'trimestre';
      } catch (error) {
        console.error('Erro ao buscar despesas do trimestre:', error);
      } finally {
        carregandoDespesas.value = false;
      }
    };

    onMounted(buscarDespesasAno);

    return { despesas, buscarDespesasAno, buscarDespesasTrimestre, selecionado, carregandoDespesas };
  }
};
</script>

<style scoped>
.container {
  max-width: 900px;
  margin: auto;
  text-align: center;
}

.title {
  font-size: 28px;
  font-weight: bold;
  color: #333;
  margin-bottom: 20px;
}

.button-group {
  display: flex;
  justify-content: center;
  gap: 15px;
  margin-bottom: 20px;
}

.btn {
  padding: 10px 20px;
  font-size: 16px;
  font-weight: bold;
  border-radius: 8px;
  transition: 0.3s ease;
}

.selected {
  background-color: #89888a !important;
  color: #fff !important;
  border-color: #fff !important;
}

.styled-table {
  width: 100%;
  border-collapse: collapse;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  overflow: hidden;
  margin-top: 20px;
}

.styled-table thead {
  background-color: #dc3545;
  color: white;
}

.styled-table th,
.styled-table td {
  padding: 12px;
  text-align: center;
  border-bottom: 1px solid #ddd;
}

.styled-table tbody tr:nth-child(even) {
  background-color: #f8d7da;
}

.styled-table tbody tr:hover {
  background-color: #f5c6cb;
}

.no-data {
  margin-top: 20px;
  font-size: 16px;
  color: #666;
}
</style>
