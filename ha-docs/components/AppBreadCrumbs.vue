<template>
  <v-breadcrumbs
    class="app-breadcrumbs"
    divider="/"
    density="comfortable"
    :items="normalizedItems"
  />
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

<style scoped>
.app-breadcrumbs {
  color: #005c64;
}

.app-breadcrumbs :deep(.v-breadcrumbs-item),
.app-breadcrumbs :deep(.v-breadcrumbs-item--link) {
  color: #005c64;
  opacity: 1;
}

.app-breadcrumbs :deep(.v-breadcrumbs-item--link) {
  font-weight: 500;
}

.app-breadcrumbs :deep(.v-breadcrumbs-divider),
.app-breadcrumbs :deep(.v-breadcrumbs-item--disabled) {
  color: #455a64;
  opacity: 1;
}
</style>
