const storageKey = "vuex";

const defaultAuth = () => ({
  seq_login_id: "",
  token: "",
  roles: [],
});

const loadAuth = () => {
  if (!process.client) {
    return defaultAuth();
  }

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

export default defineNuxtRouteMiddleware((to) => {
  if (
    to.meta.auth === false ||
    to.path === "/login" ||
    to.path === "/user/entry"
  ) {
    return;
  }

  const authState = useState("auth", () => loadAuth());
  if (!authState.value?.token) {
    authState.value = loadAuth();
  }

  if (!authState.value.token) {
    return navigateTo("/login");
  }
});
