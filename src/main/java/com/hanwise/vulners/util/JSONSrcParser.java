package com.hanwise.vulners.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.hanwise.vulners.entity.CVEEntry;
import com.hanwise.vulners.entity.CVEOSPackage;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class JSONSrcParser {
    public  List<CVEEntry> parseFile(String jsonFile){
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        objectMapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        File file = new File(jsonFile);
        List<CVEEntry> cveEntryList= Collections.emptyList();
        System.out.println("Current dir is : "+ new File(".").getAbsolutePath());
        try {
            cveEntryList = objectMapper.readValue(file, typeFactory.constructCollectionType(List.class, CVEEntry.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cveEntryList;
    }
}
