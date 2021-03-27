<template>
  <transition name="fade">
    <v-btn
      v-scroll="onScroll"
      v-show="fab"
      @click="toTop"
      fab
      fixed
      bottom
      right
      color="primary"
      ><v-icon>mdi-arrow-up-bold</v-icon></v-btn
    >
  </transition>
</template>

<script>
export default {
  data: function () {
    return {
      fab: false,
    };
  },
  methods: {
    toTop: function () {
      this.$vuetify.goTo(0);
    },
    onScroll: function (e) {
      if (typeof window === "undefined") {
        return;
      }
      const top = window.pageYOffset || e.target.scrollTop || 0;
      this.fab = top > 100;
    },
  },
};
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: 0.5s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
  transform: scale(0);
}
</style>