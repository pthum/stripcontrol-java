import Vue from 'vue'
import Router from 'vue-router'
import MainPage from '@/components/MainPage'
import ColorProfileService from '@/components/ColorProfileService'
import LedStripService from '@/components/LedStripService'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'MainPage',
      component: MainPage
    },
    { path: '/colorprofileservice', component: ColorProfileService },
    { path: '/ledstripservice', component: LedStripService }
  ]
})
