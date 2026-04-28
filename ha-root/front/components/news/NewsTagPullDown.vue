<template>
  <v-select
    :items="tagColorSelectList"
    label="タグ色"
    item-title="label"
    item-value="color"
    return-object
    single-line
    :model-value="init"
    :rules="[required]"
    @update:model-value="updateValue"
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
    modelValue: { type: String, required: false },
  },
  computed: {
    init: function () {
      return this.getTag(this.modelValue || this.color);
    },
  },
  methods: {
    updateValue: function (e) {
      this.$emit("update:modelValue", e.color);
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
    if (!this.modelValue && this.color) {
      this.$emit("update:modelValue", this.color);
    }
  },
};
</script>

<style scoped></style>
