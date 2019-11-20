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
            <b-button :variant="variantEdit" :disabled="disabledEdit" @click="toggle(false)"><font-awesome-icon icon="edit"> </font-awesome-icon></b-button>
            <b-button :variant="variantCreate" :disabled="disabledCreate" @click="toggle(true)"><font-awesome-icon icon="plus-square"> </font-awesome-icon></b-button>
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
    this.toggle(true)
    this.disabledEdit = true
  },
  data () {
    return {
      variantEdit: Ui.getVariant(false),
      variantCreate: Ui.getVariant(false),
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
        this.toggle(false)
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
      if (isCreate) {
        this.resetStoreStrip({ type: 'editableStrip' })
      } else {
        this.updateStoreStrip({ type: 'editableStrip', object: this.storeSelectedStrip })
      }
      this.toggleCreateElements(isCreate)
      this.toggleEditElements(!isCreate)
    },
    toggleCreateElements (isEnabled) {
      this.variantCreate = Ui.getVariant(isEnabled)
      this.disabledCreate = isEnabled
    },
    toggleEditElements (isEnabled) {
      this.variantEdit = Ui.getVariant(isEnabled)
      this.disabledEdit = isEnabled
    },
    /** set the created object as selected strip, update the led strips, inform user  */
    handleLSSave (event) {
      this.updateStoreStrip({ type: 'selectedStrip', object: event.object })
      this.toggle(false)
      this.updateLedStripInBackendList(event.object)
      EventBus.makeToast(this, event)
    },
    /** reset the selected strip, update the led strips, inform user */
    handleLSDelete (event) {
      this.resetStoreStrip({ type: 'selectedStrip' })
      this.removeLedStripInBackendList(event.object)
      this.toggle(true)
      EventBus.makeToast(this, event)
    },
    handleLSSelect (event) {
      this.updateStoreStrip({ type: 'selectedStrip', object: event.object })
      this.toggle(false)
    },
    handleLSGetAll (event) {
      if (event.object.length === 0) {
        this.disabledEdit = true
      }
    },
    ...mapMutations({
      updateStoreStrip: 'updateLedStrip',
      resetStoreStrip: 'resetLedStrip',
      updateLedStripInBackendList: 'updateLedStripInBackendList',
      removeLedStripInBackendList: 'removeLedStripInBackendList'
    })
  },
  mounted () {
    EventBus.$on(EventType.LS_CREATE, this.handleLSSave)
    EventBus.$on(EventType.LS_UPDATE, this.handleLSSave)
    EventBus.$on(EventType.LS_DELETE, this.handleLSDelete)
    EventBus.$on(EventType.LS_SELECT, this.handleLSSelect)
    EventBus.$on(EventType.LS_GETALL, this.handleLSGetAll)
  },
  beforeDestroy () {
    EventBus.$off(EventType.LS_CREATE, this.handleLSSave)
    EventBus.$off(EventType.LS_UPDATE, this.handleLSSave)
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
