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
            <b-button :variant="variantEdit" :disabled="disabledEdit" @click="toggleEdit()"><font-awesome-icon icon="edit"> </font-awesome-icon></b-button>
            <b-button :variant="variantCreate" :disabled="disabledCreate" @click="toggleCreate()"><font-awesome-icon icon="plus-square"> </font-awesome-icon></b-button>
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
import colorhelper from './colorhelper'
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
    this.toggleCreate()
    this.disabledEdit = true
  },
  data () {
    return {
      errors: [],
      variantEdit: Ui.VRNT_DISABLED,
      variantCreate: Ui.VRNT_DISABLED,
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
    getHexColor (profile) {
      return colorhelper.rgbToHex2(profile)
    },
    callGetColorProfiles () {
      ApiManager.callGetColorProfiles(this)
    },
    /** sets the current set profile as profile to edit */
    toggleEdit () {
      this.updateStoreProfile({ type: 'editableProfile', object: this.storeSelectedProfile })
      this.variantEdit = Ui.VRNT_ENABLED
      this.disabledEdit = true
      this.variantCreate = Ui.VRNT_DISABLED
      this.disabledCreate = false
    },
    /** resets the profile to edit to initial values */
    toggleCreate () {
      this.resetStoreProfile({ type: 'editableProfile' })
      this.variantEdit = Ui.VRNT_DISABLED
      this.disabledEdit = false
      this.variantCreate = Ui.VRNT_ENABLED
      this.disabledCreate = true
    },
    /** set the created object as selected profile, update the colorprofiles, inform user  */
    handleCPCreate (event) {
      this.updateStoreProfile({ type: 'selectedProfile', object: event.object })
      this.updateColorProfileInBackendList(event.object)
      this.toggleEdit()
      this.makeToast(event)
    },
    /** reset the selected profile, update the colorprofiles, inform user */
    handleCPDelete (event) {
      this.resetStoreProfile({ type: 'selectedProfile' })
      this.removeColorProfileInBackendList(event.object)
      this.toggleCreate()
      this.makeToast(event)
    },
    handleCPSelect (event) {
      this.updateStoreProfile({ type: 'selectedProfile', object: event.object })
      this.toggleEdit()
    },
    handleCPGetAll (event) {
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
      updateStoreProfile: 'updateColorProfile',
      updateStoreProfiles: 'updateBackendProfiles',
      resetStoreProfile: 'resetColorProfile',
      updateColorProfileInBackendList: 'updateColorProfileInBackendList',
      removeColorProfileInBackendList: 'removeColorProfileInBackendList'
    })
  },
  mounted () {
    EventBus.$on(EventType.CP_CREATE, this.handleCPCreate)
    EventBus.$on(EventType.CP_UPDATE, this.handleCPCreate)
    EventBus.$on(EventType.CP_DELETE, this.handleCPDelete)
    EventBus.$on(EventType.CP_SELECT, this.handleCPSelect)
    EventBus.$on(EventType.CP_GETALL, this.handleCPGetAll)
  },
  beforeDestroy () {
    EventBus.$off(EventType.CP_CREATE, this.handleCPCreate)
    EventBus.$off(EventType.CP_UPDATE, this.handleCPCreate)
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
