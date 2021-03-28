<template>
  <div>
    <transition name="fade">
      <v-timeline align-top dense v-if="timelines.length != 0">
        <v-timeline-item
          small
          v-for="(timeline, i) in timelines"
          :key="i"
          :color="color"
        >
          <v-card max-width="650" :color="color">
            <v-card-title>{{ timeline.name }}</v-card-title>
            <v-divider />
            <v-card-text class="cardBody">
              <div>起動時刻={{ timeline.date }}</div>
            </v-card-text>
          </v-card>
        </v-timeline-item>
      </v-timeline></transition
    >
  </div>
</template>

<script>
export default {
  props: {
    timelines: Array,
    color: String,
  },
};
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: 0.6s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
  transform: scale(0);
}
.cardBody {
  background-color: azure;
}
</style>