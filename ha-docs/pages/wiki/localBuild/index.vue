<template>
  <div>
    <v-row>
      <v-col class="text-center" sm="12">
        <AppBreadCrumbs :items="breadcrumbs" />
      </v-col>
    </v-row>
    <v-row>
      <v-col class="text-left" sm="12">
        <AppContentsTitle :title="breadcrumbs[breadcrumbs.length - 1].text" />
      </v-col>
    </v-row>

    <v-row align="center">
      <v-col sm="12">
        <v-alert text type="error" elevation="2" border="left">
            健康管理アプリはDocker環境に移行したため、本手順は不要。<br>
            備忘のために手順は残すが、基本的に参照しないこと。
        </v-alert>
      </v-col>
    </v-row>

    <v-row align="center">
      <v-col sm="12">
        <v-alert text type="info" elevation="2" border="left">
          <div class="text-subtitle-1">概要</div>
          <div class="text-body-2">ローカル環境のビルド手順書になります</div>
        </v-alert>
      </v-col>
    </v-row>

    <v-row align="center">
      <v-col sm="12">
        <v-expansion-panels multiple>
          <v-expansion-panel>
            <v-expansion-panel-header
              class="justify-self-start text-subtitle-1"
              disable-icon-rotate
            >
              <div>バッチ ビルド手順</div>
            </v-expansion-panel-header>
            <v-expansion-panel-content class="text-body-2">
              <ol>
                <li>コマンドプロンプト起動</li>
                <li>
                  以下のコマンドを実行<br /><kbd>ha-build/bat/maven-build-batch.bat local</kbd>
                </li>
              </ol>
            </v-expansion-panel-content>
          </v-expansion-panel>
          <v-expansion-panel>
            <v-expansion-panel-header
              class="justify-self-start text-subtitle-1"
              disable-icon-rotate
            >
              <div>健康管理ドキュメント ビルド手順</div>
            </v-expansion-panel-header>
            <v-expansion-panel-content class="text-body-2">
              <ol>
                <li>コマンドプロンプト起動</li>
                <li>
                  以下のコマンドを実行<br /><kbd>ha-build/bat/ha-docs-gen.bat</kbd>
                </li>
              </ol>
            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>
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
          text: "ローカル環境ビルド手順",
          disabled: true,
          href: "/wiki/localBuild",
        },
      ],
    };
  },
};
</script>
<style scoped>
</style>