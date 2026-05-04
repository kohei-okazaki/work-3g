import { defineNuxtConfig } from "nuxt/config";

export default defineNuxtConfig({
  compatibilityDate: "2026-04-28",
  srcDir: ".",
  dir: {
    assets: "assets",
    layouts: "layouts",
    middleware: "middleware",
    pages: "pages",
    plugins: "plugins",
    public: "static",
  },
  ssr: false,
  css: ["~/assets/baseLayout.css"],
  modules: ["vuetify-nuxt-module"],
  app: {
    head: {
      title: "健康管理ドキュメント | TOPページ",
      htmlAttrs: {
        lang: "ja",
      },
      meta: [
        { charset: "utf-8" },
        {
          name: "viewport",
          content: "width=device-width, initial-scale=1",
        },
        {
          name: "description",
          content: "Health info application docs",
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
  vuetify: {
    moduleOptions: {
      styles: {
        configFile: "assets/variables.scss",
      },
    },
    vuetifyOptions: {
      theme: {
        defaultTheme: "light",
        themes: {
          light: {
            colors: {
              primary: "#0097a7",
              accent: "#7b1fa2",
              secondary: "#ff8f00",
              info: "#26c6da",
              warning: "#f9a825",
              error: "#d84315",
              success: "#2e7d32",
            },
          },
          dark: {
            colors: {
              primary: "#0097a7",
              accent: "#7b1fa2",
              secondary: "#ff8f00",
              info: "#0097a7",
              warning: "#f9a825",
              error: "#d84315",
              success: "#2e7d32",
            },
          },
        },
      },
      icons: {
        defaultSet: "mdi",
      },
    },
  },
});
