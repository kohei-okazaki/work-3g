<template>
  <div>
    <v-row>
      <v-col class="text-center" sm="12">
        <AppBreadCrumbs :items="breadcrumbs" />
      </v-col>
    </v-row>
    <v-row justify="center">
      <v-col class="text-left" sm="12">
        <AppContentsTitle :title="breadcrumbs[breadcrumbs.length - 1].text" />
      </v-col>
    </v-row>
    <v-row justify="center">
      <v-col class="text-left" sm="12">
        <v-alert border="left" type="info" text elevation="2" dismissible
          >簡易的にDBを検索するSQLを作成するツール。<br />以下に当たる部分を指定し作成ボタンでSQLを作成
          <ul>
            <li>テーブル名</li>
            <li>WHERE句</li>
          </ul></v-alert
        >
      </v-col>
    </v-row>

    <v-form ref="sqlForm">
      <v-row>
        <v-col class="text-left" sm="12" md="6">
          <v-select
            :items="tables"
            return-object
            clearable
            label="検索対象テーブル名"
            item-text="logicalName"
            item-value="physicalName"
            v-model="selectedTable"
            @input="setColumns"
          ></v-select>
        </v-col>
      </v-row>
      <v-row v-if="selectedTable != null">
        <v-col class="text-left" sm="12" md="6">
          <v-select
            :items="columns"
            return-object
            clearable
            label="検索対象カラム名"
            item-text="logicalName"
            item-value="physicalName"
            v-model="selectedColumn"
          ></v-select>
        </v-col>
        <v-col class="text-left" sm="12" md="6">
          <v-text-field
            label="検索対象カラムの値"
            clearable
            v-model="selectedColumnValue"
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row
        v-if="
          selectedTable != null &&
          selectedColumn != null &&
          selectedColumnValue != null
        "
      >
        <v-col class="text-left" sm="12">
          <v-btn color="info" @click="createSql">SQL生成</v-btn>
        </v-col>
      </v-row>
      <v-row v-if="sql != null">
        <v-col class="text-left" sm="12">
          <v-textarea label="SQL" v-model="sql" readonly />
        </v-col>
      </v-row>
    </v-form>
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
          text: "検索",
          disabled: true,
          href: "/db/select",
        },
      ],
      tables: this.$store.state.db.tables,
      columns: [],
      selectedTable: null,
      selectedColumn: null,
      selectedColumnValue: null,
      sql: null,
    };
  },
  methods: {
    setColumns: function () {
      if (this.selectedTable == null) {
        // 選択テーブル名がnullの場合
        this.columns = [];
        return;
      }
      this.columns = this.selectedTable.columns;
    },
    createSql: function () {
      this.sql =
        "SELECT * FROM " +
        this.selectedTable.physicalName +
        " WHERE " +
        this.selectedColumn.physicalName +
        " = '" +
        this.selectedColumnValue +
        "';";
    },
  },
};
</script>

<style scoped>
</style>