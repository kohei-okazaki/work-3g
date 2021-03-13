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
    <v-form ref="sqlForm">
      <v-row>
        <v-col class="text-left" sm="12" md="5">
          <v-select
            :items="tables"
            return-object
            clearable
            label="テーブル名"
            item-text="logicalName"
            item-value="physicalName"
            v-model="table"
          ></v-select>
        </v-col>
      </v-row>
      <v-row>
        <v-col class="text-left" sm="12" md="3">
          <v-text-field
            clearable
            label="変更前カラム名"
            v-model="oldColumn"
          ></v-text-field>
        </v-col>
        <v-col class="text-left" sm="12" md="3">
          <v-text-field
            clearable
            label="変更後カラム名"
            v-model="newColumn"
          ></v-text-field>
        </v-col>
        <v-col class="text-left" sm="12" md="3">
          <v-select
            :items="columnTypes"
            return-object
            clearable
            label="カラム型"
            v-model="columnType"
          ></v-select>
        </v-col>
        <v-col class="text-left" sm="12" md="3">
          <v-text-field
            clearable
            label="サイズ"
            v-model="columnSize"
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row
        v-if="
          table != null &&
          oldColumn != null &&
          newColumn != null &&
          columnType != null
        "
      >
        <v-col class="text-left" sm="12">
          <v-btn color="info" @click="createSql">SQL生成</v-btn>
        </v-col>
      </v-row>
      <v-row v-if="sql != null">
        <v-col class="text-left" sm="12">
          <v-textarea label="SQL" v-model="sql" readonly></v-textarea>
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
          text: "カラム変更",
          disabled: true,
          href: "/db/modColumn",
        },
      ],
      tables: this.$store.state.db.tables,
      columnTypes: ["VARCHAR", "DECIMAL", "INT", "DATE", "DATETIME"],
      table: null,
      oldColumn: null,
      newColumn: null,
      columnType: null,
      columnSize: null,
      sql: null,
    };
  },
  methods: {
    createSql: function () {
      let sql =
        "ALTER TABLE " +
        this.table.physicalName +
        " CHANGE " +
        this.oldColumn +
        " " +
        this.newColumn +
        " " +
        this.columnType;
      if (this.columnType == "VARCHAR" || this.columnType == "DECIMAL") {
        sql += "(" + this.columnSize + ")";
      }
      this.sql = sql + ";";
    },
  },
};
</script>

<style scoped>
</style>