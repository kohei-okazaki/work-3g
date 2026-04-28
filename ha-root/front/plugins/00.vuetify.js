import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
import { aliases, mdi } from "vuetify/iconsets/mdi";

const themeColors = {
  primary: "#0097A7",
  accent: "#8E24AA",
  secondary: "#FF8F00",
  info: "#26C6DA",
  warning: "#F9A825",
  error: "#E64A19",
  success: "#388E3C",
};

export default defineNuxtPlugin((nuxtApp) => {
  const vuetify = createVuetify({
    components,
    directives,
    icons: {
      defaultSet: "mdi",
      aliases,
      sets: {
        mdi,
      },
    },
    theme: {
      defaultTheme: "light",
      themes: {
        light: {
          dark: false,
          colors: themeColors,
        },
        dark: {
          dark: true,
          colors: {
            ...themeColors,
            info: "#0097A7",
          },
        },
      },
    },
  });

  nuxtApp.vueApp.use(vuetify);
  nuxtApp.provide("vuetifyInstance", vuetify);
});
