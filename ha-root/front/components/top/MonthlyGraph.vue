<template>
  <v-card class="mt-4 mx-auto" max-width="400">
    <v-sheet
      class="v-sheet--offset mx-auto"
      :color="color"
      elevation="12"
      max-width="calc(100% - 32px)"
      rounded
    >
      <v-sparkline
        :labels="labels"
        :value="values"
        color="white"
        line-width="2"
        padding="16"
        smooth
        auto-draw
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
      <br />
      <v-row>
        <v-col align="left"
          ><v-btn text @click="subMonth"
            ><v-icon>mdi-arrow-left-circle</v-icon>前月</v-btn
          ></v-col
        >
        <v-col align="center">
          <div class="date">
            <b>{{ viewDate }}</b>
          </div>
        </v-col>
        <v-col align="right"
          ><v-btn text @click="addMonth"
            >翌月<v-icon>mdi-arrow-right-circle</v-icon></v-btn
          ></v-col
        >
      </v-row>
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
  data: function () {
    return {
      date: new Date(),
      viewDate: "",
    };
  },
  methods: {
    getGraph: function () {
      var y = this.date.getFullYear();
      var m = ("00" + (this.date.getMonth() + 1)).slice(-2);
      this.$emit("get-graph", y + m);
    },
    subMonth: function () {
      this.date.setMonth(this.date.getMonth() - 1);
      var y = this.date.getFullYear();
      var m = ("00" + (this.date.getMonth() + 1)).slice(-2);
      this.viewDate = y + "/" + m;
    },
    addMonth: function () {
      this.date.setMonth(this.date.getMonth() + 1);
      var y = this.date.getFullYear();
      var m = ("00" + (this.date.getMonth() + 1)).slice(-2);
      this.viewDate = y + "/" + m;
    },
  },
  computed: {
    titleDate: function () {
      var y = this.date.getFullYear();
      var m = ("00" + (this.date.getMonth() + 1)).slice(-2);
      return y + "/" + m;
    },
  },
  watch: {
    viewDate: {
      immediate: true,
      handler: function () {
        var y = this.date.getFullYear();
        var m = ("00" + (this.date.getMonth() + 1)).slice(-2);
        this.viewDate = y + "/" + m;
      },
    },
  },
};
</script>

<style scoped>
.v-sheet--offset {
  top: -24px;
  position: relative;
}
.date {
  margin-top: 7px;
}
</style>