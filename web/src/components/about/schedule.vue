<template>
  <div class="home">
    <div>
      <div @click="getDataMy">获取数据</div>
      <div>
        <Button @click="handleSelectAll(true)">全选</Button>
        |
        <Button @click="handleSelectAll(false)">取消</Button>
        |
        <Button @click="handleSelectOne">选择了...</Button>
        |
        <Button @click="getAddress">选择了 service address : {{address}}</Button>
      </div>
      <div>
        <Upload action="api/upload" multiple :show-upload-list="false" ref="uploadIMG" :before-upload="beforeUpload">
          <img src="@/assets/logo.png" height="30" width="30" ref="imgUrl"/>
          <Button icon="ios-cloud-upload-outline">Upload files</Button>
        </Upload>
      </div>
      <!--  <div @click="mysqladd">mysql</div>
        <div @click="redisTest">redisTest</div>
        <div @click="sentKafak">sentKafak</div>
        <div @click="getConsumer">getConsumer</div>-->
      <div v-if="file!==null">Upload file:{{file.name}}
        <Button type="text" @click="upload" :loading="loadingStatus">{{loadingStatus?'Uploading':'Click to upload'}}
        </Button>
      </div>
      <Button icon="ios-cloud-upload-outline" @click="download">download</Button>
      |
      <Button @click="add">add</Button>
      |
      <Table border :columns="columns" :data="listData" ref="MyTable" :footer="handleRender"
             @on-select="onSelect($event)" @on-selection-change="onSelectionChange($event)">
      </Table>

      <Table border :columns="columns1" :data="listData">
        <template slot-scope="{ row }" slot="name">
          <strong>{{ row.name }}</strong>
        </template>
        <template slot-scope="{ row, index }" slot="action">
          <Button type="primary" size="small" style="margin-right: 5px" @click="show(index)">View</Button>
          <Button type="error" size="small" @click="remove(index)">Delete</Button>
        </template>
      </Table>

      <Page :current.sync="pageCurrent" :total="totalRow" :page-size="pageSize" show-total @on-change="changePage"/>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'schedule',
    components: {},
    data() {
      return {
        columns: [
          {
            type: 'selection',
            width: 90,
            align: 'center'
          },
          {
            title: 'Action',
            key: 'classPath',
            width: 150,
            align: 'center',
            render: (h, params) => {
              return h('div', [
                h('Button', {
                  props: {
                    type: 'primary',
                    size: 'small'
                  },
                  style: {
                    marginRight: '5px'
                  },
                  on: {
                    click: () => {
                      this.show(params.index)
                    }
                  }
                }, 'View'),
                h('Button', {
                  props: {
                    type: 'primary',
                    size: 'small'
                  },
                  style: {
                    marginRight: '5px'
                  },
                  on: {
                    click: () => {
                      this.edit(params.index)
                      // this.handleRender(params.index)
                    }
                  }
                }, 'edit'),
                h('Button', {
                  props: {
                    type: 'error',
                    size: 'small'
                  },
                  on: {
                    click: () => {
                      this.remove(params.index)
                    }
                  }
                }, 'Delete')
              ])
            }
          },
          {
            title: 'ID',
            key: 'id'
          },
          {
            title: 'cron',
            key: 'cron'
          },
          {
            title: 'classPath',
            key: 'classPath'
          },
          {
            title: '状态',
            key: 'startOver',
            width: 150,
            align: 'center',
            render: (h, params) => {
              return h('div', [
                h('Button', {
                  props: {
                    type: 'error',
                    size: 'small'
                  },
                  on: {
                    click: () => {
                      if (params.row.startOver === 1) {
                        params.row.startOver = 2
                      } else {
                        params.row.startOver = 1
                      }
                      // this.forbidden(params.index)
                    }
                  }
                }, params.row.startOver === 1 ? '禁用' : '可用')
              ])
            }
          },
        ],
        columns1: [
          {
            title: 'id',
            key: 'id'
          },
          {
            title: 'cron',
            key: 'cron'
          },
          {
            title: 'classPath',
            key: 'classPath'
          },
          {
            title: 'Action',
            slot: 'action',
            width: 150,
            align: 'center'
          }
        ],
        listData: [],//{'id': '1', 'name': '1', 'action': '1'}
        totalRow: 0,
        currentTotal: 0,
        pageCurrent: 1,
        pageSize: 3,
        footer: 123,
        value: '',
        address: '',
        user: {userName: '', 'description': ''},
        loadingStatus: false,
        file: null
      }
    },
    methods: {
      getDataMy() {
        this.getData()
      },
      getAddress() {
        let this_ = this
        this.$httpApi.getAddress().then(function (resp) {
          this_.address = resp.data
          // console.info(resp.data)
        })
      },
      changePage() {
        this.getData()
      },
      getData() {
        // debugger;
        let this_ = this
        let page = {'pageCurrent': this.pageCurrent, 'pageSize': this.pageSize}
        this.$apiConfigSchedule.getData(page).then(function (resp) {
          this_.listData = resp.data.list
          this_.currentTotal = resp.data.currentTotal
          this_.totalRow = resp.data.total
        })
      },
      handleRender(index) {
        this.$Modal.confirm({
          render: (h) => {
            return h('Input', {
              props: {
                value: this.listData[index].name,
                autofocus: true,
                placeholder: 'Please enter your name...'
              },
              on: {
                input: (val) => {
                  this.value = val
                }
              }
            })
          }
        })
      },
      add() {
        this.$Modal.confirm({
          title: 'User add',
          content: `id：<input id="a" type="text"  >
           <br> Name：<input id="b" type="text"  >
                    <br>Age：<input id="c" type="text" >`,
          onOk: () => {
            this.listData.push({
              id: document.getElementById('a').value,
              name: document.getElementById('c').value,
              action: document.getElementById('c').value
            })
            this.$httpApi.addData(this.listData).then(function (resp) {
              console.info(resp.msg)
            })
          },
          onCancel: () => {
            this.$Message.info('Clicked cancel')
          }
        })
      },
      show(index) {
        this.$Modal.info({
          title: 'User Info',
          content: `Name：${this.listData[index].name}<br>Age：${this.listData[index].id}`
        })
      },
      edit(index) {
        this.$Modal.confirm({
          title: 'User edit',
          content: `Name：<input id="a" type="text" value="${this.listData[index].cron}" >
                    <br>Age：<input id="b" type="text" value="${this.listData[index].id}" >`,
          onOk: () => {
            this.listData[index].id = document.getElementById('b').value
            this.listData[index].name = document.getElementById('a').value
            let param = {
              'id': this.listData[index].id,
              'name': this.listData[index].name,
              'action': this.listData[index].action
            }
            this.$httpApi.updateData(param).then(function (resp) {
              console.info(resp.data)
            })
          },
          onCancel: () => {
            this.$Message.info('Clicked cancel')
          }
        })
      },
      remove(index) {
        console.info(this.listData[index].id)
        let param = {'id': this.listData[index].id}
        let this_ = this
        this.$httpApi.delData(param).then(function (resp) {
          this_.listData.splice(index, 1)
          console.info(resp.msg)
        })
      },
      forbidden(index) {
        console.info(this.listData[index].id)
        /* let param = {'id': this.listData[index].id}
         let this_ = this
         this.$httpApi.delData(param).then(function (resp) {
           this_.listData.splice(index, 1)
           console.info(resp.msg)
         })*/
      },
      handleSelectAll(status) {
        this.$refs.MyTable.selectAll(status)
      },
      handleSelectOne() {
        console.info(this.$refs.MyTable.objData[0]._isChecked)
        // let objData = this.$refs.MyTable.objData;
        /* let objData1= objData.filter((item, index, objData) => {
             return item._isChecked
           }) */
        // console.info(objData1)
      },
      createIMG(event) {
        console.info(event)
      },
      onSelect(event) {
        console.info(event[0])
      },
      onSelectionChange(event) {
        console.info(event)
      },
      download() {
        this.$httpApi({method: 'post', url: 'download', responseType: 'blob'}).then(function (resp) {
          if ('download' in document.createElement('Test.vue')) {
            if (resp.type == 'application/json') {
              console.info('请先登陆')
              return
            }
            let link = document.createElement('Test.vue')
            link.download = 'parallel.png'
            link.style.display = 'none'
            link.href = window.URL.createObjectURL(new Blob([resp]))
            document.body.appendChild(link)
            link.click()
            URL.revokeObjectURL(link.href)
            document.body.removeChild(link)
          } else {
            navigator.msSaveBlob(new Blob([resp]), 'parallel.png')
          }
        })
        // window.open("http://localhost:8080/download")
      },
      beforeUpload(file) {
        this.file = file
        // this.imgUrl = window.URL.createObjectURL(file)//显示
        // this.$refs.uploadIMG.post(file)
        this.$refs.imgUrl.src = window.URL.createObjectURL(file)
        return false
      },
      upload() {
        this.loadingStatus = true
        let formData = new FormData()
        formData.append('file', this.file)
        let this_ = this
        this.$httpApi.post('upload', formData).then(function (resp) {
            console.info(resp)
            // this_.file.splice(index, 1)
            this_.file = null
            this_.$refs.imgUrl.src = 'img/' + resp.msg
          }
        )
        this.loadingStatus = false
      }, // redisTest
      mysqladd() {
        this.$httpApi.post('mysqladd').then(function (resp) {
          console.info(resp)
        })
      }, // getConsumer
      redisTest() {
        this.$httpApi.post('redisTest').then(function (resp) {
          console.info(resp)
        })
      },
      getConsumer() {
        this.$httpApi.post('getConsumer').then(function (resp) {
          console.info(resp)
        })
      },
      sentKafak() {
        this.$httpApi.post('sentKafak').then(function (resp) {
          console.info(resp)
        })
      }

    },
    computed: {
      currentRow() {
        this.current = this.listData.length
        return this.current % this.pageSize
      }
    }
  }
</script>
