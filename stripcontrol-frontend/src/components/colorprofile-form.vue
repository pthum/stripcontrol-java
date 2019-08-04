<template>
  <div class="color-profile">
    <b-container fluid>{{logSelectr()}}
      <form v-on:submit.prevent="saveEntry">
          <b-row class="my-1" >
              <b-col sm="3"><label>red</label></b-col>
              <b-col sm="9"><b-form-input id="redValue" type="number" v-model="colorprofile.red" @input="handleColorRGB" /></b-col>
          </b-row>
          <b-row class="my-1">
              <b-col sm="3"><label>green</label></b-col>
              <b-col sm="9"><b-form-input id="greenValue" type="number" v-model="colorprofile.green" @input="handleColorRGB" /></b-col>
          </b-row>
          <b-row class="my-1">
              <b-col sm="3"><label>blue</label></b-col>
              <b-col sm="9"><b-form-input id="blueValue"  type="number" v-model="colorprofile.blue" @input="handleColorRGB" /></b-col>
          </b-row>
          <b-row class="my-1">
              <b-col sm="3"><label>brightness</label></b-col>
              <b-col sm="9"><b-form-input id="brightnessValue" type="number" v-model="colorprofile.brightness" /></b-col>
          </b-row>
          <b-row class="my-1">
              <b-col sm="3"><label>color</label></b-col>
              <b-col sm="9"><b-form-input id="colorValue" v-model="ctest" type="color" @input="handleColorHex"/></b-col>
          </b-row>
          <b-button block variant="success" type="submit">Edit {{colorprofile.id}}</b-button>
      </form>
    </b-container>
  </div>
</template>

<script>
import api from './backend-api'
import colorhelper from './colorhelper'
export default {
  name: 'color-form',
  props: [ 'selectr' ],
  data () {
    return {
      errors: [],
      colorprofile: this.selectr,
      ctest: colorhelper.rgbToHex(this.selectr.red, this.selectr.green, this.selectr.blue)
    }
  },
  methods: {
    logSelectr () {
      console.log(this.selectr)
    },
    handleColorRGB () {
      var result = colorhelper.rgbToHex(this.colorprofile.red, this.colorprofile.green, this.colorprofile.blue)
      console.log('rgb2hex, oldValue: ' + this.ctest + ' newValue: ' + result + ' with: r=' + this.colorprofile.red + ' g=' + this.colorprofile.green + ' b=' + this.selectr.blue)
      this.ctest = result
    },
    handleColorHex () {
      var result = colorhelper.hexToRgb(this.ctest)
      this.colorprofile.red = result.r
      this.colorprofile.green = result.g
      this.colorprofile.blue = result.b
      console.log('hex: ' + this.ctest)
    },
    saveEntry () {
      if (typeof this.colorprofile.id !== 'undefined') {
        console.log('update entry with id ' + this.colorprofile.id)
        this.updateEntry()
      } else {
        console.log('create entry with id ' + this.colorprofile.id)
        this.createEntry()
      }
    },
    updateEntry () {
      console.log('updating entry' + this.colorprofile)
      api.putColorProfile(this.colorprofile).then(response => {
        console.log('successfully updated entry')
        this.callGetColorProfiles()
      }).catch(error => {
        this.errors.push(error)
      })
    },
    createEntry () {
      console.log('creating entry' + this.colorprofile)
      api.postColorProfile(this.colorprofile).then(response => {
        console.log('successfully created entry')
        this.callGetColorProfiles()
      }).catch(error => {
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
