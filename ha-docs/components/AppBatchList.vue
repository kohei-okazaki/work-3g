<template>
  <v-expansion-panels multiple>
    <v-expansion-panel v-for="(batchItem, i) in batchArray" :key="i">
      <v-expansion-panel-header class="text-subtitle-1"
        >{{ i + 1 }}.{{ batchItem.name }}
      </v-expansion-panel-header>
      <v-expansion-panel-content class="text-body-2">
        <div>{{ batchItem.description }}</div>
        <br />
        <div>
          <v-btn
            :href="batchItem.url"
            text
            color="link"
            min-height="20"
            class="x-small post-link align-center py-1 px-2"
            target="_blank"
            rel="noopener noreferrer"
            >処理フロー</v-btn
          >はこちら
        </div>
        <v-alert border="left" type="info">起動タイミング</v-alert>
        <v-simple-table>
          <thead>
            <tr>
              <th>No.</th>
              <th>起動時刻</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(cron, i) in batchItem.crons" :key="i">
              <td>{{ i + 1 }}</td>
              <td>{{ cron }}</td>
            </tr>
          </tbody>
        </v-simple-table>
        <br />
        <v-alert border="left" type="info">起動引数</v-alert>
        <v-simple-table>
          <thead>
            <tr>
              <th>No.</th>
              <th>引数名</th>
              <th>設定値</th>
              <th>備考</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(arg, i) in batchItem.args" :key="i">
              <td>{{ i + 1 }}</td>
              <td>{{ arg.name }}</td>
              <td>{{ arg.value }}</td>
              <td v-html="arg.description"></td>
            </tr>
          </tbody>
        </v-simple-table>
        <br />
      </v-expansion-panel-content>
    </v-expansion-panel>
  </v-expansion-panels>
</template>
<script>
export default {
  props: {
    batchArray: Array,
  },
};
</script>

<style scoped>
</style>