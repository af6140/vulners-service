package com.hanwise.vulners.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanwise.vulners.entity.CVE;
import com.hanwise.vulners.entity.CVEOSPackage;
import com.hanwise.vulners.entity.CVESource;
import com.hanwise.vulners.entity.CVSS;

import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CVESourceSummary {
    @JsonProperty
    private String lastSeen;
    @JsonProperty
    private Set<String> references;
    @JsonProperty
    private String description;
    @JsonProperty
    private String id;
    @JsonProperty
    private String href;
    @JsonProperty
    private String modified;
    @JsonProperty
    private float cvssScore;
    @JsonProperty
    private String cvssVector;
    @JsonProperty
    private List<String> cveList;
    @JsonProperty
    private String published;

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

    public float getCvssScore() {
        return cvssScore;
    }

    public void setCvssScore(float cvssScore) {
        this.cvssScore = cvssScore;
    }

    public String getCvvsVector() {
        return cvssVector;
    }

    public void setCvssVector(String cvssVector) {
        this.cvssVector = cvssVector;
    }

    public List<String> getCveList() {
        return cveList;
    }

    public void setCveList(List<String> cveList) {
        this.cveList = cveList;
    }

    public CVESourceSummary(CVESource source) {
        this.lastSeen = source.getLastSeen();
        this.references = source.getReferences();
        this.description = source.getDescription();
        this.id = source.getId();
        this.href = source.getHref();
        this.modified = source.getModified();
        this.cvssScore = source.getCvss().getScore();
        this.cvssVector = source.getCvss().getVector();
        this.cvssVector = this.cvssVector == null ? "N/A" : this.cvssVector;
        this.cveList =source.getCveList().stream().map(p -> p.getId()).collect(Collectors.toList());
        this.published = source.getPublished();
    }
}
