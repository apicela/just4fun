import { createRouter, createWebHistory } from 'vue-router';
import Operadoras from '../views/OperadorasView.vue';
import Despesas from '../views/OperadorasDespesas.vue';

console.log('Router carregado'); // Adicione este log para testar

const routes = [
  { path: '/', redirect: '/operadoras' },
  { path: '/operadoras', component: Operadoras },
  { path: '/despesas', component: Despesas }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
