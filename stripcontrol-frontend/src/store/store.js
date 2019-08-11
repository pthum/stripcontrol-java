import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export const store = new Vuex.Store({
  state: {
    backendProfiles: [],
    editableColorProfile: {
      red: 0,
      green: 0,
      blue: 0,
      brightness: 0
    },
    creatableColorProfile: {
      red: 0,
      green: 0,
      blue: 0,
      brightness: 0
    }
  },
  mutations: {
    updateBackendProfiles (state, loadedBackendProfiles) {
      state.backendProfiles = loadedBackendProfiles
    },
    updateColorProfile (state, obj) {
      state[obj.type] = obj.object
    }
  },
  getters: {
    backendProfiles: state => state.backendProfiles,
    editableColorProfile: state => state.editableColorProfile,
    creatableColorProfile: state => state.creatableColorProfile,
    findColorProfile: (state) => (formName) => {
      return state[formName]
    }
  }
})
