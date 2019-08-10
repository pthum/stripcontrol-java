<template>
  <div class="service">
    <h1>{{ msg }}</h1>

    <b-container fluid>
        <b-row class="my-1">
            <b-col sm="3"></b-col>
            <b-col sm="6">
                <b-button block variant="outline-primar" @click="callGetColorProfiles()">Update Color Profiles</b-button>
            </b-col>
            <b-col sm="3"></b-col>
        </b-row>
        <b-row class="my-1" >
            <b-col sm="9">
            <b-form-select @input="updateSelect">
                <option v-for="profile in $store.getters.backendProfiles" :key="profile.id" :value="profile">r:{{profile.red}},g:{{profile.green}},b:{{profile.blue}},brightness:{{profile.brightness}}</option>
            </b-form-select>
            </b-col>
            <b-col sm="3">
                <b-button  block variant="primary" @click="toggleEdit()">Edit</b-button>
            </b-col>
        </b-row>
        <b-row v-if="toggleEditFlag">
            <colorprofileform v-bind:selectr="selected" formProfileName="editableColorProfile"/>
        </b-row>
    </b-container>

    <h4>Create new ColorProfile</h4>

    <div>
        <button @click="toggleCreate()">Create new ColorProfile</button>
        <div v-if="toggleCreateFlag">
            <colorprofileform v-bind:selectr="createable"  formProfileName="creatableColorProfile"/>
        </div>
    </div>
  </div>
</template>

<script>
import api from './backend-api'
import colorprofileform from './colorprofile-form'
import EventBus from './eventbus'
export default {
  name: 'service',
  components: {
    colorprofileform
  },
  created () {
    this.callGetColorProfiles()
  },
  data () {
    return {
      msg: 'ColorProfile handling:',
      errors: [],
      selected: {},
      createable: { red: 0, green: 0, blue: 0, brightness: 0 },
      ctest: {},
      toggleEditFlag: false,
      toggleCreateFlag: false
    }
  },
  methods: {
    // Fetches profiles when the component is created.
    callGetColorProfiles () {
      api.getColorProfiles().then(response => {
        this.$store.commit('updateBackendProfiles', response.data)
      })
        .catch(error => {
          this.errors.push(error)
        })
    },
    toggleEdit () {
      this.toggleEditFlag = !this.toggleEditFlag
    },
    toggleCreate () {
      this.toggleCreateFlag = !this.toggleCreateFlag
    },
    updateSelect (e) {
      console.log(e)
      this.$store.commit('updateEditableColorProfile', {type: 'editableColorProfile', object: e})
    }
  },
  mounted () {
    EventBus.$on('CPupdate', this.callGetColorProfiles)
  }
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
