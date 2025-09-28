export const state = () => ({
  // ログインID
  seq_login_id: "",
  // トークン
  token: "",
  // ユーザ権限
  roles: [],
});

export const mutations = {
  /**
   * トークンをクリアする
   * @param {*} state
   */
  clearToken: function (state) {
    state.seq_login_id = "";
    state.token = "";
  },
  /**
   * トークンを設定する
   * @param {*} state
   * @param {*} token_data
   */
  setToken: function (state, token_data) {
    state.seq_login_id = token_data.seq_login_id;
    state.token = token_data.token;
  },
  /**
   * ユーザ情報を設定する
   * @param {*} state
   * @param {*} user_data
   */
  setUserData: function (state, user_data) {
    state.roles = user_data.roles;
  },
};
