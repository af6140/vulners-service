package com.hanwise.vulners.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class Configuration {

    @Value("${db.jsonsrc}")
    private String jsonSrc;

    @Value("${data.srcurl}")
    private String dataUrl;

    @Value("${data.path}")
    private String dataPath;


    private HttpHeaders defaultJsonResponseHttpHeaders;


    public Configuration() {
        this.defaultJsonResponseHttpHeaders = new HttpHeaders();
        this.defaultJsonResponseHttpHeaders.set("Content-Type", "application/json;charset=UTF-8");
    }

    public String getJsonSrc() {
        return jsonSrc;
    }

    public void setJsonSrc(String jsonSrc) {
        this.jsonSrc = jsonSrc;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public HttpHeaders getDefaultJsonResponseHttpHeaders() {
        return defaultJsonResponseHttpHeaders;
    }

    public void setDefaultJsonResponseHttpHeaders(HttpHeaders defaultJsonResponseHttpHeaders) {
        this.defaultJsonResponseHttpHeaders = defaultJsonResponseHttpHeaders;
    }
}
