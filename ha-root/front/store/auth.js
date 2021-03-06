export const state = () => ({
  seq_login_id: '',
  token: '',
  roles: [],
});

export const mutations = {
  clearToken: function (state) {
    state.seq_login_id = '';
    state.token = '';
  },
  setToken: function (state, token_data) {
    state.seq_login_id = token_data.seq_login_id;
    state.token = token_data.token;
  },
  setUserData: function (state, user_data) {
    state.roles = user_data.roles;
  },
};
