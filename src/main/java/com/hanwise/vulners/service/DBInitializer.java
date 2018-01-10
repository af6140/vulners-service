package com.hanwise.vulners.service;

import com.hanwise.vulners.entity.CVEOSPackage;
import com.hanwise.vulners.entity.CVESource;
import com.hanwise.vulners.repository.CVESourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class DBInitializer {

    private static Logger logger = LoggerFactory.getLogger(DBInitializer.class);
    @Autowired
    private CVESourceRepository cveSourceRepository;

    @CacheEvict(cacheNames="matchingCVE", allEntries=true)
    public void loadDataBase(List<CVESource> entryList){
        if(entryList!=null){
            for(CVESource entry: entryList){
                Set<CVEOSPackage> pkgs = entry.getAffectedPackages();
                for(CVEOSPackage pkg: pkgs){
                    pkg.setCveSource(entry);
                }
                cveSourceRepository.save(entry);
            }
        } else {
            logger.warn("No cve list to load");
        }

    }

}
