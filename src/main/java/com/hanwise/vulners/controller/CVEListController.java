package com.hanwise.vulners.controller;

import com.hanwise.vulners.entity.CVEEntry;
import com.hanwise.vulners.entity.CVEOSPackageId;
import com.hanwise.vulners.entity.CVESource;
import com.hanwise.vulners.service.CVEService;
import com.hanwise.vulners.service.Configuration;
import com.hanwise.vulners.service.JSONService;
import com.hanwise.vulners.service.ServiceException;
import com.hanwise.vulners.util.CVESourceSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.sql.rowset.serial.SerialException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cve-list")
public class CVEListController {

    private static Logger logger = LoggerFactory.getLogger(CVEListController.class);
    @Autowired
    private CVEService cveService;
    @Autowired
    private Configuration configuration;
    @Autowired
    private JSONService jsonService;

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<CVESourceSummary>>
    getCVEsForPakcageVersion(
            @RequestParam(value="os_type", required=true, defaultValue = "CentOS") String osType,
            @RequestParam(value="os_version", required=true, defaultValue = "7" ) String osVersion,
            @RequestParam(value="name", required=true) String name,
            @RequestParam(value="version", required=true) String version,
            @RequestParam(value="arch", required=true, defaultValue="x86_64") String arch

    ) {
        logger.debug("Checking package: {} of version {} for arch {}", name, version,  arch);
        List<CVESource> cveSources = this.cveService.getMatchingCVE(osType,osVersion,name,arch,version);
        logger.debug("Found cvesources: {} ", cveSources.size());
        List<CVESourceSummary> cves = cveSources.stream().map(s -> new CVESourceSummary(s)).collect(Collectors.toList());
        if(cves==null || cves.size()==0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cves, this.configuration.getDefaultJsonResponseHttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<List<CVESourceSummary>>
    getCVEsForPackageVersions(@RequestBody List<CVEOSPackageId> pkgIds) throws ServiceException{
        if(logger.isDebugEnabled()){
            logger.debug("Checking cves for packages: ");
            logger.debug(this.jsonService.writeObjectAsString(pkgIds));

        }
        boolean valid = true;
        if(pkgIds!=null && pkgIds.size()>0){
            for(CVEOSPackageId pkgId: pkgIds){
                if(!pkgId.isValid()){
                    valid =false;
                    break;
                }
            }
        }

        if(!valid){
            return new ResponseEntity(this.jsonService.writeObjectAsString(pkgIds),HttpStatus.BAD_REQUEST);
        }

        List<CVESource> cveSources = null;
        List<CVESourceSummary> cves = null;
        if(valid && pkgIds.size()>0) {
            cveSources = this.cveService.getMatchingCVE(pkgIds);
            logger.debug("Found cvesources: {} ", cveSources.size());
            cves = cveSources.stream().map(s -> new CVESourceSummary(s)).collect(Collectors.toList());
        }
        if(cves==null || cves.size()==0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cves, this.configuration.getDefaultJsonResponseHttpHeaders(), HttpStatus.OK);
    }
}
