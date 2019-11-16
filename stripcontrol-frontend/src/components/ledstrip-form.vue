<template>
  <div class="led-strip">
    <p></p>
    <h4 v-if="typeof id !== 'undefined'">Edit Strip &quot;{{name}}&quot; ({{id}})</h4>
    <h4 v-else>Create LED Strip</h4>
    <b-container fluid>
      <form v-on:submit.prevent="saveEntry">
          <b-row class="my-1">
              <b-col sm="3"><label>Name</label></b-col>
              <b-col sm="9">
                <b-form-input id="nameValue" type="text" aria-describedby="input-name-required-feedback" v-model="name" :state="nameState" />
                <b-form-invalid-feedback id="input-name-required-feedback">Required</b-form-invalid-feedback>
              </b-col>
          </b-row>
          <b-row class="my-1">
              <b-col sm="3"><label>Description</label></b-col>
              <b-col sm="9"><b-form-input id="descriptionValue" type="text" v-model="description"/></b-col>
          </b-row>
          <b-row class="my-1">
              <b-col sm="3">
                <label><a href="javascript:void(0)" v-b-modal.modal-show-pinout v-b-tooltip title="Show GPIO pinout">MISO Pin</a></label>
              </b-col>
              <b-col sm="9">
                <b-form-input id="misoPinValue" type="number" v-model="misoPin" :state="misoValid"/>
                <b-form-invalid-feedback id="input-name-required-feedback">Invalid Pin</b-form-invalid-feedback>
              </b-col>
          </b-row>
          <b-row class="my-1">
              <b-col sm="3">
                <label><a href="javascript:void(0)" v-b-modal.modal-show-pinout v-b-tooltip title="Show GPIO pinout">SCLK Pin</a></label></b-col>
              <b-col sm="9">
                <b-form-input id="sclkPinValue" type="number" v-model="sclkPin" :state="sclkValid"/>
                <b-form-invalid-feedback id="input-name-required-feedback">Invalid Pin</b-form-invalid-feedback>
              </b-col>
          </b-row>
          <b-row class="my-1">
              <b-col sm="3"><label># LEDs</label></b-col>
              <b-col sm="9">
                <b-form-input id="numLedValue" type="number" v-model="numLeds" :state="numLedsValid"/>
                <b-form-invalid-feedback id="input-name-required-feedback">Minimum one LED necessary</b-form-invalid-feedback>
              </b-col>
          </b-row>
          <b-row class="my-1">
              <b-col sm="3"><label>Speed (Hz)</label></b-col>
              <b-col sm="9"><b-form-input id="speedValue" type="number" v-model="speedHz"/></b-col>
          </b-row>
          <b-row>
            <b-col>
              <b-button-group>
                <b-button variant="danger" v-b-modal.modal-remove-ledstrip v-if="typeof id !== 'undefined'"><font-awesome-icon icon="trash"> </font-awesome-icon> Delete &quot;{{name}}&quot;</b-button>
                <b-button variant="success" type="submit" :disabled="writable === false" v-if="typeof id !== 'undefined'"><font-awesome-icon icon="edit"> </font-awesome-icon> Edit &quot;{{name}}&quot;</b-button>
                <b-button variant="success" type="submit" :disabled="writable === false" v-else><font-awesome-icon icon="plus-square"> </font-awesome-icon> Create</b-button>
              </b-button-group>
              <b-modal id="modal-remove-ledstrip" header-bg-variant="dark" header-text-variant="danger" title="Remove strip?" @ok="deleteEntry">
                <p class="my-4">Really remove led strip &quot;{{name}}&quot; ?</p>
              </b-modal>
            </b-col>
          </b-row>
      </form>
    </b-container>
    <b-modal id="modal-show-pinout" header-bg-variant="dark" header-text-variant="danger" ok-only title="Pinout">
      <b-img src="https://pinout.xyz/resources/raspberry-pi-pinout.png" fluid alt="Responsive image"></b-img>
    </b-modal>
  </div>
</template>

<script>
import ApiManager from './api-manager'
import { mapMutations, mapGetters } from 'vuex'

export default {
  name: 'strip-form',
  props: [ 'formStripName' ],
  computed: {
    name: {
      get () {
        return this.findLedStrip(this.formStripName).name
      },
      set (value) {
        this.findLedStrip(this.formStripName).name = value
      }
    },
    /* indicates, whether the name input field is valid */
    nameState () {
      return (this.findLedStrip(this.formStripName).name.length > 0) ? null : false
    },
    misoValid () {
      var currentPin = this.findLedStrip(this.formStripName).misoPin
      currentPin = parseInt(currentPin, 10)
      return currentPin >= 0 && currentPin <= 27 ? null : false
    },
    sclkValid () {
      var currentPin = this.findLedStrip(this.formStripName).sclkPin
      currentPin = parseInt(currentPin, 10)
      return currentPin >= 0 && currentPin <= 27 ? null : false
    },
    numLedsValid () {
      return parseInt(this.findLedStrip(this.formStripName).numLeds, 10) > 0 ? null : false
    },
    /* indicates, whether the form can be submitted (create or update) */
    writable () {
      return this.nameState && this.misoValid && this.sclkValid
    },
    description: {
      get () {
        return this.findLedStrip(this.formStripName).description
      },
      set (value) {
        this.findLedStrip(this.formStripName).description = value
      }
    },
    misoPin: {
      get () {
        return this.findLedStrip(this.formStripName).misoPin
      },
      set (value) {
        this.findLedStrip(this.formStripName).misoPin = value
      }
    },
    numLeds: {
      get () {
        return this.findLedStrip(this.formStripName).numLeds
      },
      set (value) {
        this.findLedStrip(this.formStripName).numLeds = value
      }
    },
    sclkPin: {
      get () {
        return this.findLedStrip(this.formStripName).sclkPin
      },
      set (value) {
        this.findLedStrip(this.formStripName).sclkPin = value
      }
    },
    speedHz: {
      get () {
        return this.findLedStrip(this.formStripName).speedHz
      },
      set (value) {
        this.findLedStrip(this.formStripName).speedHz = value
      }
    },
    id () {
      return this.findLedStrip(this.formStripName).id
    },
    ...mapGetters([
      'findLedStrip'
    ])
  },
  methods: {
    /** save an entry, will do an update if id is set, create otherwise */
    saveEntry () {
      var obj = { name: this.name, description: this.description, misoPin: this.misoPin, sclkPin: this.sclkPin, numLeds: this.numLeds, speedHz: this.speedHz, id: this.id }
      if (typeof this.id !== 'undefined') {
        ApiManager.updateLedStrip(this, obj)
      } else {
        ApiManager.createLedStrip(this, obj)
      }
    },
    /** delete an entry */
    deleteEntry () {
      var obj = { id: this.id, name: this.name }
      ApiManager.deleteLedStrip(this, obj)
    },
    ...mapMutations({
      updateStoreStrip: 'updateLedStrip',
      resetStoreStrip: 'resetLedStrip'
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
