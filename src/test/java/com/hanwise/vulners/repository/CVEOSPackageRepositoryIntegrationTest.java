package com.hanwise.vulners.repository;

import com.hanwise.vulners.VulnersServiceConfiguration;
import com.hanwise.vulners.entity.CVEOSPackage;
import com.hanwise.vulners.entity.CVEOS;
import com.hanwise.vulners.service.CVEServiceIntegrationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VulnersServiceConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@TestConfiguration
public class CVEOSPackageRepositoryIntegrationTest {
    private static Logger logger = LoggerFactory.getLogger(CVEServiceIntegrationTest.class);
    @Autowired
    private CVEOSPackageRepository cveosPackageRepository;

    @Test
    public void testFind() {
        List<CVEOSPackage> packageList = cveosPackageRepository.findDistinctByOsTypeAndOsVersionAndPackageNameAndArch(
                "CentOS", "7", "kexec-tools", "x86_64"
        );
        logger.debug("Find packages: "+ packageList.size());
        for(CVEOSPackage pkg: packageList) {
            logger.debug("Package: "+ pkg.getPackageName()+ " "+ pkg.getPackageVersion() + " " + pkg.getOperator() + " belongs to "+ pkg.getCveSource().getId());
        }
        assert(packageList.size()==2);
    }

    @Test
    public void testFindOS() {
        List<Map<String,String>> oses = cveosPackageRepository.geAvailableOSes();
        logger.debug("Find oses: "+ oses.size());
        for(Map os: oses){
            logger.debug("OS: "+ os.toString());
        }
    }
}
