import axios from 'axios'

const AXIOS = axios.create({
  baseURL: `/api`,
  timeout: 1000
})

export default {
  getColorProfiles () {
    return AXIOS.get(`/colorprofile`)
  },
  putColorProfile (colorProfile) {
    return AXIOS.put(`/colorprofile/${colorProfile.id}`, colorProfile)
  },
  postColorProfile (colorProfile) {
    return AXIOS.post(`/colorprofile`, colorProfile)
  },
  deleteColorProfile (colorProfile) {
    return AXIOS.delete(`/colorprofile/${colorProfile.id}`)
  }
}
