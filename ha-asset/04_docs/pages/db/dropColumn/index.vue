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
        <v-col class="text-left" sm="12" md="6">
          <v-select
            :items="tables"
            return-object
            clearable
            label="テーブル名"
            item-text="logicalName"
            item-value="physicalName"
            v-model="table"
            @input="setColumns"
          ></v-select>
        </v-col>
        <v-col class="text-left" sm="12" md="6">
          <v-select
            :items="columns"
            return-object
            clearable
            label="カラム名"
            item-text="logicalName"
            item-value="physicalName"
            v-model="column"
          ></v-select>
        </v-col>
      </v-row>
      <v-row v-if="table != null && column != null">
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
          text: "カラム削除",
          disabled: true,
          href: "/db/dropColumn",
        },
      ],
      tables: this.$store.state.db.tables,
      columns: [],
      table: null,
      column: null,
      sql: null,
    };
  },
  methods: {
    setColumns: function () {
      if (this.table == null) {
        // 選択テーブル名がnullの場合
        this.columns = [];
        return;
      }
      this.columns = this.table.columns;
    },
    createSql: function () {
      this.sql =
        "ALTER TABLE " +
        this.table.physicalName +
        " DROP " +
        this.column.physicalName +
        ";";
    },
  },
};
</script>

<style scoped>
</style>