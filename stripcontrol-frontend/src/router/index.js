import Vue from 'vue'
import VueRouter from 'vue-router'
import MainPage from '@/components/MainPage'
import ColorProfileService from '@/components/ColorProfileService'
import LedStripService from '@/components/LedStripService'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'MainPage',
    component: MainPage
  },
  { path: '/colorprofileservice', component: ColorProfileService },
  { path: '/ledstripservice', component: LedStripService }
]

const router = new VueRouter({
  routes
})

export default router
