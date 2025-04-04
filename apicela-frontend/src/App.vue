<script setup>
import AppHeader from './components/AppHeader.vue';
</script>

<template>
  <AppHeader />
  <main class="container mt-4">
    <RouterView />
  </main>
</template>

<style scoped>
html, body {
  height: 100%;
  margin: 0;
  overflow: hidden;
}

#app {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

main {
  flex: 1;
  overflow-y: auto; /* Permite rolagem no conteúdo */
  padding: 20px;
}
</style>
