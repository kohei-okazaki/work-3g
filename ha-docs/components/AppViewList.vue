<template>
  <v-expansion-panels multiple>
    <v-expansion-panel v-for="(view, i) in viewArray" :key="i">
      <v-expansion-panel-header class="text-subtitle-1"
        >{{ i + 1 }}.{{ view.name }}
      </v-expansion-panel-header>
      <v-expansion-panel-content class="text-body-2">
        <div>{{ view.description }}</div>
        <br />
        <div>
          本画面遷移時、ログイン認証要否=<v-icon v-if="view.loginAuth" color="green"
            >mdi-check</v-icon
          ><v-icon v-else color="blue">mdi-minus</v-icon>
        </div>
        <br />
        <v-simple-table dense v-if="view.leads.length > 0">
          <thead>
            <tr>
              <th>No.</th>
              <th>画面名</th>
              <th>ログイン認証要否</th>
              <th>遷移方法</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(lead, j) in view.leads" :key="j">
              <td>{{ j + 1 }}</td>
              <td>{{ lead.name }}</td>
              <td>
                <v-icon v-if="lead.loginAuth" color="green">mdi-check</v-icon>
              </td>
              <td>{{ lead.description }}</td>
            </tr>
          </tbody></v-simple-table
        ><br />
      </v-expansion-panel-content>
    </v-expansion-panel>
  </v-expansion-panels>
</template>

<script>
export default {
  props: {
    viewArray: Array,
  },
};
</script>

<style scoped>
</style>