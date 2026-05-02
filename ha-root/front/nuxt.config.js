const nodeEnvironment = process.env.NODE_ENV;
// APP_ENV はアプリの環境名です。指定値は local / dev を想定します。
// NODE_ENV=production は Nuxt/Vite のビルドモードであり、アプリの環境名ではありません。
const environment =
  process.env.APP_ENV ||
  (["local", "dev"].includes(nodeEnvironment)
    ? nodeEnvironment
    : "local");
const envSet = require(`./env.${environment}.js`);
const apiBaseURL = envSet.api_base_url || "http://localhost:8082/api/root/";

export default defineNuxtConfig({
  compatibilityDate: "2026-04-28",

  ssr: false,

  devServer: {
    host: process.env.HOST || "0.0.0.0",
    port: process.env.PORT ? Number(process.env.PORT) : 8083,
  },

  app: {
    head: {
      titleTemplate: "%s | front",
      title: "front",
      meta: [
        {
          charset: "utf-8",
        },
        {
          name: "viewport",
          content: "width=device-width, initial-scale=1",
        },
        {
          name: "description",
          content: "",
        },
      ],
      link: [
        {
          rel: "icon",
          type: "image/x-icon",
          href: "/favicon.ico",
        },
      ],
    },
  },

  css: [
    "~/assets/baseLayout.css",
    "vuetify/styles",
    "@mdi/font/css/materialdesignicons.css",
  ],

  components: true,

  experimental: {
    payloadExtraction: true,
  },

  runtimeConfig: {
    public: {
      apiBaseURL,
      env: envSet,
    },
  },

  vite: {
    optimizeDeps: {
      include: [
        "@vue/devtools-core",
        "@vue/devtools-kit",
        "axios",
        "vuetify",
        "vuetify/components",
        "vuetify/directives",
        "vuetify/iconsets/mdi",
      ],
    },
    define: {
      "process.env.api_base_url": JSON.stringify(apiBaseURL),
    },
    ssr: {
      noExternal: ["vuetify"],
    },
  },

  nitro: {
    prerender: {
      routes: ["/"],
    },
  },
});
