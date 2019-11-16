import api from './backend-api'
import EventBus from './eventbus'
import { EventType } from './constant-contig'
import { store } from '../store/store'

export default {
  /** create an entry */
  createLedStrip (vm, obj) {
    api.postLedStrip(obj).then(response => {
      var resUrlArray = response.headers.location.split('/')
      var createdId = resUrlArray[resUrlArray.length - 1]
      obj.id = Number(createdId)
      this.handleSuccess({ action: EventType.LS_CREATE, text: 'Successfully created led strip with id ' + createdId, object: obj })
    }).catch(error => {
      this.handleError(vm, error)
    })
  },
  /** update an entry */
  updateLedStrip (vm, obj) {
    api.putLedStrip(obj).then(response => {
      this.handleSuccess({ action: EventType.LS_UPDATE, text: 'Successfully updated led strip "' + obj.name + '"', object: obj })
    }).catch(error => {
      this.handleError(vm, error)
    })
  },
  /** delete an entry */
  deleteLedStrip (vm, obj) {
    api.deleteLedStrip(obj).then(response => {
      this.handleSuccess({ action: EventType.LS_DELETE, text: 'Deleted led strip "' + obj.name + '"', object: obj })
    }).catch(error => {
      this.handleError(vm, error)
    })
  },
  callGetLedStrips (vm) {
    api.getLedStrips().then(response => {
      store.commit('updateBackendStrips', response.data)
      this.handleSuccess({ action: EventType.LS_GETALL, text: 'Successfully got all led strips', object: response.data })
    }).catch(error => {
      this.handleError(vm, error)
    })
  },
  /** Fetches profiles when the component is created. */
  callGetColorProfiles (vm) {
    api.getColorProfiles().then(response => {
      store.commit('updateBackendProfiles', response.data)
      this.handleSuccess({ action: EventType.LS_GETALL, text: 'Successfully got all color profiles', object: response.data })
    }).catch(error => {
      this.handleError(vm, error)
    })
  },
  createColorProfile (vm, obj) {
    api.postColorProfile(obj).then(response => {
      var resUrlArray = response.headers.location.split('/')
      var createdId = resUrlArray[resUrlArray.length - 1]
      obj.id = Number(createdId)
      this.handleSuccess({ action: EventType.CP_CREATE, text: 'Successfully created color profile with id ' + createdId, object: obj })
    }).catch(error => {
      this.handleError(vm, error)
    })
  },
  /** update an entry */
  updateColorProfile (vm, obj) {
    api.putColorProfile(obj).then(response => {
      this.handleSuccess({ action: EventType.CP_UPDATE, text: 'Successfully updated color profile with id ' + obj.id, object: obj })
    }).catch(error => {
      this.handleError(vm, error)
    })
  },
  /** delete an entry */
  deleteColorProfile (vm, obj) {
    api.deleteColorProfile(obj).then(response => {
      this.handleSuccess({ action: EventType.CP_DELETE, text: 'Deleted color profile with id ' + obj.id, object: obj })
    }).catch(error => {
      this.handleError(vm, error)
    })
  },
  /** handle success message, expects object with text field and optionally an object field */
  handleSuccess (event) {
    EventBus.$emit(event.action, { variant: 'success', content: event.text, object: event.object })
  },
  /** handle error message */
  handleError (vm, error) {
    EventBus.makeToast(vm, { variant: 'danger', content: error.message })
    throw error
  }
}
