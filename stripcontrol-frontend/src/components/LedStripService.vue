<template>
  <div class="ledStripService">
    <h1>LED Strips</h1>
    <b-container fluid>
      <b-row class="my-1" >
        <b-col sm="3">
        </b-col>
        <b-col sm="5">
          <b-button-group >
            <ledstripselect/>
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
import ApiManager from './api-manager'
import ledstripform from './ledstrip-form'
import ledstripselect from './ledstrip-select'
import EventBus from './eventbus'
import { Ui, EventType } from './constant-contig'
import { mapMutations, mapGetters } from 'vuex'

export default {
  name: 'ledstripservice',
  components: {
    ledstripform,
    ledstripselect
  },
  created () {
    this.callGetLedStrips()
    this.toggleCreate()
    this.disabledEdit = true
  },
  data () {
    return {
      variantEdit: Ui.VRNT_DISABLED,
      variantCreate: Ui.VRNT_DISABLED,
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
    ...mapGetters({
      storeSelectedStrip: 'selectedStrip'
    })
  },
  methods: {
    /** Fetches strips when the component is created. */
    callGetLedStrips () {
      ApiManager.callGetLedStrips(this)
    },
    toggle (isCreate) {

    },
    /** sets the current set strip as strip to edit */
    toggleEdit () {
      this.updateStoreStrip({ type: 'editableStrip', object: this.storeSelectedStrip })
      this.variantEdit = Ui.VRNT_ENABLED
      this.disabledEdit = true
      this.variantCreate = Ui.VRNT_DISABLED
      this.disabledCreate = false
    },
    /** resets the strip to edit to initial values */
    toggleCreate () {
      this.resetStoreStrip({ type: 'editableStrip' })
      this.variantEdit = Ui.VRNT_DISABLED
      this.disabledEdit = false
      this.variantCreate = Ui.VRNT_ENABLED
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
    handleLSSelect (event) {
      this.updateStoreStrip({ type: 'selectedStrip', object: event.object })
      this.toggleEdit()
    },
    handleLSGetAll (event) {
      if (event.object.length === 0) {
        this.disabledEdit = true
      }
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
    EventBus.$on(EventType.LS_CREATE, this.handleLSCreate)
    EventBus.$on(EventType.LS_UPDATE, this.handleLSCreate)
    EventBus.$on(EventType.LS_DELETE, this.handleLSDelete)
    EventBus.$on(EventType.LS_SELECT, this.handleLSSelect)
    EventBus.$on(EventType.LS_GETALL, this.handleLSGetAll)
  },
  beforeDestroy () {
    EventBus.$off(EventType.LS_CREATE, this.handleLSCreate)
    EventBus.$off(EventType.LS_UPDATE, this.handleLSCreate)
    EventBus.$off(EventType.LS_DELETE, this.handleLSDelete)
    EventBus.$off(EventType.LS_SELECT, this.handleLSSelect)
    EventBus.$off(EventType.LS_GETALL, this.handleLSGetAll)
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
