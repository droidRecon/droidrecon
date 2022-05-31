package com.iiitmk.project.droidrecon;

import java.util.List;

public class SubDomain {
    String SubDomainName;
    String Status;
    String Methods;
    String Technology;
    String Whois;
    String DNS;
    List<Port> portList;


    public SubDomain() {
    }

    public SubDomain(String subDomainName, String status, String methods, String technology, String whois, String DNS, List<Port> portList) {
        SubDomainName = subDomainName;
        Status = status;
        Methods = methods;
        Technology = technology;
        Whois = whois;
        this.DNS = DNS;
        this.portList = portList;
    }

    public String getSubDomainName() {
        return SubDomainName;
    }

    public void setSubDomainName(String subDomainName) {
        SubDomainName = subDomainName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMethods() {
        return Methods;
    }

    public void setMethods(String methods) {
        Methods = methods;
    }

    public String getTechnology() {
        return Technology;
    }

    public void setTechnology(String technology) {
        Technology = technology;
    }

    public String getWhois() {
        return Whois;
    }

    public void setWhois(String whois) {
        Whois = whois;
    }

    public String getDNS() {
        return DNS;
    }

    public void setDNS(String DNS) {
        this.DNS = DNS;
    }

    public List<Port> getPortList() {
        return portList;
    }

    public void setPortList(List<Port> portList) {
        this.portList = portList;
    }
}
