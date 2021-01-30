export const state = () => ({
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
  },
};
