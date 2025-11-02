import colors from 'vuetify/es5/util/colors'
// import fs from 'fs'
// import path from 'path'

export default {
  ssr: false,

  target: 'static',

  head: {
    title: '健康管理アプリdocs',
    htmlAttrs: {
      lang: 'ja'
    },
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

  plugins: [
    { src: '~/plugins/mermaid.client.js', mode: 'client' },
    { src: '~/plugins/vue-simple-context-menu', mode: 'client' }
  ],

  components: true,

  buildModules: [
    '@nuxtjs/vuetify',
  ],

  modules: [],

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

  build: {
    // 依存一式をBabel対象に
    transpile: ['mermaid', '@mermaid-js/parser', '@iconify/utils', 'uuid', 'marked'],
    postcss: {
      postcssOptions: {
        // 追加・上書きしたいプラグインだけを書けばOK（デフォルトは維持されます）
        plugins: {
          // 例: postcss-url を無効化したい場合
          'postcss-url': false,
        },
        // デフォルトの順序（preset-envとcssnanoを後段に）
        order: 'presetEnvAndCssnanoLast',
        // postcss-preset-env の設定（autoprefixer含む）
        preset: {
          stage: 2,
          features: {
            'nesting-rules': true,
          },
          autoprefixer: {
            // IEグリッド対応が要らなければfalseでもOK
            grid: false,
          }
        }
      }
    },
    extend(config, ctx) {
      // 依存の .mjs / .js を確実に Babel に通す（そのまま残す）
      config.module.rules.push({
        test: /\.m?js$/,
        include: /node_modules\/(mermaid|@mermaid-js|@iconify|uuid|marked)/,
        use: { loader: 'babel-loader' },
        type: 'javascript/auto'
      })
      config.resolve = config.resolve || {}
      config.resolve.alias = {
        ...(config.resolve.alias || {}),
        // ESM を使う
        mermaid: 'mermaid/dist/mermaid.esm.mjs',
        // Webpack4の"exports"回避のため、parserの実体に直リンク
        '@mermaid-js/parser': '@mermaid-js/parser/dist/index.mjs'
      }
    },
  },

  babel: {
    plugins: [
      '@babel/plugin-proposal-optional-chaining',
      '@babel/plugin-proposal-nullish-coalescing-operator'
    ]
  }
}
