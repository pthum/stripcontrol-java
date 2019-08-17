<template>
  <div class="service">
    <h1>ColorProfiles</h1>
    <b-container fluid>
      <b-row class="my-1" >
        <b-col sm="3">
          Select Profile
        </b-col>
        <b-col sm="4">
          <b-button-group >
            <b-form-select @input="updateSelect">
              <option v-for="profile in $store.getters.backendProfiles" :key="profile.id" :value="profile">r:{{profile.red}},g:{{profile.green}},b:{{profile.blue}},brightness:{{profile.brightness}}</option>
            </b-form-select>
            <b-button variant="outline-primary" @click="callGetColorProfiles()"><font-awesome-icon icon="sync" /></b-button>
            <b-button variant="primary" @click="toggleEdit()">Edit</b-button>
          </b-button-group>
        </b-col>
        <b-col>
        </b-col>
      </b-row>
      <b-row v-if="toggleEditFlag">
        <b-col>
        <colorprofileform v-bind:selectr="selected" formProfileName="editableColorProfile"/>
        </b-col>
      </b-row>
    </b-container>
    <p></p>
    <h4>Create new ColorProfile</h4>
    <b-container fluid>
      <b-row class="my-1" >
        <b-col sm="12">
            <b-button block variant="primary" @click="toggleCreate()">Create new ColorProfile</b-button>
        </b-col>
      </b-row>
      <b-row v-if="toggleCreateFlag">
        <b-col>
          <colorprofileform v-bind:selectr="createable" formProfileName="creatableColorProfile"/>
        </b-col>
      </b-row>
    </b-container>
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
      this.$store.commit('updateColorProfile', {type: 'editableColorProfile', object: e})
    },
    makeSuccessNotification (text) {
      this.makeToast({variant: 'success', content: text})
    },
    makeErrorNotification (error) {
      console.log(error)
      this.makeToast({variant: 'danger', content: error.message})
    },
    makeToast (obj) {
      this.$bvToast.toast(obj.content, {
        title: ` ${obj.variant || 'default'}`,
        variant: obj.variant,
        solid: true
      })
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
