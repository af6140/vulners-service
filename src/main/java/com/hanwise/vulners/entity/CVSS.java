package com.hanwise.vulners.entity;

import javax.persistence.Column;
import java.io.Serializable;

public class CVSS implements Serializable{
    @Column(name="cvss_score")
    private float score;
    @Column(name="cvss_vector")
    private String vector;

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getVector() {
        return vector;
    }

    public void setVector(String vector) {
        this.vector = vector;
    }
}
