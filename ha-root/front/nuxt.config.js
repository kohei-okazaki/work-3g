import colors from 'vuetify/es5/util/colors'

const environment = process.env.NODE_ENV || 'local';
const envSet = require(`./env.${environment}.js`);

export default {

  // 環境毎の設定ファイルを読込
  env: envSet,

  ssr: false,

  server: {
    host: process.env.HOST || '0.0.0.0',
    port: process.env.PORT ? Number(process.env.PORT) : 8083
  },

  head: {
    titleTemplate: '%s | front',
    title: '管理サイト',
    meta: [{
      charset: 'utf-8'
    },
    {
      name: 'viewport',
      content: 'width=device-width, initial-scale=1'
    },
    {
      hid: 'description',
      name: 'description',
      content: ''
    }
    ],
    link: [{
      rel: 'icon',
      type: 'image/x-icon',
      href: '/favicon.ico'
    }]
  },

  css: ['~/assets/baseLayout.css'],

  plugins: [],

  components: true,

  buildModules: [
    // UIフレームワーク
    '@nuxtjs/vuetify',
  ],

  modules: [
    // API通信を可能にするライブラリ
    '@nuxtjs/axios',
    '@nuxtjs/proxy',
    // ログイン認証を可能にするライブラリ
    '@nuxtjs/auth',
    // basic認証を有効化するライブラリ
    'nuxt-basic-auth-module'
  ],

  axios: {
    // TODO URLの環境ごとの切り替えできない
    // baseURL: process.env["api_base_url"]
    // baseURL: process.env.api_base_url
    baseURL: process.env.api_base_url || 'http://localhost:8082/api/root/'
  },

  basic: {
    name: 'hoge',
    pass: 'pass'
  },

  auth: {
    redirect: {
      login: '/login',
      logout: '/login',
      callback: false,
      home: '/'
    },
    strategies: {
      local: {
        endpoints: {
          login: {
            url: 'login',
            method: 'post',
            propertyName: 'token'
          },
          logout: false,
          user: false,
        }
      }
    }
  },

  router: {
    middleware: ['auth']
  },

  vuetify: {
    customVariables: ['~/assets/variables.scss'],
    theme: {
      dark: false,
      themes: {
        dark: {
          primary: colors.cyan.darken2,
          accent: colors.purple.darken1,
          secondary: colors.amber.darken3,
          info: colors.cyan.darken2,
          warning: colors.yellow.darken3,
          error: colors.deepOrange.darken3,
          success: colors.green.darken3
        },
        light: {
          primary: colors.cyan.darken2,
          accent: colors.purple.darken1,
          secondary: colors.amber.darken3,
          info: colors.cyan.lighten1,
          warning: colors.yellow.darken3,
          error: colors.deepOrange.darken3,
          success: colors.green.darken3
        }
      }
    }
  },

  build: {},

}
