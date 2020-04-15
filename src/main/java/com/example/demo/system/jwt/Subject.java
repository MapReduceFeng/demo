package com.example.demo.system.jwt;

import java.util.List;

public class Subject {
    private String subject;
    private String claim;
    private String issuer;
    private String audience;
    private List<String> arrayClaim;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getClaim() {
        return claim;
    }

    public void setClaim(String claim) {
        this.claim = claim;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public List<String> getArrayClaim() {
        return arrayClaim;
    }

    public void setArrayClaim(List<String> arrayClaim) {
        this.arrayClaim = arrayClaim;
    }
}
