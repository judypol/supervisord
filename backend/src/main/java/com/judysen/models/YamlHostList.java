package com.judysen.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhihua on 2018/9/28.
 */
@Component
@ConfigurationProperties(prefix = "supervisord")
public class YamlHostList {
    private List<SupervisordHostModel> api=new ArrayList<>();

    public List<SupervisordHostModel> getApi() {
        return api;
    }

    public void setApi(List<SupervisordHostModel> api) {
        this.api = api;
    }
}
