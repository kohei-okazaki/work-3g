export const state = () => ({
  auth_data_list: []
});

export const mutations = {
  
  setToken: function (state, token_data) {

    for (var i = 0; i < state.auth_data_list.length; i++) {
      let auth_data = state.auth_data_list[i];
      if (auth_data.seq_login_id == token_data.seq_login_id) {
        // 同一ログインIDの場合、トークンを更新し処理を終了
        auth_data.token = token_data.token;
        return;
      }
    }

    // 同一ログインIDは↑のループで更新されているため、ここまで来た場合は新規追加
    state.auth_data_list.unshift({
      seq_login_id: token_data.seq_login_id,
      token: token_data.token,
    });
  },

}
