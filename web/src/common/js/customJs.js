import store from '../../store'
import axios_ from 'axios'

let JsUtil = JsUtil || {}
let JsUtilPro = JsUtilPro || {}

JsUtil.getCookie = function (tokenKey) {
  let arr
  let reg = new RegExp('(^|)' + tokenKey + '=([^;]*)(;|$)')
  if (arr = document.cookie.match(reg)) {
    return unescape(arr[2])
  } else {
    return null
  }
}

JsUtil.setCookie = function (tokenKey, tokenValue) {
  document.cookie = tokenKey + '=' + tokenValue
}

JsUtil.tests = function () {
  alert(JsUtilPro.name.b)
}

JsUtil.getToken = function () {
  setInterval(() => {
    axios_.post('/system/continuation', {'token': store.state.token}).then(function (resp) {
      store.commit('setToken', resp.data)
    })
  }, 5000 - 10)
}

JsUtilPro.name = {
  a: 'Test.vue',
  b: 'test is ok !'
}

export default {
  JsUtil: JsUtil,
  JsUtilPro: JsUtilPro
}
