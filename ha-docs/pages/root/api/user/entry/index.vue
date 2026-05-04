<template>
  <div>
    <v-row justify="center">
      <v-col sm="12">
        <AppBreadCrumbs :items="breadcrumbs" />
      </v-col>
    </v-row>
    <v-row justify="center">
      <v-col sm="12">
        <AppContentsTitle :title="breadcrumbs[breadcrumbs.length - 1].text" />
      </v-col>
    </v-row>
    <v-row justify="center">
      <v-col sm="12">
        <AppDocs :flow="flow" />
      </v-col>
    </v-row>
  </div>
</template>

<script>
import AppBreadCrumbs from "~/components/AppBreadCrumbs.vue";
import AppContentsTitle from "~/components/AppContentsTitle.vue";
import AppDocs from "~/components/AppDocs.vue";

export default {
  // RootAPIのレイアウトを適用
  layout: "rootApiLayout",
  components: {
    AppBreadCrumbs,
    AppContentsTitle,
    AppDocs,
  },
  data: function () {
    return {
      breadcrumbs: [
        {
          text: "Top",
          disabled: false,
          href: "/",
        },
        {
          text: "管理者サイト用API",
          disabled: false,
          href: "/root/api",
        },
        {
          text: "管理ユーザ作成API",
          disabled: true,
          href: "/root/api/user/entry",
        },
      ],
      flow: [
        {
          id: "1",
          text: "リクエスト受付",
          edgeType: "round",
          next: ["2"],
        },
        {
          id: "2",
          text: "リクエスト妥当性チェック",
          edgeType: "round",
          next: ["3", "101"],
        },
        {
          id: "3",
          text: "パスワード一致チェック",
          edgeType: "round",
          next: ["4", "101"],
        },
        {
          id: "4",
          text: "トランザクション開始",
          edgeType: "round",
          next: ["5"],
        },
        {
          id: "5",
          text: "<b>照会権限</b>のROOT_ROLE_MT 検索",
          edgeType: "round",
          next: ["6", "101"],
          group: "トランザクション",
          style: "fill:#c6ffc6",
        },
        {
          id: "6",
          text: "ROOT_USER_ROLE_MNG_MT 登録",
          edgeType: "round",
          next: ["7", "101"],
          group: "トランザクション",
          style: "fill:#c6ffc6",
        },
        {
          id: "7",
          text: "ROOT_USER_ROLE_DETAIL_MT 登録",
          edgeType: "round",
          next: ["8", "101"],
          group: "トランザクション",
          style: "fill:#c6ffc6",
        },
        {
          id: "8",
          text: "パスワードハッシュ化<br>有効期限設定",
          edgeType: "round",
          next: ["9", "101"],
          group: "トランザクション",
        },
        {
          id: "9",
          text: "ROOT_LOGIN_INFO 登録",
          edgeType: "round",
          next: ["10", "101"],
          group: "トランザクション",
          style: "fill:#c6ffc6",
        },
        {
          id: "10",
          text: "コミット",
          edgeType: "round",
          next: ["100"],
          group: "トランザクション",
        },
        {
          id: "100",
          text: "正常系レスポンスJSON 生成",
          edgeType: "round",
          next: ["110"],
        },
        {
          id: "101",
          text: "異常系レスポンスJSON 生成",
          edgeType: "round",
          next: ["110"],
        },
        {
          id: "110",
          text: "レスポンス返却",
          edgeType: "round",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>
