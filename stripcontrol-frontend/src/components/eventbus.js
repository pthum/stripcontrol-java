import Vue from 'vue'
class EventBusClz extends Vue {
  /** makes a toast, expects an object with content field and variant field */
  makeToast (vm, toastData) {
    vm.$bvToast.toast(toastData.content, {
      title: ` ${toastData.variant || 'default'}`,
      variant: toastData.variant,
      solid: true
    })
  }
}
const EventBus = new EventBusClz()
export default EventBus
