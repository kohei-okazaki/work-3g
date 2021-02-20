<template>
  <v-card class="mt-4 mx-auto" max-width="400">
    <v-sheet
      class="v-sheet--offset mx-auto"
      :color="color"
      elevation="12"
      max-width="calc(100% - 32px)"
    >
      <v-sparkline
        :labels="labels"
        :value="values"
        color="white"
        line-width="2"
        padding="16"
        smooth
      >
        <template v-slot:label="item">
          {{
            new Date(item.value).getMonth() +
            1 +
            "/" +
            new Date(item.value).getDate()
          }}
        </template>
      </v-sparkline>
    </v-sheet>

    <v-card-text class="pt-0">
      <div class="title font-weight-light mb-2">{{ title }}</div>
      <div class="subheading font-weight-light grey--text">
        {{ text }}
      </div>
      <v-divider class="my-2"></v-divider>
      <div class="pushable" @click="getGraph">
        <v-icon class="mr-2" small>mdi-reload</v-icon>
        <span class="caption grey--text font-weight-light"
          >最新の登録情報を取得</span
        >
      </div>
    </v-card-text>
  </v-card>
</template>

<script>
export default {
  props: {
    error: Object,
    labels: Array,
    values: Array,
    title: String,
    text: String,
    color: String,
  },
  methods: {
    getGraph: function () {
      this.$emit("get-graph");
    },
  },
};
</script>

<style scoped>
.v-sheet--offset {
  top: -24px;
  position: relative;
}
</style>