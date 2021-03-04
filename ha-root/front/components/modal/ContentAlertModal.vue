<template>
  <div>
    <v-dialog
      v-model="dialog"
      :max-width="width"
      :max-height="height"
      scrollable
    >
      <v-card>
        <v-toolbar dark :color="color" dense flat>
          <v-toolbar-title class="white--text">{{ title }}</v-toolbar-title>
        </v-toolbar>
        <br />
        <v-card-text>
          管理者サイトについての注意事項<br />
          推奨ブラウザは以下とする
          <v-simple-table>
            <tbody>
              <tr>
                <td v-for="(browser, i) in browsers" :key="i">
                  {{ browser }}
                </td>
              </tr>
            </tbody>
          </v-simple-table>
          ログインできたが、APIが呼べずエラーメッセージが表示される場合<br>
          一度ログアウトし、再度ログインすること
          <v-divider></v-divider>
        </v-card-text>
        <br />
        <v-card-actions class="pt-0">
          <v-spacer></v-spacer>
          <v-btn :color="color" @click="close">閉じる</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
export default {
  data: function () {
    return {
      dialog: false,
      resolve: null,
      reject: null,
      title: "管理者サイト注意事項",
      width: 600,
      height: 800,
      color: "primary darken-1",
      browsers: ["Chrome"],
    };
  },
  methods: {
    open: function () {
      this.dialog = true;
      return new Promise((resolve, reject) => {
        this.resolve = resolve;
        this.reject = reject;
      });
    },
    close: function () {
      this.dialog = false;
    },
  },
};
</script>

<style scoped>
</style>