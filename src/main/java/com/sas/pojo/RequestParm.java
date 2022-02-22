package com.sas.pojo;

import org.apache.http.NameValuePair;

public class RequestParm implements NameValuePair {
    String name;
    String value;

    public RequestParm(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
