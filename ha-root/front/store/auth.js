export const state = () => ({
  // auth_data_list: '',
  seq_login_id: '',
  token: ''
});

export const mutations = {
  clearToken: function(state) {
    console.log('vuex state clear');
    state.seq_login_id = '';
    state.token = '';
  },
  setToken: function (state, token_data) {
    state.seq_login_id = token_data.seq_login_id;
    state.token = token_data.token;

    // トークンとログインIDのオブジェクトが配列の場合
    // // state.auth_data_list.splice(0);
    // console.log("called setToken token_data.seq_login_id=" + token_data.seq_login_id + ", token=" + token_data.token);
    // console.log("size=" + state.auth_data_list.length);
    // for (var i = 0; i < state.auth_data_list.length; i++) {

    //   // 保存済の認証情報を取得
    //   let auth_data = state.auth_data_list[i];

    //   if (auth_data.seq_login_id == token_data.seq_login_id) {
    //     // 同一ログインIDの場合、トークンを更新し処理を終了
    //     auth_data.token = token_data.token;
    //     console.log("setToken hit!!!");
    //     console.log("auth_data.token=" + auth_data.token);
    //     return;
    //   }
    // }
    // // 同一ログインIDは↑のループで更新されているため、ここまで来た場合は新規追加
    // state.auth_data_list.unshift({
    //   seq_login_id: token_data.seq_login_id,
    //   token: token_data.token,
    // });
  },

  // getToken: function (state, seq_login_id) {
  //   // state.auth_data_list.splice(0);
  //   console.log("called getToken seq_login_id=" + seq_login_id);
  //   console.log("size=" + state.auth_data_list.length);
  //   for (var i = 0; i < state.auth_data_list.length; i++) {

  //     // 保存済の認証情報を取得
  //     let auth_data = state.auth_data_list[i];

  //     if (auth_data.seq_login_id == seq_login_id) {
  //       // 同一ログインIDの場合、トークン返却
  //       console.log("getToken hit!!!");
  //       console.log("auth_data.token=" + auth_data.token);
  //       return auth_data.token;
  //     }
  //   }
  // }
};
