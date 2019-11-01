<template>
  <div class="stripcontrolService">
    <h1>Strip control</h1>
    <div v-if="storedBackendStrips.length==0">
      <b-container>
        <b-row>
          <b-col>No LED Strips created yet.</b-col>
        </b-row>
      </b-container>
    </div>
    <div v-else>
      <b-container fluid v-for="strip in storedBackendStrips" :key="strip.id">
        <b-row >
          <b-col>{{strip.name}}</b-col>
          <b-col>
            <colorprofileselect :selectProfileName="getAndUpdateStoreProfileForStrip(strip)" :selectId="strip.id" :preselected="strip.profileId"/>
          </b-col>
          <b-col>
            <b-button :variant="getVariantEnabled(strip)" @click="toggleEnabled(strip)">
              <font-awesome-icon v-if="strip.enabled" icon="sun"></font-awesome-icon>
              <font-awesome-icon v-else icon="lightbulb"></font-awesome-icon>
            </b-button>
          </b-col>
        </b-row>
      </b-container>
  </div>
  </div>
</template>

<script>
import api from './backend-api'
import colorprofileselect from './colorprofile-select'
import EventBus from './eventbus'
import {mapMutations, mapGetters} from 'vuex'

export default {
  name: 'stripcontrolservice',
  components: {
    colorprofileselect
  },
  created () {
    this.callGetColorProfiles()
    this.callGetLedStrips()
  },
  data () {
    return {
      errors: []
    }
  },
  computed: {
    ...mapGetters({
      storeSelectedProfile: 'selectedProfile',
      storedBackendStrips: 'backendStrips',
      storedBackendProfiles: 'backendProfiles',
      getColorProfileById: 'getColorProfileById'
    })
  },
  methods: {
    getAndUpdateStoreProfileForStrip (strip) {
      // var profile = this.getColorProfileById(strip.profileId)
      // this.updateStoreProfile({type: 'selectProfile', stripId: strip.id, object: profile})
      return 'selectProfile'
    },
    toggleEnabled (strip) {
      strip.enabled = !strip.enabled
      api.putLedStrip(strip)
    },
    getVariantEnabled (strip) {
      return strip.enabled ? 'outline-dark' : 'dark'
    },
    /** Fetches strips when the component is created. */
    callGetLedStrips () {
      api.getLedStrips().then(response => {
        this.updateStoreStrips(response.data)
      }).catch(error => {
        this.errors.push(error)
      })
    },
    /** Fetches profiles when the component is created. */
    callGetColorProfiles () {
      api.getColorProfiles().then(response => {
        this.updateStoreProfiles(response.data)
      }).catch(error => {
        this.errors.push(error)
      })
    },
    handleCPSelect (event) {
      console.log(event)
      api.updateStripProfile({stripId: event.stripId, profile: event.object}).then(response => {
        this.callGetColorProfiles()
      }).catch(error => {
        this.errors.push(error)
      })
    },
    ...mapMutations({
      updateStoreStrip: 'updateLedStrip',
      updateStoreStrips: 'updateBackendStrips',
      updateStoreProfiles: 'updateBackendProfiles',
      updateStoreProfile: 'updateColorProfile',
      resetStoreStrip: 'resetLedStrip'
    })
  },
  mounted () {
    EventBus.$on('CPselect', this.handleCPSelect)
  },
  beforeDestroy () {
    EventBus.$off('CPselect', this.handleCPSelect)
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
.foo {
  float: left;
  width: 20px;
  height: 20px;
  margin: 5px;
  border: 1px solid rgba(0, 0, 0, .2);
}
</style>
