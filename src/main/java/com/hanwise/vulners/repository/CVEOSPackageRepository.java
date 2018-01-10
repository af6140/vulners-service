package com.hanwise.vulners.repository;

import com.hanwise.vulners.entity.CVEOSPackage;
import com.hanwise.vulners.entity.CVEOSPackageId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CVEOSPackageRepository extends CrudRepository<CVEOSPackage,CVEOSPackageId>, CustomCVEOSPackageRepository {
    //Optional<CVEOSPackage> findById(CVEOSPackageId id);
    List<CVEOSPackage> findDistinctByOsTypeAndOsVersionAndPackageNameAndArch(String osType, String osVersion, String packageName, String arch);
}
