<template>
  <div>
    <AppBreadCrumbs :items="breadcrumbs" />
    <AppContentsTitle title="健康情報計算方法" />
    <v-row justify="center" align="center">
      <v-col cols="12" sm="8" md="9">
        <div class="text-center d-flex pb-4">
          <v-expansion-panels multiple>
            <v-expansion-panel v-for="(item, i) in list" :key="i">
              <v-expansion-panel-header class="text-subtitle-1">{{
                item.name
              }}</v-expansion-panel-header>
              <v-expansion-panel-content class="text-body-2">
                <div v-html="item.exp" align="left"></div
              ></v-expansion-panel-content>
            </v-expansion-panel>
          </v-expansion-panels>
        </div>
      </v-col>
    </v-row>
  </div>
</template>
<script>
import AppBreadCrumbs from "~/components/AppBreadCrumbs.vue";
import AppContentsTitle from "~/components/AppContentsTitle.vue";

export default {
  // Wikiのレイアウトを適用
  layout: "wikiLayout",
  components: {
    AppBreadCrumbs,
    AppContentsTitle,
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
          text: "Wiki",
          disabled: false,
          href: "/wiki",
        },
        {
          text: "健康情報計算方法",
          disabled: true,
          href: "/wiki/healthinfoCalc",
        },
      ],
      list: [
        {
          name: "BMI計算方法",
          exp: "体重(kg) / (身長(m) * 身長(m))",
        },
        {
          name: "標準体重計算方法",
          exp: "身長(m) * 身長(m) * 22",
        },
        {
          name: "基礎代謝量計算方法",
          exp:
            "<ol><li>男性の場合、13.397 * 体重(kg) + 4.799 * 身長(cm) - 5.677 * 年齢 + 88.362</li><li>女性の場合、9.247 * 体重(kg) + 3.098 * 身長(cm) - 4.33 * 年齢 + 447.593</li></ol>",
        },
        {
          name: "予測肺活量計算方法",
          exp:
            "<ol><li>男性の場合、(27.63 - 0.112 * 年齢) * 身長(cm)</li><li>女性の場合、(21.78 − 0.101 * 年齢) * 身長(cm)</li></ol>",
        },
      ],
    };
  },
};
</script>
<style scoped>
</style>