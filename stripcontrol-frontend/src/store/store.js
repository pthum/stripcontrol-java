import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const colorProfileStore = {
  state: {
    backendProfiles: [],
    editableProfile: {
      red: 0,
      green: 0,
      blue: 0,
      brightness: 0
    },
    selectedProfile: {}
  },
  mutations: {
    /** update the backend profiles */
    updateBackendProfiles (state, loadedBackendProfiles) {
      state.backendProfiles = loadedBackendProfiles
    },
    /** update the color profile, expects an object containing a field with type and a field with name object
     * containing the object */
    updateColorProfile (state, obj) {
      state[obj.type] = obj.object
    },
    /** resets the color profile to 0 values, without id, expects an object containing a type field */
    resetColorProfile (state, obj) {
      state[obj.type] = {red: 0, green: 0, blue: 0, brightness: 0}
    }
  },
  getters: {
    backendProfiles: state => state.backendProfiles,
    editableProfile: state => state.editableProfile,
    selectedProfile: state => state.selectedProfile,
    findColorProfile: (state) => (formName) => {
      return state[formName]
    }
  }
}

const ledStripStore = {
  state: {
    backendStrips: [],
    editableStrip: {
      name: '',
      description: '',
      misoPin: 0,
      numLeds: 0,
      sclkPin: 0,
      speedHz: 8000000
    },
    selectedStrip: {}
  },
  mutations: {
    /** update the backend strips */
    updateBackendStrips (state, loadedLedStrips) {
      state.backendStrips = loadedLedStrips
    },
    /** update the LED strip, expects an object containing a field with type and a field with name object
     * containing the object */
    updateLedStrip (state, obj) {
      state[obj.type] = obj.object
    },
    /** resets the led strip to 0 values, without id, expects an object containing a type field */
    resetLedStrip (state, obj) {
      state[obj.type] = { name: '', description: '', misoPin: 0, numLeds: 0, sclkPin: 0, speedHz: 8000000 }
    }
  },
  getters: {
    backendStrips: state => state.backendStrips,
    editableStrip: state => state.editableStrip,
    selectedStrip: state => state.selectedStrip,
    findLedStrip: (state) => (formName) => {
      return state[formName]
    }
  }
}

export const store = new Vuex.Store({
  modules: {
    profileStore: colorProfileStore,
    stripStore: ledStripStore
  }
})
