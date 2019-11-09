<template>
  <div class="ledStripService">
    <h1>LED Strips</h1>
    <b-container fluid>
      <b-row class="my-1" >
        <b-col sm="3">
        </b-col>
        <b-col sm="5">
          <b-button-group >
            <b-dropdown v-model="selected" class="selectpicker" variant="dark" :text="stringSelected">
              <b-dropdown-item v-if="storedBackendStrips.length === 0" disabled>No strips available</b-dropdown-item>
              <b-dropdown-item v-else v-for="strip in storedBackendStrips" :key="strip.id" :value="strip" @click="selected = strip">
                 {{strip.name}}
              </b-dropdown-item>
            </b-dropdown>

            <b-button variant="dark" @click="callGetLedStrips()"><font-awesome-icon icon="sync" /></b-button>
            <b-button :variant="variantEdit" :disabled="disabledEdit" @click="toggleEdit()"><font-awesome-icon icon="edit"> </font-awesome-icon></b-button>
            <b-button :variant="variantCreate" :disabled="disabledCreate" @click="toggleCreate()"><font-awesome-icon icon="plus-square"> </font-awesome-icon></b-button>
          </b-button-group>
        </b-col>
        <b-col>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
        <ledstripform formStripName="editableStrip"/>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import api from './backend-api'
import ledstripform from './ledstrip-form'
import EventBus from './eventbus'
import { mapMutations, mapGetters } from 'vuex'

export default {
  name: 'ledstripservice',
  components: {
    ledstripform
  },
  created () {
    this.callGetLedStrips()
    this.toggleCreate()
    this.disabledEdit = true
  },
  data () {
    return {
      errors: [],
      variantEdit: 'dark',
      variantCreate: 'dark',
      disabledEdit: false,
      disabledCreate: false
    }
  },
  computed: {
    selected: {
      get () {
        return this.storeSelectedStrip
      },
      set (value) {
        this.updateStoreStrip({ type: 'selectedStrip', object: value })
        this.toggleEdit()
      }
    },
    stringSelected: function () {
      if (typeof this.selected.id === 'undefined') {
        return 'Select strip'
      }
      return this.selected.name
    },
    ...mapGetters({
      storeSelectedStrip: 'selectedStrip',
      storedBackendStrips: 'backendStrips'
    })
  },
  methods: {
    /** Fetches strips when the component is created. */
    callGetLedStrips () {
      api.getLedStrips().then(response => {
        this.updateStoreStrips(response.data)
        if (response.data.length === 0) {
          this.disabledEdit = true
        }
      }).catch(error => {
        this.errors.push(error)
      })
    },
    /** sets the current set strip as strip to edit */
    toggleEdit () {
      this.updateStoreStrip({ type: 'editableStrip', object: this.storeSelectedStrip })
      this.variantEdit = 'outline-dark'
      this.disabledEdit = true
      this.variantCreate = 'dark'
      this.disabledCreate = false
    },
    /** resets the strip to edit to initial values */
    toggleCreate () {
      this.resetStoreStrip({ type: 'editableStrip' })
      this.variantEdit = 'dark'
      this.disabledEdit = false
      this.variantCreate = 'outline-dark'
      this.disabledCreate = true
    },
    /** set the created object as selected strip, update the led strips, inform user  */
    handleLSCreate (event) {
      this.updateStoreStrip({ type: 'selectedStrip', object: event.object })
      this.toggleEdit()
      this.updateLedStripInBackendList(event.object)
      this.makeToast(event)
    },
    /** reset the selected strip, update the led strips, inform user */
    handleLSDelete (event) {
      this.resetStoreStrip({ type: 'selectedStrip' })
      this.removeLedStripInBackendList(event.object)
      this.toggleCreate()
      this.makeToast(event)
    },
    /** makes a toast, expects an object with content field and variant field */
    makeToast (toastData) {
      this.$bvToast.toast(toastData.content, {
        title: `${toastData.variant || 'default'}`,
        variant: toastData.variant,
        solid: true
      })
    },
    ...mapMutations({
      updateStoreStrip: 'updateLedStrip',
      updateStoreStrips: 'updateBackendStrips',
      resetStoreStrip: 'resetLedStrip',
      updateLedStripInBackendList: 'updateLedStripInBackendList',
      removeLedStripInBackendList: 'removeLedStripInBackendList'
    })
  },
  mounted () {
    EventBus.$on('LSupdate', this.handleLSCreate)
    EventBus.$on('LScreate', this.handleLSCreate)
    EventBus.$on('LSdelete', this.handleLSDelete)
  },
  beforeDestroy () {
    EventBus.$off('LSupdate', this.handleLSCreate)
    EventBus.$off('LScreate', this.handleLSCreate)
    EventBus.$off('LSdelete', this.handleLSDelete)
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
