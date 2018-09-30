package com.judysen.controller;

import com.judysen.models.ResponseMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by lizhihua on 2018/9/28.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SupervisorControllerTest {
    @Autowired
    SupervisorController supervisorController;
    @Test
    public void getAllProcessTest() throws Exception{
        supervisorController.getAllProcess("服务器1");
    }
    @Test
    public void restartProcessTest() throws Exception{
        ResponseMessage responseMessage=supervisorController.reStart("pentosWeb","服务器1");
        System.out.println(responseMessage.getData());
    }
    @Test
    public void stopProcessTest() throws Exception{
        ResponseMessage responseMessage=supervisorController.stop("pentosWeb","服务器1");
        System.out.println(responseMessage.getData());
    }
    @Test
    public void startProcessTest() throws Exception{
        ResponseMessage responseMessage=supervisorController.start("pentosWeb","服务器1");
        System.out.println(responseMessage.getData());
    }
}
