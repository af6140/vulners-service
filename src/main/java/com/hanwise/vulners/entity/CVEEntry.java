package com.hanwise.vulners.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;


public class CVEEntry implements Serializable{

    @JsonProperty("_id")
    private String cseaId;

    @JsonProperty("_source")
    private CVESource source;

    public CVESource getSource() {
        return source;
    }

    public void setSource(CVESource source) {
        this.source = source;
    }

    @JsonIgnore
    public String getCseaId() {
        return cseaId;
    }
    @JsonIgnore
    public void setCseaId(String cseaId) {
        this.cseaId = cseaId;
    }

    @JsonIgnore
    public long getId() {
        String[] specs= this.getSource().getId().split("[-:]");
        System.out.println(specs[0]);
        System.out.println(specs[1]);
        return Long.parseLong(specs[1]+specs[2]);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CVEEntry cveEntry = (CVEEntry) o;
        return Objects.equals(this.getCseaId(), this.getCseaId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getCseaId());
    }
}
