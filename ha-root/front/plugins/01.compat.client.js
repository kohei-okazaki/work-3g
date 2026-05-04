import axios from "axios";
import { reactive, watch } from "vue";

const storageKey = "vuex";

const defaultAuth = () => ({
  seq_login_id: "",
  token: "",
  roles: [],
});

const loadAuth = () => {
  try {
    const persisted = JSON.parse(localStorage.getItem(storageKey) || "{}");
    return {
      ...defaultAuth(),
      ...(persisted.auth || persisted),
    };
  } catch {
    return defaultAuth();
  }
};

export default defineNuxtPlugin((nuxtApp) => {
  const config = useRuntimeConfig();
  const authState = useState("auth", () => loadAuth());
  const state = reactive({
    auth: authState.value,
  });

  const persist = () => {
    localStorage.setItem(storageKey, JSON.stringify({ auth: state.auth }));
  };

  const setAuth = (nextAuth) => {
    state.auth = {
      ...defaultAuth(),
      ...nextAuth,
    };
    authState.value = state.auth;
    persist();
  };

  watch(
    authState,
    (value) => {
      state.auth = {
        ...defaultAuth(),
        ...value,
      };
      persist();
    },
    { deep: true },
  );

  const store = {
    state,
    commit(type, payload = {}) {
      switch (type) {
        case "auth/clearToken":
          setAuth({
            ...state.auth,
            seq_login_id: "",
            token: "",
          });
          break;
        case "auth/setToken":
          setAuth({
            ...state.auth,
            seq_login_id: payload.seq_login_id || "",
            token: payload.token || "",
          });
          break;
        case "auth/setUserData":
          setAuth({
            ...state.auth,
            roles: payload.roles || [],
          });
          break;
        default:
          throw new Error(`Unknown mutation: ${type}`);
      }
    },
    dispatch() {
      return Promise.resolve();
    },
  };

  const apiClient = axios.create({
    baseURL: config.public.apiBaseURL,
  });

  const auth = {
    loginWith(strategy, options = {}) {
      if (strategy !== "local") {
        return Promise.reject(
          new Error(`Unsupported auth strategy: ${strategy}`),
        );
      }
      return apiClient.post("login", options.data || {});
    },
    logout() {
      store.commit("auth/clearToken");
      return navigateTo("/login");
    },
  };

  const themeState = useState("themeDark", () => {
    return localStorage.getItem("themeDark") === "true";
  });

  const getVuetifyTheme = () => nuxtApp.$vuetifyInstance?.theme;

  const isDarkTheme = () => {
    const theme = getVuetifyTheme();
    return Boolean(
      theme?.current?.value?.dark ??
        theme?.global?.current?.value?.dark ??
        theme?.current?.dark ??
        theme?.global?.current?.dark ??
        themeState.value,
    );
  };

  const setTheme = (isDark) => {
    themeState.value = Boolean(isDark);
    localStorage.setItem("themeDark", String(themeState.value));

    const theme = getVuetifyTheme();
    if (theme?.change) {
      theme.change(themeState.value ? "dark" : "light");
    } else if (theme?.global?.name) {
      theme.global.name.value = themeState.value ? "dark" : "light";
    }
  };

  const vuetifyCompat = {
    theme: {
      get dark() {
        return isDarkTheme();
      },
      set dark(value) {
        setTheme(value);
      },
    },
    goTo(target) {
      const top = Number(target) || 0;
      window.scrollTo({ top, behavior: "smooth" });
    },
  };

  setTheme(themeState.value);

  nuxtApp.vueApp.config.globalProperties.$store = store;
  nuxtApp.vueApp.config.globalProperties.$auth = auth;
  nuxtApp.vueApp.config.globalProperties.$axios = apiClient;
  nuxtApp.vueApp.config.globalProperties.$vuetify = vuetifyCompat;
  nuxtApp.vueApp.config.globalProperties.$isDarkTheme = isDarkTheme;
  nuxtApp.vueApp.config.globalProperties.$setDarkTheme = setTheme;
  nuxtApp.vueApp.config.globalProperties.$isValidForm = async (formRef) => {
    if (!formRef?.validate) {
      return true;
    }

    const result = await formRef.validate();
    return result === true || result?.valid === true;
  };

  return {
    provide: {
      store,
      auth,
      axios: apiClient,
      vuetify: vuetifyCompat,
      isDarkTheme,
      setDarkTheme: setTheme,
    },
  };
});
