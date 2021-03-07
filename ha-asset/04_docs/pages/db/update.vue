<template>
  <div>
    <AppBreadCrumbs :items="breadcrumbs" />
    <AppContentsTitle :title="breadcrumbs[breadcrumbs.length - 1].text" />
    <v-row justify="center">
      <v-col class="text-left" cols="12" sm="8" md="10">
        <v-alert border="left" type="info" text elevation="2" dismissible
          >簡易的にDBを更新するSQLを作成するツール。<br />以下に当たる部分を指定し作成ボタンでSQLを作成
          <ul>
            <li>テーブル名</li>
            <li>SET句</li>
            <li>WHERE句</li>
          </ul></v-alert
        >
      </v-col>
    </v-row>

    <v-form ref="sqlForm">
      <v-row>
        <v-col cols="1" sm="1" md="1"></v-col>
        <v-col class="text-left" cols="5" sm="5" md="5">
          <v-select
            :items="tables"
            return-object
            clearable
            label="更新対象テーブル名"
            item-text="logicalName"
            item-value="physicalName"
            v-model="updateTable"
            @input="setColumns"
          ></v-select>
        </v-col>
      </v-row>
      <v-row v-if="updateTable != null">
        <v-col cols="1" sm="1" md="1"></v-col>
        <v-col class="text-left" cols="5" sm="5" md="5">
          <v-select
            :items="columns"
            return-object
            clearable
            label="更新対象カラム名"
            item-text="logicalName"
            item-value="physicalName"
            v-model="updateColumn"
          ></v-select>
        </v-col>
        <v-col class="text-left" cols="5" sm="5" md="5">
          <v-text-field
            label="更新対象カラムの値"
            clearable
            v-model="updateColumnValue"
          ></v-text-field>
        </v-col>
      </v-row>

      <v-row
        v-if="
          updateTable != null &&
          updateColumn != null &&
          updateColumnValue != null
        "
      >
        <v-col cols="1" sm="1" md="1"></v-col>
        <v-col class="text-left" cols="5" sm="5" md="5">
          <v-select
            :items="columns"
            return-object
            clearable
            label="更新条件カラム名"
            item-text="logicalName"
            item-value="physicalName"
            v-model="whereColumn"
          ></v-select>
        </v-col>
        <v-col class="text-left" cols="5" sm="5" md="5">
          <v-text-field
            label="更新条件カラムの値"
            clearable
            v-model="whereColumnValue"
          ></v-text-field>
        </v-col>
      </v-row>

      <v-row
        v-if="
          updateTable != null &&
          updateColumn != null &&
          updateColumnValue != null &&
          whereColumn != null &&
          whereColumnValue != null
        "
      >
        <v-col cols="1" sm="1" md="1"></v-col>
        <v-col class="text-left" cols="5" sm="5" md="5">
          <v-btn color="info" @click="createSql">SQL生成</v-btn>
        </v-col>
      </v-row>
      <v-row v-if="sql != null">
        <v-col cols="1" sm="1" md="1"></v-col>
        <v-col class="text-left" cols="5" sm="5" md="5">
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
          text: "更新",
          disabled: true,
          href: "/db/update",
        },
      ],
      tables: this.$store.state.db.tables,
      columns: [],
      updateTable: null,
      updateColumn: null,
      updateColumnValue: null,
      whereColumn: null,
      whereColumnValue: null,
      sql: null,
    };
  },
  methods: {
    setColumns: function () {
      if (this.updateTable == null) {
        // 選択テーブル名がnullの場合
        this.columns = [];
        return;
      }
      this.columns = this.updateTable.columns;
    },
    createSql: function () {
      this.sql =
        "UPDATE " +
        this.updateTable.physicalName +
        " SET " +
        this.updateColumn.physicalName +
        " = '" +
        this.updateColumnValue +
        "' WHERE " +
        this.whereColumn.physicalName +
        " = '" +
        this.whereColumnValue +
        "';";
    },
  },
};
</script>

<style scoped>
</style>