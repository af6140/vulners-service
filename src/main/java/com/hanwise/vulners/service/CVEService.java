package com.hanwise.vulners.service;

import com.hanwise.vulners.entity.CVEOSPackage;
import com.hanwise.vulners.entity.CVEOSPackageId;
import com.hanwise.vulners.entity.CVESource;
import com.hanwise.vulners.repository.CVEOSPackageRepository;
import com.hanwise.vulners.util.VersionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.*;

@Component
public class CVEService {

    public static  Logger logger = LoggerFactory.getLogger(CVEService.class);

    public static String DEFAULT_OS_TYPE="CentOS";
    public static String DEFAULT_OS_VERSION="7";
    public static String DEFAULT_ARCH="x86_64";

    @Autowired
    private CVEOSPackageRepository cveosPackageRepository;

    @Autowired
    private JSONService jsonService;

    @Cacheable("matchingCVE")
    public List<CVESource> getMatchingCVE(String osType, String osVersion, String packageName, String arch, String packageVersion) {

        List<CVEOSPackage> packageList=this.cveosPackageRepository.findDistinctByOsTypeAndOsVersionAndPackageNameAndArch(osType,osVersion,packageName,arch);

        // dealing with special version in source
        List<CVEOSPackage> anyPackageList = this.cveosPackageRepository.findDistinctByOsTypeAndOsVersionAndPackageNameAndArch(osType,"any",packageName,arch);
        packageList.addAll(anyPackageList);
        String pVersion = VersionUtil.splitEpochVersion(packageVersion)[1];

        List<CVESource> cveSources= new LinkedList<>();
        for(CVEOSPackage pkg: packageList){
            String targetV = pkg.getEncodedVersion();
            String pkgO = pkg.getOperator();
            if("lt".equalsIgnoreCase(pkgO)) {
                if(VersionUtil.encodeRPMVersion(pVersion).compareTo(targetV)<0) {
                    cveSources.add(pkg.getCveSource());
                    if(logger.isDebugEnabled()) logger.debug("Find cve source : "+ pkg.getCveSource().getId());
                }
            } else if ("eq".equalsIgnoreCase(pkgO)){
                if(VersionUtil.encodeRPMVersion(pVersion).compareTo(targetV)==0) {
                    cveSources.add(pkg.getCveSource());
                    if(logger.isDebugEnabled())  logger.debug("Find cve source : "+ pkg.getCveSource().getId());
                }
            } else if ("gt".equalsIgnoreCase(pkgO)){
                if(VersionUtil.encodeRPMVersion(pVersion).compareTo(targetV)>0) {
                    cveSources.add(pkg.getCveSource());
                    if(logger.isDebugEnabled())  logger.debug("Find cve source : "+ pkg.getCveSource().getId());
                }
            } else {
                logger.warn("Not expected operator found: {} for package: {} of version {}  ", pkg.getCveSource().getId(), pkg.getPackageName(), pkg.getPackageVersion());
            }

        }
        // in reverse order by id
        Collections.sort(cveSources, new Comparator<CVESource>() {
            @Override
            public int compare(CVESource source, CVESource t1) {
                return -1 * source.getId().compareTo(t1.getId());
            }
        });
        return cveSources;
    }

    public List<CVESource> getMatchingCVE(List<CVEOSPackageId> pkgIds){
        Set<CVESource> cveSources = new HashSet<>();
        for(CVEOSPackageId pkgId : pkgIds) {
            if(logger.isDebugEnabled()){
                logger.debug("Matching for pkgid: "+ this.jsonService.writeObjectAsString(pkgId));
            }
            List<CVESource> pkgCVESources = this.getMatchingCVE(pkgId.getOsType(), pkgId.getOsVersion(), pkgId.getPackageName(), pkgId.getArch(), pkgId.getPackageVersion());
            cveSources.addAll(pkgCVESources);
        }
        List<CVESource> cveSourceList=new ArrayList<>(cveSources);
        // sort and in reverse order
        Collections.sort(cveSourceList, new Comparator<CVESource>() {
            @Override
            public int compare(CVESource source, CVESource t1) {
                return -1* source.getId().compareTo(t1.getId());
            }
        });
        return cveSourceList;
    }
}
