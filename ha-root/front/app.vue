<script setup>
import { computed } from "vue";

const SITE_TITLE = "健康管理サイト";
const PAGE_TITLES = [
  { title: "Top", test: (path) => path === "/" },
  { title: "アカウント情報一覧", test: (path) => path === "/account" },
  { title: "API通信ログ一覧", test: (path) => path === "/apidata" },
  { title: "バッチ実行ログ一覧", test: (path) => path === "/job" },
  { title: "健康情報一覧", test: (path) => path === "/healthinfo" },
  { title: "お知らせ一覧", test: (path) => path === "/news" },
  { title: "メモ一覧", test: (path) => path === "/note" },
  { title: "問い合わせ一覧", test: (path) => path === "/inquiry" },
  { title: "ログイン", test: (path) => path === "/login" },
  { title: "管理ユーザ作成", test: (path) => path === "/user/entry" },
  { title: "ユーザ編集", test: (path) => path.startsWith("/user/edit/") },
];

const route = useRoute();

const normalizePath = (path) => {
  const normalizedPath = path.replace(/\/$/, "");
  return normalizedPath === "" ? "/" : normalizedPath;
};

const pageTitle = computed(() => {
  const currentPath = normalizePath(route.path);
  return PAGE_TITLES.find(({ test }) => test(currentPath))?.title || "Top";
});

useHead({
  title: computed(() => `${SITE_TITLE} | ${pageTitle.value}`),
});
</script>

<template>
  <NuxtLayout>
    <NuxtPage />
  </NuxtLayout>
</template>
