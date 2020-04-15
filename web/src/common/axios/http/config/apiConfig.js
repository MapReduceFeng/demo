import withAxios from '../withAxios'

const apiConfig = [
  {
    name: 'test',
    url: '/test/test',
    method: 'post'
  },
  {
    name: 'userLogin',
    url: '/system/login',
    method: 'post'
  }, {
    name: 'getData',
    url: '/system/getData',
    method: 'post'
  }, {
    name: 'addData',
    url: '/system/addData',
    method: 'post'
  }, {
    name: 'updateData',
    url: '/system/updateData',
    method: 'post'
  }, {
    name: 'delData',
    url: '/system/delData',
    method: 'post'
  }, {
    name: 'getAddress',
    url: '/system/getAddress',
    method: 'post'
  },
  {
    name: 'menuGetData',
    url: '/menu/getData',
    method: 'post'
  },
  {
    name: 'elasticGetData',
    url: '/elastic/getData',
    method: 'post'
  },
  {
    name: 'elasticAddData',
    url: '/elastic/addData',
    method: 'post'
  }
]
export default withAxios(apiConfig)
