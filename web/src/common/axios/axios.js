import axios from 'axios'
import store from '../../store'

axios.defaults.timeout = 10000
axios.defaults.baseURL = '/api'
axios.interceptors.request.use(
  config => {
    config.headers = {
      'Content-TypeMy': 'application/json'// 测试用
    }
    config.headers = Object.assign = (config.headers, {'token': store.state.token})
    store.commit('setLoading', true)
    return config
  },
  error => {
    store.commit('setLoading', false)
    return Promise.reject(error)
  }
)
axios.interceptors.response.use(
  response => {
    store.commit('setLoading', false)
    console.info(response.data)
    return response.data
  },
  error => {
    store.commit('setLoading', false)
    return Promise.reject(error)
  }
)
export default axios
