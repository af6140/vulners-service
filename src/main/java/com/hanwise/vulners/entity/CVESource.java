package com.hanwise.vulners.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="tbl_cvesource")
public class CVESource implements Serializable{

    @JsonProperty("lastseen")
    private String lastSeen;

    @ElementCollection
    @CollectionTable(name="tbl_cve_references", joinColumns = {@JoinColumn(name="cve_id")})
    private Set<String> references;

    @OneToMany(mappedBy="cveSource", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("affectedPackage")
    private Set<CVEOSPackage> affectedPackages;

    @Lob
    private String description;

    @Id
    private String id;

    private String href;

    private String modified;
    private String published;

    @Embedded
    private CVSS cvss;



    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tbl_cvesource_cve", joinColumns = @JoinColumn(name = "cvesource_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "cve_id", referencedColumnName = "id"))
    @JsonProperty("cvelist")
    private Set<CVE> cveList;

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public Set<String> getReferences() {
        return references;
    }

    public void setReferences(Set<String> references) {
        this.references = references;
    }

    public Set<CVEOSPackage> getAffectedPackages() {
        return affectedPackages;
    }

    public void setAffectedPackages(Set<CVEOSPackage> affectedPackages) {
        this.affectedPackages = affectedPackages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public CVSS getCvss() {
        return cvss;
    }

    public void setCvss(CVSS cvss) {
        this.cvss = cvss;
    }

    public Set<CVE> getCveList() {
        return cveList;
    }


    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public void setCveList(Set<CVE> cveList) {
        this.cveList = cveList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CVESource cveSource = (CVESource) o;
        return Objects.equals(id, cveSource.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
