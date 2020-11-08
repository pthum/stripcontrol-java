<template>
  <div class="colorProfileSelect">
    <b-dropdown v-model="selected" class="selectpicker" variant="dark" :text="stringSelected">
      <b-dropdown-item v-if="storedBackendProfiles.length === 0" disabled>No profiles available</b-dropdown-item>
      <b-dropdown-item v-else v-for="profile in storedBackendProfiles" :key="profile.id" :value="profile" @click="handleSelection(profile)">
        <div class="foo" :style="{backgroundColor: getHexColor(profile) }">&nbsp;</div>&#x2600;: {{profile.brightness}}
      </b-dropdown-item>
    </b-dropdown>
  </div>
</template>

<script>
import colorhelper from './colorhelper'
import EventBus from './eventbus'
import { mapGetters } from 'vuex'
import { EventType } from './constant-contig'

export default {
  name: 'colorprofile-select',
  props: ['selectProfileName', 'selectId', 'preselected'],
  created () {
    this.id = this.preselected
  },
  data () {
    return {
      id: -1
    }
  },
  computed: {
    selected () {
      return this.getColorProfileById(this.id)
    },
    stringSelected () {
      if (typeof this.selected === 'undefined' || typeof this.selected.id === 'undefined') {
        return 'Select profile'
      }
      var brightness = typeof this.selected.brightness !== 'undefined' ? this.selected.brightness : 0
      return this.getHexColor(this.selected) + ', \u2600:' + brightness
    },
    ...mapGetters({
      storedBackendProfiles: 'backendProfiles',
      getColorProfileById: 'getColorProfileById'
    })
  },
  methods: {
    handleSelection (profile) {
      this.id = profile.id
      EventBus.$emit(EventType.CP_SELECT, { object: profile, stripId: this.selectId, type: this.selectProfileName })
    },
    getHexColor (profile) {
      return colorhelper.rgbToHex(profile)
    },
    handleCPCreate (event) {
      if (event !== 'undefined' && event.object !== 'undefined' && event.object.id !== 'undefined') {
        this.id = event.object.id
      }
    }
  },
  mounted () {
    EventBus.$on(EventType.CP_CREATE, this.handleCPCreate)
  },
  beforeDestroy () {
    EventBus.$off(EventType.CP_CREATE, this.handleCPCreate)
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
