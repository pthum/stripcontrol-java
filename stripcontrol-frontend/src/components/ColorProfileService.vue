<template>
  <div class="colorProfileService">
    <h1>ColorProfiles</h1>
    <b-container fluid>
      <b-row class="my-1" >
        <b-col sm="3">
        </b-col>
        <b-col sm="5">
          <b-button-group>
            <colorprofileselect selectProfileName="selectedProfile" :preselected="storeSelectedProfile.id"/>
            <b-button variant="dark" @click="callGetColorProfiles(this)"><font-awesome-icon icon="sync" /></b-button>
            <b-button :variant="variantEdit" :disabled="disabledEdit" @click="toggle(false)"><font-awesome-icon icon="edit"> </font-awesome-icon></b-button>
            <b-button :variant="variantCreate" :disabled="disabledCreate" @click="toggle(true)"><font-awesome-icon icon="plus-square"> </font-awesome-icon></b-button>
          </b-button-group>
        </b-col>
        <b-col>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <colorprofileform formProfileName="editableProfile"/>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import ApiManager from './api-manager'
import colorprofileform from './colorprofile-form'
import colorprofileselect from './colorprofile-select'
import EventBus from './eventbus'
import { Ui, EventType } from './constant-contig'
import { mapMutations, mapGetters } from 'vuex'

export default {
  name: 'colorprofileservice',
  components: {
    colorprofileform,
    colorprofileselect
  },
  created () {
    this.callGetColorProfiles()
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
    ...mapGetters({
      storeSelectedProfile: 'selectedProfile'
    })
  },
  methods: {
    callGetColorProfiles () {
      ApiManager.callGetColorProfiles()
    },
    toggle (isCreate) {
      if (isCreate) {
        this.resetStoreProfile({ type: 'editableProfile' })
      } else {
        this.updateStoreProfile({ type: 'editableProfile', object: this.storeSelectedProfile })
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
    /** set the saved object as selected profile, update the colorprofiles, inform user  */
    handleCPSave (event) {
      this.updateStoreProfile({ type: 'selectedProfile', object: event.object })
      this.updateColorProfileInBackendList(event.object)
      this.toggle(false)
      EventBus.makeToast(this, event)
    },
    /** reset the selected profile, update the colorprofiles, inform user */
    handleCPDelete (event) {
      this.resetStoreProfile({ type: 'selectedProfile' })
      this.removeColorProfileInBackendList(event.object)
      this.toggle(true)
      EventBus.makeToast(this, event)
    },
    handleCPSelect (event) {
      this.updateStoreProfile({ type: 'selectedProfile', object: event.object })
      this.toggle(false)
    },
    handleCPGetAll (event) {
      if (event.object.length === 0) {
        this.disabledEdit = true
      }
    },
    ...mapMutations({
      updateStoreProfile: 'updateColorProfile',
      resetStoreProfile: 'resetColorProfile',
      updateColorProfileInBackendList: 'updateColorProfileInBackendList',
      removeColorProfileInBackendList: 'removeColorProfileInBackendList'
    })
  },
  mounted () {
    EventBus.$on(EventType.CP_CREATE, this.handleCPSave)
    EventBus.$on(EventType.CP_UPDATE, this.handleCPSave)
    EventBus.$on(EventType.CP_DELETE, this.handleCPDelete)
    EventBus.$on(EventType.CP_SELECT, this.handleCPSelect)
    EventBus.$on(EventType.CP_GETALL, this.handleCPGetAll)
  },
  beforeDestroy () {
    EventBus.$off(EventType.CP_CREATE, this.handleCPSave)
    EventBus.$off(EventType.CP_UPDATE, this.handleCPSave)
    EventBus.$off(EventType.CP_DELETE, this.handleCPDelete)
    EventBus.$off(EventType.CP_SELECT, this.handleCPSelect)
    EventBus.$on(EventType.CP_GETALL, this.handleCPGetAll)
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
