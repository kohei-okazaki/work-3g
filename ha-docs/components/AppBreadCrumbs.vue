<template>
  <v-breadcrumbs divider="/" density="comfortable" :items="normalizedItems" />
</template>
<script>
export default {
  props: {
    items: {
      type: Array,
      default: () => [],
    },
  },
  computed: {
    normalizedItems: function () {
      return this.items.map(function (item) {
        return {
          ...item,
          title: item.title || item.text,
          href: item.disabled ? undefined : item.href,
        };
      });
    },
  },
};
</script>

<style scoped></style>
