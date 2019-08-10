<template>
  <div class="color-profile">
    <b-container fluid>
      <form v-on:submit.prevent="saveEntry">
          <b-row class="my-1" >
              <b-col sm="3"><label>red</label></b-col>
              <b-col sm="9"><b-form-input id="redValue" type="number" :value="red" @input="handleColorRGB" /></b-col>
          </b-row>
          <b-row class="my-1">
              <b-col sm="3"><label>green</label></b-col>
              <b-col sm="9"><b-form-input id="greenValue" type="number" :value="green" @input="handleColorRGB" /></b-col>
          </b-row>
          <b-row class="my-1">
              <b-col sm="3"><label>blue</label></b-col>
              <b-col sm="9"><b-form-input id="blueValue"  type="number" :value="blue" @input="handleColorRGB" /></b-col>
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
      // colorprofile: this.$store.getters.findColorProfile(this.formProfileName),
      ctest: colorhelper.rgbToHex2(this.$store.getters.findColorProfile(this.formProfileName))
    }
  },
  methods: {
    logSelectr () {
      console.log(this.formProfileName)
      // console.log(this.colorprofile)
    },
    handleColorRGB () {
      var result = colorhelper.rgbToHex(this.red, this.green, this.blue)
      console.log('rgb2hex, oldValue: ' + this.ctest + ' newValue: ' + result + ' with: r=' + this.red + ' g=' + this.green + ' b=' + this.blue)
      this.ctest = result
      this.$store.commit('updateEditableColorProfile', {type: this.formProfileName, object: this.colorprofile})
    },
    handleColorHex () {
      var result = colorhelper.hexToRgb(this.ctest)
      var obj = { red: result.r, green: result.g, blue: result.b, brightness: this.brightness, id: this.id }
      console.log('hex: ' + this.ctest)
      this.$store.commit('updateEditableColorProfile', {type: this.formProfileName, object: obj})
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
      }).catch(error => {
        console.log(error)
        this.errors.push(error)
      })
    },
    createEntry () {
      var obj = { red: this.red, green: this.green, blue: this.blue, brightness: this.brightness, id: this.id }
      console.log('creating entry' + obj.id)
      api.postColorProfile(obj).then(response => {
        this.callGetColorProfiles()
      }).catch(error => {
        console.log(error)
        this.errors.push(error)
      })
    },
    callGetColorProfiles () {
      EventBus.$emit('CPupdate', {})
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
