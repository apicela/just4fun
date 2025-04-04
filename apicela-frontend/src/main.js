import { createApp } from 'vue';
import App from './App.vue';
import router from './router'; // Certifique-se de que está importando o roteador

const app = createApp(App);
app.use(router);
app.mount('#app');
