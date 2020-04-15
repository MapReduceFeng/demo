const path = require('path')
const glob = require('glob')
const webpack = require('webpack')

const getPages = (() => {
  const [
    globPathHtml, globPathJs, pages, tempSet] = [
    ['./src/**/**.js', 'entry'],
    ['./public/index.html', 'template'],
    Object.create(null),
    new Set()
  ]
  const getMultiPageConf = (globPath, keyName) => {
    let [fileList, tempArr, modName] = [glob.sync(globPath), [], null]
    let set = new Set()
    set.add('./src/assets/').add('./src/js/')
    fileList = fileList.filter(function (item) {
      let result = true
      for (let isSet of set) {
        if (item.startsWith(isSet)) {
          result = false
          break
        }
      }
      return result
    })
    console.log('生成相应页面' + fileList)
    if (fileList.length !== 0) {
      for (let entry of fileList) {
        let paths = entry.substring(6, entry.length - 3)
        if (tempSet.has(entry)) {
          Object.assign(pages[paths], {[keyName]: entry, 'filename': [paths] + '.html'})
        } else {
          Reflect.set(pages, paths, {[keyName]: entry, 'filename': [paths] + '.html'}) && tempSet.add(paths)
        }
      }
      return true
    } else {
      if (keyName === 'template') {
        throw new Error('没有相应模板页')
      } else if (keyName === 'entry') {
        throw new Error('没有相应入口js文件')
      } else {
        throw new Error('其它错误！')
      }
    }
  }
  try {
    while (getMultiPageConf(...globPathHtml)) {
      return pages
    }
  } catch (err) {
    console.log('加载出错！')
  }
})()

module.exports = {
  lintOnSave: false,
  devServer: {
    port: 3000,
    proxy: {
      '/api': {
        // target: 'http://localhost:8080/',
        target: 'http://localhost:3001/',
        ws: true,
        changeOrigin: true,
        pathRewrite: {
          '^/api': '/'
        }
      }
    }
  },
  pages: getPages,
  publicPath: '/',
  outputDir: 'dist'
}
