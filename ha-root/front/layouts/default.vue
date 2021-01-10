<template>
  <v-app dark>
    <v-navigation-drawer v-model="drawer" :mini-variant="miniVariant" :clipped="clipped" fixed app>
      <v-list>
        <v-list-item v-for="(item, i) in items" :key="i" :to="item.to" router exact>
          <v-list-item-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title v-text="item.title" />
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <v-app-bar :clipped-left="clipped" fixed app>
      <v-app-bar-nav-icon @click.stop="drawer = !drawer" />
      <v-btn icon @click.stop="miniVariant = !miniVariant">
        <v-icon>mdi-{{ `chevron-${miniVariant ? 'right' : 'left'}` }}</v-icon>
      </v-btn>
      <v-btn icon @click.stop="clipped = !clipped">
        <v-icon>mdi-application</v-icon>
      </v-btn>
      <v-btn icon @click.stop="fixed = !fixed">
        <v-icon>mdi-minus</v-icon>
      </v-btn>
      <AppTop />
      <v-spacer />
      <AppLogout />
      <v-btn icon @click.stop="rightDrawer = !rightDrawer">
        <v-icon>mdi-menu</v-icon>
      </v-btn>
    </v-app-bar>
    <v-main>
      <v-container>
        <nuxt />
      </v-container>
    </v-main>
    <v-navigation-drawer v-model="rightDrawer" :right="right" temporary fixed>
      <v-list>
        <v-list-item @click.native="right = !right">
          <v-list-item-action>
            <v-icon light>
              mdi-repeat
            </v-icon>
          </v-list-item-action>
          <v-list-item-title>Switch drawer (click me)</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <AppFooter />
  </v-app>
</template>

<script>
import AppFooter from '~/components/Footer.vue'
import AppLogout from '~/components/Logout.vue'
import AppTop from '~/components/Top.vue'

export default {
  components: {
    AppFooter,
    AppLogout,
    AppTop,
  },
  data () {
    return {
      clipped: false,
      drawer: true,
      fixed: false,
      items: [
        {
          icon: 'mdi-apps',
          title: 'Top',
          to: '/'
        },
        {
          icon: 'mdi-account-edit',
          title: '管理ユーザ編集',
          to: '/user/edit'
        },
        {
          icon: 'mdi-account',
          title: 'アカウント情報一覧',
          to: '/account'
        },
        {
          icon: 'mdi-api',
          title: 'API通信情報一覧',
          to: '/apidata'
        },
        {
          icon: 'mdi-pill',
          title: '健康情報一覧',
          to: '/healthinfo'
        },
        {
          icon: 'mdi-newspaper',
          title: 'お知らせ一覧',
          to: '/news'
        }
      ],
      miniVariant: false,
      right: true,
      rightDrawer: false,
    }
  },
}
</script>

<style scope>
</style> 