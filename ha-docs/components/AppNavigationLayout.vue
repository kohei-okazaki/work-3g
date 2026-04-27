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
      <v-container>
        <slot />
      </v-container>
    </v-main>

    <AppScroll />

    <v-footer app>
      <span>&copy; {{ new Date().getFullYear() }}</span>
    </v-footer>
  </v-app>
</template>
