<template>
  <transition name="fade">
    <v-btn
      v-show="fab"
      class="app-scroll-button"
      @click="toTop"
      color="primary"
      icon
      elevation="6"
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
      window.scrollTo({
        top: 0,
        behavior: "smooth",
      });
    },
    onScroll: function () {
      if (typeof window === "undefined") {
        return;
      }
      const top = window.pageYOffset || document.documentElement.scrollTop || 0;
      this.fab = top > 100;
    },
  },
  mounted: function () {
    this.onScroll();
    window.addEventListener("scroll", this.onScroll, { passive: true });
  },
  beforeUnmount: function () {
    window.removeEventListener("scroll", this.onScroll);
  },
};
</script>

<style scoped>
.app-scroll-button {
  position: fixed;
  right: 16px;
  bottom: 16px;
  z-index: 1000;
}

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
