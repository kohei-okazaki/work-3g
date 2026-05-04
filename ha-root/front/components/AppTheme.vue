<template>
  <v-switch
    v-model="theme"
    class="theme-position"
    color="primary"
    density="compact"
    hide-details
    :prepend-icon="themeIcon"
    aria-label="theme"
  ></v-switch>
</template>

<script>
export default {
  data: function () {
    return {
      theme: false,
    };
  },
  computed: {
    themeIcon: function () {
      return this.theme ? "mdi-weather-night" : "mdi-weather-sunny";
    },
  },
  watch: {
    theme: function (value) {
      this.$setDarkTheme(value);
    },
  },
  created: function () {
    this.theme = this.$isDarkTheme();
  },
};
</script>

<style scoped>
.theme-position {
  align-self: center;
  display: flex;
  align-items: center;
  margin: 0 8px;
  padding: 0;
}
</style>
