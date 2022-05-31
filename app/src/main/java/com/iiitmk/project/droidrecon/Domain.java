package com.iiitmk.project.droidrecon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Domain {
    String domainName;
    List<SubDomain> subDomainList;

    public Domain() {
    }

    public Domain(String domainName, List<SubDomain> subDomainList) {
        this.domainName = domainName;
        this.subDomainList = subDomainList;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public List<SubDomain> getSubDomainList() {
        return subDomainList;
    }

    public void setSubDomainList(List<SubDomain> subDomainList) {
        this.subDomainList = subDomainList;
    }
}
