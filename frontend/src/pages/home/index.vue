<template>
    <div>
        <div class="search">
            <el-form :inline="true" :model="form" class="demo-form-inline">
                <el-form-item label="活动区域">
                    <el-select v-model="form.serverName" placeholder="服务器名">
                        <el-option v-for="item in servers" :key="item" :label="item" :value="item"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onSearch">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onStopAll">Stop All</el-button>
                    <el-button type="primary" @click="onStartAll">Start All</el-button>
                    <el-button type="primary" @click="onRestartAll">ReStart All</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div class="processTable">
            <el-table :data="processData" style="width:100%;" :row-style="rowClass">
                <el-table-column prop="name" label="程序"></el-table-column>
                <el-table-column label="状态">
                    <template slot-scope="scope">
                        <el-tag type="primary" v-if="scope.row.stateName=='RUNNING'">{{scope.row.stateName}}</el-tag>
                        <el-tag type="danger" v-else>{{scope.row.stateName}}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="description" label="描述">

                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <template v-if="scope.row.stateName=='RUNNING'">
                            <el-button size="small" type="primary" @click="onStop(scope.row)">Stop</el-button>
                            <el-button size="small" type="warning" @click="onReStart(scope.row)">ReStart</el-button>
                        </template>
                        <template v-else>
                            <el-button size="small" type="primary" @click="onStart(scope.row)">Start</el-button>
                        </template>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>
<style scoped>
    
    .info-box-icon{
        border-top-left-radius: 2px;
        border-top-right-radius: 0;
        border-bottom-right-radius: 0;
        border-bottom-left-radius: 2px;
        display: block;
        float: left;
        height: 90px;
        width: 90px;
        text-align: center;
        font-size: 45px;
        line-height: 90px;
        background: rgba(0,0,0,0.2);
        color:#fff !important;
    }
    .info-box{
        display: block;
        min-height: 90px;
        background: #fff;
        width: 100%;
        box-shadow: 0 1px 1px rgba(0,0,0,0.1);
        border-radius: 2px;
        margin-bottom: 15px;
    }
    .first_cl{
        background-color: #00c0ef !important;
    }
    .second_cl{
        background-color: #f39c12 !important;
    }
    .third_cl{
        background-color: #00a65a !important;
    }
    .progress{
        height: 2px;
        margin: 5px -10px 5px -10px;
        overflow: hidden;
        background-color: #f5f5f5;
        border-radius: 4px;
        -webkit-box-shadow: inset 0 1px 2px rgba(0,0,0,.1);
        box-shadow: inset 0 1px 2px rgba(0,0,0,.1)
    }
    .info-box-content{
        padding: 5px 10px;
        margin-left: 90px;
    }
    .info-box-content span{
        display: block;
        font-size: 14px;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        /* padding-left: 15px; */
        color:#f5f5f5;
        /* height: 25px; */
    }
    .box{
        position: relative;
        border-radius: 3px;
        background: #ffffff;
        border-top: 3px solid #d2d6de;
        margin-bottom: 20px;
        width: 100%;
        box-shadow: 0 1px 1px rgba(0,0,0,0.1);
    }
    .lineChart {
        height: 350px;
        width:100%;
    }
    .polarChart{
        height: 350px;
        width:100%;
    }
</style>


<script>
import moment from 'moment'
import Vue from 'vue'
// import 'echarts/lib/chart/line'
// import 'echarts/lib/chart/pie'
// import 'echarts/lib/component/legend'
// import 'echarts/lib/component/title'
// import 'echarts/lib/component/tooltip'

export default {
    data(){
        return{
            form:{
                serverName:''
            },
            processData:[],
            servers:[]
        }
    },
    methods:{
        onSearch(){
            this.$axios.get('supervisor/allProcess?serverName='+this.form.serverName).then((res)=>{
                if(res.success==true){
                    this.processData=res.data
                }else{
                    this.$message.error(res.msg);
                }
            })
        },
        getServers(){
            this.$axios.get('supervisor/allHostInfo').then(res=>{
                if(res.success){
                    this.servers=res.data;
                    this.form.serverName=this.servers[0];
                    this.onSearch();
                }else{
                    this.$message.error(res.msg);
                }
            });
        },
        onSubmit(){

        },
        onStop(row){
            var url='supervisor/stop?process='+row.name+'&serverName='+this.form.serverName;
            this.$axios.get(url)
                .then(res=>{
                    if(res.success){
                        row.stateName="STOPPED"
                    }
                });
        },
        onStart(row){
            var url='supervisor/start?process='+row.name+'&serverName='+this.form.serverName;
            this.$axios.get(url)
                .then(res=>{
                    if(res.success&&res.data){
                        row.stateName="RUNNING"
                    }
                });
        },
        onReStart(row){
            var url='supervisor/reStart?process='+row.name+'&serverName='+this.form.serverName;
            this.$axios.get(url)
                .then(res=>{
                    if(res.success){
                        row.stateName="RUNNING"
                    }else{
                        row.stateName='STOPPED';
                    }
                });
        },
        onStopAll(){
            var url='supervisor/stopAll?serverName='+this.form.serverName;
            this.$axios.get(url)
                .then(res=>{
                    this.onSearch();
                });
        },
        onStartAll(){
            var url='supervisor/startAll?serverName='+this.form.serverName;
            this.$axios.get(url)
                .then(res=>{
                    this.onSearch();
                });
        },
        onRestartAll(){
            var url='supervisor/reStartAll?serverName='+this.form.serverName;
            this.$axios.get(url)
                .then(res=>{
                    this.onSearch();
                });
        },
        rowClass(row){
            if(row.rowIndex%2==0){
                return {"background-color": "#ffffcc"}
            }else{
                return {"background-color": "#fff"}
            }
        }
    },
    created(){
        this.getServers();
    }
}
</script>
