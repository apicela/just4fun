<template>
    <header class="header">
      <h1 class="title">📡 Sistema de Operadoras</h1>
      <nav class="nav-links">
        <router-link to="/operadoras" class="nav-button operadoras">Ver Operadoras</router-link>
        <router-link to="/despesas" class="nav-button despesas">Ver Despesas</router-link>
      </nav>
    </header>
  </template>
  
  <style scoped>
  .header {
    width: 100vw;
    padding: 20px;
    text-align: center;
    background: linear-gradient(135deg, #1e1e1e, #343a40);
    color: white;
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
    margin: 0;
  }
  
  .title {
    font-size: 28px;
    font-weight: bold;
    letter-spacing: 1px;
    margin-bottom: 10px;
  }
  
  .nav-links {
    display: flex;
    justify-content: center;
    gap: 15px;
    margin-top: 10px;
  }
  
  .nav-button {
    padding: 12px 20px;
    font-size: 16px;
    font-weight: bold;
    border-radius: 8px;
    text-decoration: none;
    color: white;
    transition: all 0.3s ease;
    box-shadow: 0px 3px 5px rgba(0, 0, 0, 0.2);
  }
  
  .operadoras {
    background-color: #007bff;
  }
  
  .operadoras:hover {
    background-color: #0056b3;
    transform: scale(1.05);
  }
  
  .despesas {
    background-color: #dc3545;
  }
  
  .despesas:hover {
    background-color: #a71d2a;
    transform: scale(1.05);
  }
  
  @media (max-width: 600px) {
    .nav-links {
      flex-direction: column;
      gap: 10px;
    }
  }
  </style>
  