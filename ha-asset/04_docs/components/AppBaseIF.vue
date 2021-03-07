<template>
  <v-expansion-panels multiple>
    <v-expansion-panel v-for="(ifItem, i) in ifs" :key="i">
      <v-expansion-panel-header class="text-subtitle-1"
        >{{ i + 1 }}.{{ ifItem.name }}
        <template v-slot:actions>
          <v-chip :color="getHttpMethodColor(ifItem.httpMethod)">
            {{ ifItem.httpMethod }}
          </v-chip>
          <v-chip color="gray">
            {{ ifItem.endpoint }}
          </v-chip>
        </template>
      </v-expansion-panel-header>
      <v-expansion-panel-content class="text-body-2">
        <div>{{ ifItem.description }}</div>
        <br />
        <v-alert border="left" type="info">リクエストIF</v-alert>
        <v-simple-table>
          <thead>
            <tr>
              <th>No.</th>
              <th>項目名(物理名)</th>
              <th>項目名(論理名)</th>
              <th>必須</th>
              <th>型</th>
              <th>Byte数</th>
              <th>備考</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(request, i) in ifItem.request" :key="i">
              <td>{{ i + 1 }}</td>
              <td>{{ request.physicalName }}</td>
              <td>{{ request.logicalName }}</td>
              <td>
                <v-icon v-if="request.required"
                  >mdi-checkbox-blank-circle-outline</v-icon
                >
              </td>
              <td>{{ request.type }}</td>
              <td>{{ request.byte }}</td>
              <td v-html="request.description"></td>
            </tr>
          </tbody>
        </v-simple-table>
        <v-divider />
        <br />
        <v-alert border="left" type="info">レスポンスIF</v-alert>
        <v-simple-table>
          <thead>
            <tr>
              <th>No.</th>
              <th>項目名(物理名)</th>
              <th>項目名(論理名)</th>
              <th>必須</th>
              <th>型</th>
              <th>Byte数</th>
              <th>備考</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(response, i) in ifItem.response" :key="i">
              <td>{{ i + 1 }}</td>
              <td>{{ response.physicalName }}</td>
              <td>{{ response.logicalName }}</td>
              <td>
                <v-icon v-if="response.required"
                  >mdi-checkbox-blank-circle-outline</v-icon
                >
              </td>
              <td>{{ response.type }}</td>
              <td>{{ response.byte }}</td>
              <td v-html="response.description"></td>
            </tr>
          </tbody>
        </v-simple-table>
      </v-expansion-panel-content>
    </v-expansion-panel>
  </v-expansion-panels>
</template>
<script>
export default {
  props: {
    ifs: Object,
  },
  methods: {
    getHttpMethodColor: function (httpMethod) {
      if (httpMethod == "GET") {
        return "orange";
      } else if (httpMethod == "POST") {
        return "blue";
      } else if (httpMethod == "PUT") {
        return "yellow";
      } else if (httpMethod == "DELETE") {
        return "red";
      }
      return "gray";
    },
  },
};
</script>

<style scoped>
</style>