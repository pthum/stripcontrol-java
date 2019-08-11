<template>
  <div class="color-profile">
    <b-container fluid>
      <form v-on:submit.prevent="saveEntry">
          <b-row class="my-1" >
              <b-col sm="3"><label>red</label></b-col>
              <b-col sm="9"><b-form-input id="redValue" type="number" :value="red" @input="handleColorRed" /></b-col>
          </b-row>
          <b-row class="my-1">
              <b-col sm="3"><label>green</label></b-col>
              <b-col sm="9"><b-form-input id="greenValue" type="number" :value="green" @input="handleColorGreen" /></b-col>
          </b-row>
          <b-row class="my-1">
              <b-col sm="3"><label>blue</label></b-col>
              <b-col sm="9"><b-form-input id="blueValue"  type="number" :value="blue" @input="handleColorBlue" /></b-col>
          </b-row>
          <b-row class="my-1">
              <b-col sm="3"><label>brightness</label></b-col>
              <b-col sm="9"><b-form-input id="brightnessValue" type="number" :value="brightness" /></b-col>
          </b-row>
          <b-row class="my-1">
              <b-col sm="3"><label>color</label></b-col>
              <b-col sm="9"><b-form-input id="colorValue" v-model="ctest" type="color" @input="handleColorHex"/></b-col>
          </b-row>
          <b-button block variant="success" type="submit" v-if="typeof id !== 'undefined'">Edit {{id}}</b-button>
          <b-button block variant="success" type="submit" v-else>Create ColorProfile</b-button>
          <b-button block variant="danger" v-on:click="deleteEntry" v-if="typeof id !== 'undefined'">Delete {{id}}</b-button>
      </form>
    </b-container>
  </div>
</template>

<script>
import api from './backend-api'
import colorhelper from './colorhelper'
import EventBus from './eventbus'
export default {
  name: 'color-form',
  props: [ 'formProfileName' ],
  computed: {
    red () {
      return this.$store.getters.findColorProfile(this.formProfileName).red
    },
    green () {
      return this.$store.getters.findColorProfile(this.formProfileName).green
    },
    blue () {
      return this.$store.getters.findColorProfile(this.formProfileName).blue
    },
    brightness () {
      return this.$store.getters.findColorProfile(this.formProfileName).brightness
    },
    id () {
      return this.$store.getters.findColorProfile(this.formProfileName).id
    }
  },
  data () {
    return {
      errors: [],
      ctest: colorhelper.rgbToHex2(this.$store.getters.findColorProfile(this.formProfileName))
    }
  },
  methods: {
    commitColorProfileRGB (red, green, blue) {
      var obj = { red: red, green: green, blue: blue, brightness: this.brightness, id: this.id }
      this.$store.commit('updateColorProfile', {type: this.formProfileName, object: obj})
    },
    handleColorRed (e) {
      console.log(e)
      this.ctest = colorhelper.rgbToHex(e, this.green, this.blue)
      this.commitColorProfileRGB(e, this.green, this.blue)
    },
    handleColorGreen (e) {
      console.log(e)
      this.ctest = colorhelper.rgbToHex(this.red, e, this.blue)
      this.commitColorProfileRGB(this.red, e, this.blue)
    },
    handleColorBlue (e) {
      console.log(e)
      this.ctest = colorhelper.rgbToHex(this.red, this.green, e)
      this.commitColorProfileRGB(this.red, this.green, e)
    },
    handleColorHex () {
      var result = colorhelper.hexToRgb(this.ctest)
      console.log('hex: ' + this.ctest)
      this.commitColorProfileRGB(result.r, result.g, result.b)
    },
    saveEntry () {
      if (typeof this.id !== 'undefined') {
        console.log('update entry with id ' + this.id)
        this.updateEntry()
      } else {
        console.log('create entry with id ' + this.id)
        this.createEntry()
      }
    },
    updateEntry () {
      var obj = { red: this.red, green: this.green, blue: this.blue, brightness: this.brightness, id: this.id }
      console.log('updating entry' + obj.id)
      api.putColorProfile(obj).then(response => {
        this.callGetColorProfiles()
        this.makeSuccessNotification('Successfully updated color profile with id ' + this.id)
      }).catch(error => {
        this.makeErrorNotification(error)
        this.errors.push(error)
      })
    },
    createEntry () {
      var obj = { red: this.red, green: this.green, blue: this.blue, brightness: this.brightness, id: this.id }
      console.log('creating entry' + obj.id)
      api.postColorProfile(obj).then(response => {
        this.makeSuccessNotification('Successfully created color profile')
        this.callGetColorProfiles()
      }).catch(error => {
        this.makeErrorNotification(error)
        this.errors.push(error)
      })
    },
    makeSuccessNotification (text) {
      EventBus.$emit('MakeToast', {variant: 'success', content: text})
    },
    makeErrorNotification (error) {
      console.log(error)
      EventBus.$emit('MakeToast', {variant: 'danger', content: error.message})
    },
    callGetColorProfiles () {
      EventBus.$emit('CPupdate', {})
    },
    deleteEntry () {
      var obj = { id: this.id }
      console.log('deleting entry' + obj.id)
      api.deleteColorProfile(obj).then(response => {
        this.callGetColorProfiles()
        this.makeSuccessNotification('Deleted color profile with id ' + obj.id)
      }).catch(error => {
        this.makeErrorNotification(error)
        this.errors.push(error)
      })
    }
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
