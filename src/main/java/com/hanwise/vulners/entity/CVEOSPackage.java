package com.hanwise.vulners.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanwise.vulners.util.VersionUtil;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="tbl_ospackage")
@IdClass(CVEOSPackageId.class)
public class CVEOSPackage {

    @JsonProperty("OS")
    @Id
    private String osType;

    @JsonProperty("OSVersion")
    @Id
    private String osVersion;

    @Id
    private String packageVersion;

    @Id
    private String packageName;

    @Id
    private String arch;
    private String operator;

    public String getOsType() {
        return osType;
    }

    public void setOsType(String OS) {
        this.osType = OS;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CVEOSPackage that = (CVEOSPackage) o;
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
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "cvesource_id")
    private CVESource cveSource;

    @JsonIgnore
    public void setCveSource(CVESource cveSource) {
        this.cveSource = cveSource;
    }

    @JsonIgnore
    public CVESource getCveSource() {
        return cveSource;
    }

    @JsonIgnore
    public String getEncodedVersion(){
        return VersionUtil.encodeRPMVersion(this.packageVersion);
    }

}
