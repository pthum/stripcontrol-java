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
            <colorprofileselect selectProfileName="selectProfile" :selectId="strip.id" :preselected="strip.profileId"/>
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
import colorprofileselect from './colorprofile-select'
import ApiManager from './api-manager'
import EventBus from './eventbus'
import { Ui, EventType } from './constant-contig'
import { mapMutations, mapGetters } from 'vuex'

export default {
  name: 'stripcontrolservice',
  components: {
    colorprofileselect
  },
  created () {
    ApiManager.callGetColorProfiles(this)
    ApiManager.callGetLedStrips(this)
  },
  computed: {
    ...mapGetters({
      storedBackendStrips: 'backendStrips'
    })
  },
  methods: {
    /** enable/disable strip */
    toggleEnabled (strip) {
      strip.enabled = !strip.enabled
      ApiManager.updateLedStrip(this, strip)
    },
    /** return the ui variant of a strip */
    getVariantEnabled (strip) {
      return Ui.getVariant(strip.enabled)
    },
    /** handle selection of a color profile */
    handleCPSelect (event) {
      ApiManager.updateStripProfile(this, { stripId: event.stripId, profile: event.object })
    },
    ...mapMutations({
      updateStoreStrip: 'updateLedStrip'
    })
  },
  mounted () {
    EventBus.$on(EventType.CP_SELECT, this.handleCPSelect)
  },
  beforeDestroy () {
    EventBus.$off(EventType.CP_SELECT, this.handleCPSelect)
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
