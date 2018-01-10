package com.hanwise.vulners.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanwise.vulners.entity.CVEEntry;
import com.hanwise.vulners.service.Configuration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
@Import(com.hanwise.vulners.SimpleTestConfiguration.class)
public class JSONSrcParserTest {

    @Autowired
    Configuration configuration;
    @Autowired
    private JSONSrcParser jsonSrcParser;

    @Test
    public void testParseJSONSrc() {
        List<CVEEntry> cveEntries=this.jsonSrcParser.parseFile(configuration.getJsonSrc());
        assertTrue(cveEntries.size()>0);

        System.out.println("Parsed cve entries count: "+cveEntries.size());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        for(CVEEntry entry: cveEntries){
            try {
                String jsonStr= mapper.writerWithDefaultPrettyPrinter().writeValueAsString(entry);
                System.out.println(jsonStr);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            break;
        }

    }
}
