import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)
const moduleA = {
  state: {
    id: 'A'
  },
  mutations: {
    updates(state, p) {
      state.id = p
    }
  }
}
const moduleB = {}
const moduleC = {}
export default new Vuex.Store({
  state: {
    name: 'demo',
    loading: false,
    login: 0,
    token: false,
    roles: [],
    location: ['/'],
    MenuItemData: []
  },
  mutations: {
    setLogin(state, p) {
      state.login = p
    },
    setRoles(state, p) {
      state.roles = p
    },
    setToken(state, p) {
      state.token = p
    },
    setLoading(state, p) {
      state.loading = p
    },
    setLocation(state, p) {
      state.location = p
    }
  },
  actions: {
    setToken: ({commit}, p) => commit('setToken', p),
    setLogin: ({commit}, p) => commit('setLogin', p),
    setRoles: ({commit}, p) => commit('setRoles', p)
  },
  getters: {
    getLogin: state => {
      return state.login
    },
    getToken: state => {
      return state.token
    },
    getRoles: state => {
      return state.roles
    }
  },
  modules: {
    moduleA,
    moduleB,
    moduleC
  }
})
