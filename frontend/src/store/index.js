import Vue from 'vue'
import Vuex from 'vuex'
import state from './state'
import mutations from './mutations'
import actions from './actions'

Vue.use(Vuex)
var store=new Vuex.Store({
    state:state,
    mutations:mutations,
    actions:actions
});

export default store;