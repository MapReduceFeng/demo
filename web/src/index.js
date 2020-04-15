import Vue from 'vue'
import App from './index.vue'
import router from './router'
import store from './store'
import ViewUI from 'view-design'
import 'view-design/dist/styles/iview.css'
import httpApi from '@/common/axios/http/config/apiConfig'
import apiConfigSchedule from '@/common/axios/http/config/apiConfigSchedule'
import customJs from '@/common/js/customJs.js'
import btnControls from '@/common/js/btnControl';


Vue.config.productionTip = false
Vue.use(ViewUI)
Vue.prototype.$httpApi = httpApi
Vue.prototype.$apiConfigSchedule = apiConfigSchedule
Vue.prototype.$customJs = customJs
Vue.use(btnControls);
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
