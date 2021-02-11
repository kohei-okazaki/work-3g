<template>
  <v-menu
    :open-on-hover="true"
    :open-on-click="true"
    :close-on-content-click="false"
    :close-on-hover="false"
    :nudge-width="200"
    offset-y
  >
    <template v-slot:activator="{ on, attrs }">
      <v-btn v-bind="attrs" v-on="on">
        {{ viewLoginUser }}
      </v-btn>
    </template>

    <v-card>
      <v-list>
        <v-list-item>
          <v-list-item-content>
            <v-list-item-title>{{ viewPopOverTitle }}</v-list-item-title>
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
  },
  computed: {
    viewLoginUser: function () {
      return "ログインID：" + this.getSeqLoginId();
    },
    viewPopOverTitle: function () {
      return "ログインユーザ情報";
    },
    viewPopOverSeqLoginId: function () {
      return "ログインID=" + this.getSeqLoginId();
    },
    viewPopOverRole: function () {
      return "権限=";
    },
  },
};
</script>

<style scoped>
</style>