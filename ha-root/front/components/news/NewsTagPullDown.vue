<template>
  <v-select
    :items="tagColorSelectList"
    label="タグ色"
    item-text="label"
    item-value="color"
    return-object
    single-line
    :value="init"
    :rules="[required]"
    @change="updateValue"
  ></v-select>
</template>

<script>
export default {
  data: function () {
    return {
      tagColorSelectList: [
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
      required: (value) => !!value || "必ず入力してください",
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
      this.$emit("input", e.color);
    },
    getTag: function (color) {
      for (var i = 0; i < this.tagColorSelectList.length; i++) {
        let tag = this.tagColorSelectList[i];
        if (tag.color == color) {
          return tag;
        }
      }
      return this.tagColorSelectList[0];
    },
  },
  mounted: function () {
    this.$emit("input", this.color);
  },
};
</script>

<style scoped>
</style>