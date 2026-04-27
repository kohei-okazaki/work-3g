import { state as createDbState } from "~/store/db";

export default defineNuxtPlugin((nuxtApp) => {
  const store = {
    state: {
      db: createDbState(),
    },
  };

  nuxtApp.vueApp.config.globalProperties.$store = store;

  return {
    provide: {
      store,
    },
  };
});
