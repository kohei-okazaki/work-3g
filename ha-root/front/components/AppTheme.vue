<template>
  <v-switch
    v-model="theme"
    class="theme-postion"
    :prepend-icon="themeIcon"
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
    theme: function () {
      this.$vuetify.theme.dark = this.theme;
    },
  },
};
</script>

<style scoped>
.theme-postion {
  margin-top: 16px;
  padding-top: 4px;
}
</style> 