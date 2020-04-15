import axios from '../axios'

const withAxios = apiConfig => {
  const serviceMap = {}
  apiConfig.map(({name, url, method}) => {
    serviceMap[name] = async function (data = {}) {
      let key = 'params'
      if (method === 'post' || method === 'put') {
        key = 'data'
      }
      return axios({method, url, [key]: data})
    }
  })
  return serviceMap
}
export default withAxios
