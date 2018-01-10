package com.hanwise.vulners.service;


import com.hanwise.vulners.util.VersionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RPMVersionService {
    private static final Logger logger = LoggerFactory.getLogger(RPMVersionService.class);
    public int compareVersion(String v1, String v2) {
        if(logger.isDebugEnabled()){
            logger.debug("Comparing version {} and {}", v1, v2);
        }
        return VersionUtil.encodeRPMVersion(v1).compareTo(VersionUtil.encodeRPMVersion(v2));
    }
}
