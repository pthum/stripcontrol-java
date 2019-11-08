module.exports = {
  outputDir: 'target/dist',
  assetsDir: 'static',
  devServer: {
    host: 'localhost',
    port: 8090,
    proxy: 'http://localhost:8080'
  }
}
