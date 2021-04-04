import Vue from 'vue'
import App from './App.vue'
import router from './router'
import { store } from './store/store'
import { LayoutPlugin, ToastPlugin, NavbarPlugin, ButtonPlugin, ButtonGroupPlugin, ModalPlugin, FormPlugin, FormInputPlugin, TooltipPlugin } from 'bootstrap-vue'
import './assets/styles/customized.scss'
import { library } from '@fortawesome/fontawesome-svg-core'
import { faSync } from '@fortawesome/free-solid-svg-icons/faSync'
import { faTrash } from '@fortawesome/free-solid-svg-icons/faTrash'
import { faEdit } from '@fortawesome/free-solid-svg-icons/faEdit'
import { faPlusSquare } from '@fortawesome/free-solid-svg-icons/faPlusSquare'
import { faPalette } from '@fortawesome/free-solid-svg-icons/faPalette'
import { faLightbulb } from '@fortawesome/free-solid-svg-icons/faLightbulb'
import { faSun } from '@fortawesome/free-solid-svg-icons/faSun'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

library.add(faSync, faTrash, faEdit, faPlusSquare, faPalette, faLightbulb, faSun)

Vue.component('font-awesome-icon', FontAwesomeIcon)
Vue.config.productionTip = false

// Bootstrap
Vue.use(NavbarPlugin)
Vue.use(ToastPlugin)
Vue.use(LayoutPlugin)
Vue.use(ButtonPlugin)
Vue.use(ButtonGroupPlugin)
Vue.use(ModalPlugin)
Vue.use(FormPlugin)
Vue.use(FormInputPlugin)
Vue.use(TooltipPlugin)

/* eslint-disable no-new */
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
