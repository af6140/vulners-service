package com.hanwise.vulners.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.io.Serializable;
import java.util.Objects;

public class CVEOSPackageId  implements Serializable {
    private String osType;
    private String osVersion;
    private String packageVersion;
    private String packageName;
    private String arch;

    public CVEOSPackageId() {
        this.osType="CentOs";
        this.osVersion="7";
        this.arch="x86_64";
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getPackageVersion() {
        return packageVersion;
    }

    public void setPackageVersion(String packageVersion) {
        this.packageVersion = packageVersion;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getArch() {
        return arch;
    }

    public void setArch(String arch) {
        this.arch = arch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CVEOSPackageId that = (CVEOSPackageId) o;
        return Objects.equals(osType, that.osType) &&
                Objects.equals(osVersion, that.osVersion) &&
                Objects.equals(packageVersion, that.packageVersion) &&
                Objects.equals(packageName, that.packageName) &&
                Objects.equals(arch, that.arch);
    }

    @Override
    public int hashCode() {

        return Objects.hash(osType, osVersion, packageVersion, packageName, arch);
    }

    @JsonIgnore
    public boolean isValid(){
        boolean valid=true;
        if(StringUtils.isEmpty(osType) || StringUtils.isEmpty(osVersion) || StringUtils.isEmpty(packageName) || StringUtils.isEmpty(arch) || StringUtils.isEmpty(packageVersion)) {
            valid = false;
        }
        return valid;
    }
}
