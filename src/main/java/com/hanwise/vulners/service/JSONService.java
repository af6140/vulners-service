package com.hanwise.vulners.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class JSONService {

    private static Logger logger = LoggerFactory.getLogger(JSONService.class);
    private ObjectMapper simpleObjectMapper;

    public JSONService(){
        this.simpleObjectMapper = new ObjectMapper();
        this.simpleObjectMapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public String writeObjectAsString(Object o){
        String value = null;
        try {
            value = this.simpleObjectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            logger.debug(e.getMessage());
        }
        return value;
    }

}
