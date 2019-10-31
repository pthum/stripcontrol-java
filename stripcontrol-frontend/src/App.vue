<template>
  <div id="home">
      <headermenuservice/>
    <div id="app">
      <router-view/>
    </div>
  </div>
</template>

<script>
import headermenuservice from './components/HeaderMenu'
export default {
  name: 'App',
  components: {
    headermenuservice
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 10px;
}
</style>
