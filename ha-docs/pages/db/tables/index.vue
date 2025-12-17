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
      </v-col> </v-row><v-row justify="center">
      <v-col sm="12">
        <v-text-field v-model="search" append-icon="mdi-magnify" label="検索条件" single-line></v-text-field>
        <v-data-table dense :headers="headers" :items="tableList" :search="search" :items-per-page="1000" :footer-props="{
          showFirstLastPage: true,
          firstIcon: 'mdi-arrow-collapse-left',
          lastIcon: 'mdi-arrow-collapse-right',
          prevIcon: 'mdi-minus',
          nextIcon: 'mdi-plus',
        }">
          <!-- v-slotの書き方は以下でないとESLintでエラーになる -->
          <template v-slot:[`item.primaryKey`]="{ item }">
            <v-icon v-if="item.primaryKey" color="green">mdi-check</v-icon>
            <v-icon v-else>mdi-minus</v-icon>
          </template>
          <template v-slot:[`item.sequence`]="{ item }">
            <v-icon v-if="item.sequence" color="green">mdi-check</v-icon>
            <v-icon v-else>mdi-minus</v-icon>
          </template>
          <template v-slot:[`item.crypt`]="{ item }">
            <v-icon v-if="item.crypt" color="green">mdi-check</v-icon>
            <v-icon v-else>mdi-minus</v-icon>
          </template>
          <template v-slot:[`item.notNull`]="{ item }">
            <v-icon v-if="item.notNull" color="green">mdi-check</v-icon>
            <v-icon v-else>mdi-minus</v-icon>
          </template>
        </v-data-table>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import AppBreadCrumbs from "~/components/AppBreadCrumbs.vue";
import AppContentsTitle from "~/components/AppContentsTitle.vue";

export default {
  // DBのレイアウトを適用
  layout: "dbLayout",
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
          text: "DB",
          disabled: false,
          href: "/db",
        },
        {
          text: "テーブル一覧",
          disabled: true,
          href: "/db/tables",
        },
      ],
      headers: [
        {
          text: "テーブル名(論理名)",
          value: "logicalTableName",
        },
        {
          text: "テーブル名(物理名)",
          value: "physicalTableName",
        },
        {
          text: "Pキー",
          value: "primaryKey",
          filterable: false,
        },
        {
          text: "シーケンス",
          value: "sequence",
          filterable: false,
        },
        {
          text: "暗号化",
          value: "crypt",
          filterable: false,
        },
        {
          text: "必須",
          value: "notNull",
          filterable: false,
        },
        {
          text: "カラム名(論理名)",
          value: "logicalColumnName",
        },
        {
          text: "カラム名(物理名)",
          value: "physicalColumnName",
        },
        {
          text: "型",
          value: "type",
          filterable: false,
        },
        {
          text: "サイズ",
          value: "size",
          filterable: false,
        },
        {
          text: "デフォルト値",
          value: "default",
          filterable: false,
        },
      ],
      tableList: [],
      search: "",
    };
  },
  mounted: function () {
    // テーブル情報のリストを取得
    let tableList = this.$store.state.db.tables;
    for (var i = 0; i < tableList.length; i++) {
      let table = tableList[i];
      let columnList = table.columns;
      for (var j = 0; j < columnList.length; j++) {
        let column = columnList[j];

        // テーブルとカラム情報を1行にまとめる
        let record = {
          logicalTableName: table.logicalName,
          physicalTableName: table.physicalName,
          primaryKey: column.primaryKey,
          sequence: column.sequence,
          crypt: column.crypt,
          notNull: column.notNull,
          logicalColumnName: column.logicalName,
          physicalColumnName: column.physicalName,
          type: column.type,
          size: column.size,
          default: column.default,
        };
        this.tableList.push(record);
      }
    }
  },
};
</script>

<style scoped></style>