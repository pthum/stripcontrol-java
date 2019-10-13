<template>
  <div class="headermenu">
  <b-navbar toggleable="lg" type="dark" variant="dark">
      <b-navbar-brand :to="{path: '/'}" >Home</b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item :to="{path: '/colorprofileservice'}">Color Profiles</b-nav-item>
          <b-nav-item :to="{path: '/ledstripservice'}">LED strips</b-nav-item>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
export default {
  name: 'headermenuservice'
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
font-weight: normal;
}
ul {
list-style-type: none;
padding: 0;
}
li {
display: inline-block;
margin: 0 10px;
}
a {
color: #42b983;
}
</style>
