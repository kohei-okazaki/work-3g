<script setup>
import { ref } from "vue";

defineProps({
  items: {
    type: Array,
    default: () => [],
  },
  title: {
    type: String,
    default: "ha-docs",
  },
});

const drawer = ref(true);
const rail = ref(false);
</script>

<template>
  <v-app>
    <v-navigation-drawer v-model="drawer" :rail="rail" order="1">
      <v-list nav density="compact">
        <v-list-item
          v-for="item in items"
          :key="item.to"
          :to="item.to"
          link
        >
          <template #prepend>
            <v-icon>{{ item.icon }}</v-icon>
          </template>
          <v-list-item-title class="text-subtitle-2">
            {{ item.title }}
          </v-list-item-title>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar>
      <v-app-bar-nav-icon @click.stop="drawer = !drawer" />
      <v-btn icon variant="text" @click.stop="rail = !rail">
        <v-icon>
          mdi-{{ `chevron-${rail ? "right" : "left"}` }}
        </v-icon>
      </v-btn>
      <v-toolbar-title>{{ title }}</v-toolbar-title>
      <v-spacer />
    </v-app-bar>

    <v-main class="base">
      <div class="app-main-content">
        <v-container class="app-page-content">
          <slot />
        </v-container>

        <v-footer class="app-footer">
          <span>&copy; {{ new Date().getFullYear() }}</span>
        </v-footer>
      </div>
    </v-main>

    <AppScroll />
  </v-app>
</template>

<style scoped>
.app-main-content {
  display: flex;
  flex-direction: column;
  min-height: 100%;
}

.app-page-content {
  flex: 1 0 auto;
}

.app-footer {
  flex: 0 0 auto;
}
</style>
