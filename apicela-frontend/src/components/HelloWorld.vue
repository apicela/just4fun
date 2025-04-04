<template>
  <header class="bg-dark text-white py-3 text-center">
    <h1>📡 Sistema de Operadoras</h1>
  </header>
  
  <div class="container mt-4 text-center">
    <h2 class="mb-4">Buscar Operadoras</h2>
    
    <div class="input-group mb-3">
      <input 
        v-model="termoBusca" 
        type="text" 
        class="form-control" 
        placeholder="Digite o nome da operadora..." 
        @keyup.enter="buscarOperadoras"
      />
      <button class="btn btn-primary" @click="buscarOperadoras">Buscar</button>
    </div>
    
    <div v-if="carregando" class="text-center mt-3">
      <div class="spinner-border text-primary" role="status">
        <span class="visually-hidden">Carregando...</span>
      </div>
    </div>
    
    <table v-if="operadoras.length" class="table table-hover mt-3 shadow-sm text-center">
      <thead class="table-dark">
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
    <p v-if="!operadoras.length && !carregando" class="mt-3 text-muted">Nenhuma operadora encontrada.</p>
    
    <div class="mt-5">
      <h4>🔍 Maiores Despesas</h4>
      <button class="btn btn-outline-danger me-2" @click="buscarDespesasAno">Ano</button>
      <button class="btn btn-outline-warning" @click="buscarDespesasTrimestre">Trimestre</button>
    </div>
    
    <table v-if="despesas.length" class="table table-bordered mt-3 text-center">
      <thead class="table-danger">
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
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import axios from 'axios';

export default {
  name: 'OperadorasBusca',
  setup() {
    const termoBusca = ref('');
    const operadoras = ref([]);
    const despesas = ref([]);
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
    
    const buscarDespesasAno = async () => {
      try {
        const response = await axios.get('http://localhost:8000/operadoras/despesas/ano');
        despesas.value = response.data;
      } catch (error) {
        console.error('Erro ao buscar despesas do ano:', error);
      }
    };
    
    const buscarDespesasTrimestre = async () => {
      try {
        const response = await axios.get('http://localhost:8000/operadoras/despesas/trimestre');
        despesas.value = response.data;
      } catch (error) {
        console.error('Erro ao buscar despesas do trimestre:', error);
      }
    };
    
    onMounted(buscarOperadoras);
    
    return { termoBusca, operadoras, buscarOperadoras, carregando, despesas, buscarDespesasAno, buscarDespesasTrimestre };
  }
};
</script>

<style scoped>
.container {
  max-width: 900px;
}
.table {
  border-radius: 10px;
  overflow: hidden;
}
header {
  background-color: #343a40;
  color: white;
  padding: 15px 0;
}
</style>