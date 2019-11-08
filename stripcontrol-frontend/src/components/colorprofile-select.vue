<template>
  <div class="colorProfileService">
    <b-dropdown v-model="selected" class="selectpicker" variant="dark" :text="stringSelected">
      <b-dropdown-item v-if="storedBackendProfiles.length === 0" disabled>No profiles available</b-dropdown-item>
      <b-dropdown-item v-else v-for="profile in storedBackendProfiles" :key="profile.id" :value="profile" @click="handleSelection(profile)">
        <div class="foo" :style="{backgroundColor: getHexColor(profile) }">&nbsp;</div><font-awesome-icon icon="sun"></font-awesome-icon>: {{profile.brightness}}
      </b-dropdown-item>
    </b-dropdown>
  </div>
</template>

<script>
import colorhelper from './colorhelper'
import EventBus from './eventbus'
import {mapMutations, mapGetters} from 'vuex'

export default {
  name: 'colorprofile-select',
  props: [ 'selectProfileName', 'selectId', 'preselected' ],
  created () {
    this.preselectObject()
  },
  data () {
    return {
      errors: [],
      selected: {}
    }
  },
  computed: {
    stringSelected () {
      if (typeof this.selected === 'undefined' || typeof this.selected.id === 'undefined') {
        return 'Select profile'
      }
      var brightness = typeof this.selected.brightness !== 'undefined' ? this.selected.brightness : 0
      return colorhelper.rgbToHex2(this.selected) + ', \u2600:' + brightness
    },
    ...mapGetters({
      storedBackendProfiles: 'backendProfiles'
    })
  },
  methods: {
    preselectObject () {
      this.selected = this.storedBackendProfiles.find(profile => profile.id === this.preselected)
    },
    handleSelection (profile) {
      this.selected = profile
      this.handleSelect({object: this.selected, stripId: this.selectId, type: this.selectProfileName})
    },
    getHexColor (profile) {
      return colorhelper.rgbToHex2(profile)
    },
    handleSelect (event) {
      EventBus.$emit('CPselect', event)
    },
    ...mapMutations({
      resetStoreProfile: 'resetColorProfile'
    })
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
