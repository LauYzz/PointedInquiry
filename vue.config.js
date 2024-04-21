module.exports = {
  publicPath : process.env.NODE_ENV === 'production' ? './' : '/',  // 根路径
  pluginOptions : {
    'style-resources-loader' : {preProcessor : 'less', patterns : []}
  },
  pwa : {
    workboxPluginMode : 'GenerateSW',
    workboxOptions : {}
    // workboxOptions: {
    //   //
    //   https://developers.google.com/web/tools/workbox/modules/workbox-webpack-plugin
    //   skipWaiting: true,
    //   clientsClaim: true,
    //   navigateFallback: '/',
    //   navigateFallbackAllowlist: [/\/api\//]
    // }
  }
}
