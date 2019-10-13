export default {
  hexToRgb (hex) {
    // Expand shorthand form (e.g. "03F") to full form (e.g. "0033FF")
    var shorthandRegex = /^#?([a-f\d])([a-f\d])([a-f\d])$/i
    hex = hex.replace(shorthandRegex, function (m, r, g, b) {
      return r + r + g + g + b + b
    })

    var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex)
    return result ? {
      r: parseInt(result[1], 16),
      g: parseInt(result[2], 16),
      b: parseInt(result[3], 16)
    } : null
  },
  rgbToHex (r, g, b) {
    if (typeof r === 'undefined') {
      r = 0
    }
    if (typeof g === 'undefined') {
      g = 0
    }
    if (typeof b === 'undefined') {
      b = 0
    }
    console.log('rgb2Hex: r:' + r + ' g:' + g + ' b:' + b)
    var rgb = b | (g << 8) | (r << 16)
    return '#' + (0x1000000 + rgb).toString(16).slice(1)
  },
  rgbToHex2 (colorProfile) {
    if (typeof colorProfile.red === 'undefined') {
      colorProfile.red = 0
    }
    if (typeof colorProfile.green === 'undefined') {
      colorProfile.green = 0
    }
    if (typeof colorProfile.blue === 'undefined') {
      colorProfile.blue = 0
    }
    var rgb = colorProfile.blue | (colorProfile.green << 8) | (colorProfile.red << 16)
    return '#' + (0x1000000 + rgb).toString(16).slice(1)
  }
}
