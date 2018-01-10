package com.hanwise.vulners.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name="tbl_cve")
public class CVE {

    @Id
    private String id;

    @ManyToMany(mappedBy = "cveList")
    private Set<CVESource> cveSources;
    public CVE() {

    }
    public CVE(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Set<CVESource> getCveSources() {
        return cveSources;
    }

    public void setCveSources(Set<CVESource> cveSources) {
        this.cveSources = cveSources;
    }
}
