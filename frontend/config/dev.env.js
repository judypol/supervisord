'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  baseURL:'"http://localhost:8081/"',
  authKey:'"dev_authKey"'
})
