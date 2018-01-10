package com.hanwise.vulners.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DataInfo implements Serializable{
    private long size;
    private String modified;
    private List<Map<String,String>> affectedOSes;

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public List<Map<String,String>> getAffectedOSes() {
        return affectedOSes;
    }

    public void setAffectedOSes(List<Map<String,String>> affectedOSes) {
        this.affectedOSes = affectedOSes;
    }
}
