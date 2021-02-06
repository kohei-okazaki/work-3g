<template>
  <v-select
    :items="tag_color_select_list"
    label="タグ色"
    item-text="label"
    item-value="color"
    return-object
    single-line
    :value="init"
    @change="updateValue"
  ></v-select>
</template>

<script>
export default {
  data: function () {
    return {
      tag_color_select_list: [
        {
          color: "blue",
          label: "青色",
        },
        {
          color: "yellow",
          label: "黄色",
        },
        {
          color: "red",
          label: "赤色",
        },
      ],
    };
  },
  props: {
    color: { type: String, required: false },
  },
  computed: {
    init: function () {
      return this.getTag(this.color);
    },
  },
  methods: {
    updateValue: function (e) {
      console.log("変更後色=" + e.color);
      this.$emit("input", e.color);
    },
    getTag: function (color) {
      for (var i = 0; i < this.tag_color_select_list.length; i++) {
        let tag = this.tag_color_select_list[i];
        if (tag.color == color) {
          return tag;
        }
      }
      return this.tag_color_select_list[0];
    },
  },
  mounted: function () {
    this.$emit("input", this.tag_color_select_list[0].color);
  },
};
</script>

<style scoped>
</style>