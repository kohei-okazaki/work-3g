<template>
  <v-menu
    :open-on-hover="true"
    :open-on-click="false"
    :close-on-content-click="false"
    :close-on-hover="false"
    :nudge-width="150"
    offset-y
  >
    <template v-slot:activator="{ on, attrs }">
      <v-btn v-bind="attrs" v-on="on" text>
        {{ viewLoginUser }}
      </v-btn>
    </template>

    <v-card>
      <v-list>
        <v-list-item>
          <v-list-item-content>
            <v-list-item-title>ログイン情報</v-list-item-title>
            <v-list-item-subtitle
              >{{ viewPopOverSeqLoginId }}
            </v-list-item-subtitle>
            <v-list-item-subtitle>{{ viewPopOverRole }} </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </v-list>
      <v-divider></v-divider>
      <v-list></v-list>
      <v-card-actions>
        <v-spacer></v-spacer>
        <AppLoginUserEdit />
      </v-card-actions>
    </v-card>
  </v-menu>
</template>

<script>
import AppLoginUserEdit from "~/components/AppLoginUserEdit.vue";

export default {
  components: {
    AppLoginUserEdit,
  },
  methods: {
    getSeqLoginId: function () {
      return this.$store.state.auth.seq_login_id;
    },
    getRoles: function () {
      return this.$store.state.auth.roles;
    },
  },
  computed: {
    viewLoginUser: function () {
      return "ログインID：" + this.getSeqLoginId();
    },
    viewPopOverSeqLoginId: function () {
      return "ログインID=" + this.getSeqLoginId();
    },
    viewPopOverRole: function () {
      // 権限配列をカンマ区切りの文字列に変換
      return "権限=" + this.getRoles().map(item => item.label).join(",");
    },
  },
};
</script>

<style scoped>
</style>