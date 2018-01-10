package com.hanwise.vulners.service;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanwise.vulners.VulnersServiceConfiguration;
import com.hanwise.vulners.entity.CVEOSPackage;
import com.hanwise.vulners.entity.CVESource;
import com.hanwise.vulners.util.CVESourceSummary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VulnersServiceConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@TestConfiguration
public class CVEServiceIntegrationTest {

    private static Logger logger= LoggerFactory.getLogger(CVEServiceIntegrationTest.class);
    @Autowired
    private CVEService cveService;


    @Test
    public void testGetMatchingCVE(){
        List<CVESource> cveSources=this.cveService.getMatchingCVE(CVEService.DEFAULT_OS_TYPE, CVEService.DEFAULT_OS_VERSION, "kexec-tools", CVEService.DEFAULT_ARCH, "2.0.5");
        assert(cveSources.size()==1);
        ObjectMapper simpleObjectMapper = new ObjectMapper();
        simpleObjectMapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        for(CVESource source: cveSources) {
            CVESourceSummary summary = new CVESourceSummary(source);
            try {
                simpleObjectMapper.writer().writeValue(System.out, summary);
            } catch (IOException e) {
                logger.error(e.getMessage());
            }

        }
    }
}
