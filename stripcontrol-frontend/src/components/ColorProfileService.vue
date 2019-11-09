<template>
  <div class="colorProfileService">
    <h1>ColorProfiles</h1>
    <b-container fluid>
      <b-row class="my-1" >
        <b-col sm="3">
        </b-col>
        <b-col sm="5">
          <b-button-group >
            <colorprofileselect selectProfileName="selectedProfile" :preselected="storeSelectedProfile.id"/>
            <b-button variant="dark" @click="callGetColorProfiles()"><font-awesome-icon icon="sync" /></b-button>
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
import api from './backend-api'
import colorprofileform from './colorprofile-form'
import colorprofileselect from './colorprofile-select'
import EventBus from './eventbus'
import colorhelper from './colorhelper'
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
      variantEdit: 'dark',
      variantCreate: 'dark',
      disabledEdit: false,
      disabledCreate: false
    }
  },
  computed: {
    ...mapGetters({
      storeSelectedProfile: 'selectedProfile',
      storedBackendProfiles: 'backendProfiles'
    })
  },
  methods: {
    getHexColor (profile) {
      return colorhelper.rgbToHex2(profile)
    },
    /** Fetches profiles when the component is created. */
    callGetColorProfiles () {
      api.getColorProfiles().then(response => {
        this.updateStoreProfiles(response.data)
        if (response.data.length === 0) {
          this.disabledEdit = true
        }
      }).catch(error => {
        this.errors.push(error)
      })
    },
    /** sets the current set profile as profile to edit */
    toggleEdit () {
      this.updateStoreProfile({ type: 'editableProfile', object: this.storeSelectedProfile })
      this.variantEdit = 'outline-dark'
      this.disabledEdit = true
      this.variantCreate = 'dark'
      this.disabledCreate = false
    },
    /** resets the profile to edit to initial values */
    toggleCreate () {
      this.resetStoreProfile({ type: 'editableProfile' })
      this.variantEdit = 'dark'
      this.disabledEdit = false
      this.variantCreate = 'outline-dark'
      this.disabledCreate = true
    },
    /** set the created object as selected profile, update the colorprofiles, inform user  */
    handleCPCreate (event) {
      console.log(event)
      this.updateStoreProfile({ type: 'selectedProfile', object: event.object })
      this.updateColorProfileInBackendList(event.object)
      this.toggleEdit()
      this.makeToast(event)
    },
    /** reset the selected profile, update the colorprofiles, inform user */
    handleCPDelete (event) {
      console.log(event)
      this.resetStoreProfile({ type: 'selectedProfile' })
      this.removeColorProfileInBackendList(event.object)
      this.toggleCreate()
      this.makeToast(event)
    },
    handleCPSelect (event) {
      this.updateStoreProfile({ type: 'selectedProfile', object: event.object })
      this.toggleEdit()
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
    EventBus.$on('CPupdate', this.handleCPCreate)
    EventBus.$on('CPcreate', this.handleCPCreate)
    EventBus.$on('CPdelete', this.handleCPDelete)
    EventBus.$on('CPselect', this.handleCPSelect)
  },
  beforeDestroy () {
    EventBus.$off('CPupdate', this.handleCPCreate)
    EventBus.$off('CPcreate', this.handleCPCreate)
    EventBus.$off('CPdelete', this.handleCPDelete)
    EventBus.$off('CPselect', this.handleCPSelect)
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
