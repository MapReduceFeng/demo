import withAxios from '../withAxios'

const apiConfig = [
  {
    name: 'test',
    url: '/test/test',
    method: 'post'
  },
  {
    name: 'getData',
    url: '/scheduling/getData',
    method: 'post'
  },
  {
    name: 'getTokens',
    url: '/users/getToken',
    method: 'get'
  },
  {
    name: 'getKoa2JWT',
    url: '/users/jwt',
    method: 'post'
  }
]
export default withAxios(apiConfig)
