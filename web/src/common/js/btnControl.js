import Vue from 'vue'

const btnControls = Vue.directive('has', {
  inserted: function (el, binding) {
    // 获取按钮权限
    if (!Vue.prototype.$_has(binding.value)) {
      //移除不匹配的按钮
      el.parentNode.removeChild(el);
    }
  }
});
Vue.prototype.$_has = function (value) {
  let isExist = false
  var buttonpermsStr = sessionStorage.getItem('btnContext');
  if (buttonpermsStr === undefined || buttonpermsStr == null) {
    return false
  } else {
    buttonpermsStr.split(',').forEach(element => {
      if (element == value) {
        isExist = true
      }
    });
  }
  console.info(isExist + ' !!!!')
  return isExist
}
export default {btnControls}
