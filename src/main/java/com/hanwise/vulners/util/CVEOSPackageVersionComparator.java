package com.hanwise.vulners.util;

import com.hanwise.vulners.entity.CVEOSPackage;

import java.util.Comparator;

public class CVEOSPackageVersionComparator implements Comparator<CVEOSPackage> {

    @Override
    public int compare(CVEOSPackage p, CVEOSPackage t1) {
        return p.getEncodedVersion().compareTo(t1.getEncodedVersion());
    }
}
