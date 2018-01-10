package com.hanwise.vulners.repository;

import com.hanwise.vulners.entity.CVEOS;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface CustomCVEOSPackageRepository {
    @Query("select distinct p.osType as osType, p.osVersion as osVersion from CVEOSPackage p")
    List<Map<String,String>> geAvailableOSes();
}
