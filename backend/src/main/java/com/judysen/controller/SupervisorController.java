package com.judysen.controller;

import com.judysen.models.ProcessModel;
import com.judysen.models.ResponseMessage;
import com.judysen.models.SupervisordHostModel;
import com.judysen.models.YamlHostList;
import com.judysen.supervisord.Process;
import com.judysen.supervisord.Supervisord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lizhihua on 2018/9/28.
 */
@RestController
@RequestMapping("supervisor")
@CrossOrigin
public class SupervisorController {
    @Autowired
    YamlHostList hostList;

    ConcurrentHashMap<String,SupervisordHostModel> allServer=new ConcurrentHashMap<>();
    public SupervisorController(@Autowired YamlHostList yamlHostList){
        for(SupervisordHostModel model:yamlHostList.getApi()){
            allServer.put(model.getName(),model);       //缓存起来
        }
    }
    /**
     * 服务器名称
     * @param serverName
     * @return
     * @throws Exception
     */
    @RequestMapping(value="allProcess",method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage getAllProcess(@RequestParam("serverName") String serverName) throws Exception {
        Supervisord supervisord =connect(serverName);
        Object[] objects =supervisord.getAllProcessInfo();
        List<ProcessModel> models=new ArrayList<>();
        if(objects!=null){
            models=convert2Entity(objects);
        }

        return new ResponseMessage(models);
    }

    private List<ProcessModel> convert2Entity(Object[] objects){
        List<ProcessModel> processModels=new ArrayList<>();
        for(Object obj:objects){
            ProcessModel model=new ProcessModel();
            HashMap map=(HashMap)obj;
            model.setDescription(map.get("description").toString());
            model.setName(map.get("name").toString());
            model.setStateName(map.get("statename").toString());
            processModels.add(model);
        }
        return processModels;
    }
    /**
     *
     * @param serverName
     * @return
     * @throws Exception
     */
    private Supervisord connect(String serverName) throws Exception{
        SupervisordHostModel model=allServer.get(serverName);
        if(model==null){
            throw new Exception("找不到对应的服务器");
        }
        Supervisord supervisord =Supervisord.connect(model.getHost()).auth(model.getUser(),model.getPassword());
        return supervisord;
    }
    /**
     * 获取所有的服务器信息
     * @return
     */
    @RequestMapping(value="allHostInfo",method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage getAllHostInfo(){
        List<String> hostNames=new ArrayList<>();
        for(SupervisordHostModel model:hostList.getApi()){
            hostNames.add(model.getName());
        }
        ResponseMessage responseMessage=new ResponseMessage(hostNames);
        return responseMessage;
    }

    /**
     * 停止一个程序
     * @param process
     * @param serverName
     * @return
     */
    @RequestMapping(value="stop",method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage stop(String process,String serverName) throws Exception{
        Supervisord supervisord=connect(serverName);
        boolean flag=supervisord.stopProcess(process,true);
        return new ResponseMessage<Boolean>(flag);
    }

    /**
     * 启动一个程序
     * @param process
     * @param serverName
     * @return
     * @throws Exception
     */
    @RequestMapping(value="start",method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage start(String process,String serverName) throws Exception{
        Supervisord supervisord=connect(serverName);
        boolean flag=supervisord.startProcess(process,true);
        return new ResponseMessage<Boolean>(flag);
    }

    /**
     * 重启
     * @param process
     * @param serverName
     * @return
     * @throws Exception
     */
    @RequestMapping(value="reStart",method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage reStart(String process,String serverName) throws Exception{
        Supervisord supervisord=connect(serverName);
        boolean flag=supervisord.stopProcess(process,true); //先停止
        if(flag){
            supervisord=connect(serverName);
            flag=supervisord.startProcess(process,true);
        }
        return new ResponseMessage(flag);
    }
    @RequestMapping(value="stopAll",method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage stopAll(String serverName) throws Exception{
        Supervisord supervisord=connect(serverName);
        Object[] objects =supervisord.stopAllProcesses(true);
        return new ResponseMessage("停止成功！");
    }
    @RequestMapping(value="startAll",method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage startAll(String serverName) throws Exception{
        Supervisord supervisord=connect(serverName);
        Object[] objects=supervisord.startAllProcesses(true);
        return new ResponseMessage("启动成功");
    }
    @RequestMapping(value="reStartAll",method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage reStartAll(String serverName) throws Exception{
        Supervisord supervisord=connect(serverName);
        Object[] objects=supervisord.stopAllProcesses(true);
        supervisord=connect(serverName);
        objects=supervisord.startAllProcesses(true);

        return new ResponseMessage("重启成功");
    }
}
