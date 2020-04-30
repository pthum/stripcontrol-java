<template>
  <div class="color-profile">
    <p></p>
    <h4 v-if="typeof id !== 'undefined'">Edit Profile {{id}}</h4>
    <h4 v-else>Create ColorProfile</h4>
    <b-container fluid>
      <form v-on:submit.prevent="saveEntry">
          <b-row class="my-1">
              <b-col sm="3"><label><font-awesome-icon icon="sun"> </font-awesome-icon></label></b-col>
              <b-col sm="8"><b-form-input id="brightnessValue" type="range" min="0" max="100" :value="brightness" @input="handleBrightness"/></b-col>
              <b-col sm="1">{{ brightness }}</b-col>
          </b-row>
          <b-row class="my-1">
              <b-col sm="3"><label><font-awesome-icon icon="palette"> </font-awesome-icon></label></b-col>
              <b-col sm="9"><b-form-input id="colorValue" type="color" :value="hexVal" @input="handleColorHex"/></b-col>
          </b-row>
          <b-row>
            <b-col>
              <b-button-group>
                <b-button variant="danger" v-b-modal.modal-remove-colorprofile v-if="typeof id !== 'undefined'"><font-awesome-icon icon="trash"> </font-awesome-icon> Delete {{id}}</b-button>
                <b-button variant="success" type="submit" v-if="typeof id !== 'undefined'"><font-awesome-icon icon="edit"> </font-awesome-icon> Edit {{id}}</b-button>
                <b-button variant="success" type="submit" v-else><font-awesome-icon icon="plus-square"> </font-awesome-icon> Create</b-button>
              </b-button-group>
              <b-modal id="modal-remove-colorprofile" header-bg-variant="dark" header-text-variant="danger" title="Remove profile?" @ok="deleteEntry">
                <p class="my-4">Really remove profile {{id}}?</p>
              </b-modal>
            </b-col>
          </b-row>
      </form>
    </b-container>
  </div>
</template>

<script>
import colorhelper from './colorhelper'
import ApiManager from './api-manager'
import { mapMutations, mapGetters } from 'vuex'

export default {
  name: 'color-form',
  props: ['formProfileName'],
  computed: {
    red () {
      return this.findColorProfile(this.formProfileName).red
    },
    green () {
      return this.findColorProfile(this.formProfileName).green
    },
    blue () {
      return this.findColorProfile(this.formProfileName).blue
    },
    brightness () {
      return this.findColorProfile(this.formProfileName).brightness
    },
    id () {
      return this.findColorProfile(this.formProfileName).id
    },
    hexVal () {
      return colorhelper.rgbToHex(this.findColorProfile(this.formProfileName))
    },
    ...mapGetters([
      'findColorProfile'
    ])
  },
  methods: {
    /** handles an update of hex value */
    handleColorHex (hexValue) {
      var result = colorhelper.hexToRgb(hexValue)
      var obj = { red: result.r, green: result.g, blue: result.b, brightness: this.brightness, id: this.id }
      this.updateStoreProfile({ type: this.formProfileName, object: obj })
    },
    /** handles an update of brightness */
    handleBrightness (value) {
      var obj = { red: this.red, green: this.green, blue: this.blue, brightness: Number(value), id: this.id }
      this.updateStoreProfile({ type: this.formProfileName, object: obj })
    },
    /** save an entry, will do an update if id is set, create otherwise */
    saveEntry () {
      var obj = { red: this.red, green: this.green, blue: this.blue, brightness: this.brightness, id: this.id }
      if (typeof this.id !== 'undefined') {
        ApiManager.updateColorProfile(this, obj)
      } else {
        ApiManager.createColorProfile(this, obj)
      }
    },
    /** delete an entry */
    deleteEntry () {
      var obj = { id: this.id }
      ApiManager.deleteColorProfile(this, obj)
    },
    ...mapMutations({
      updateStoreProfile: 'updateColorProfile'
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
</style>
