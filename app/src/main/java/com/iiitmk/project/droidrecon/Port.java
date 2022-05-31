package com.iiitmk.project.droidrecon;

public class Port {
    String portNo;
    String service;
    String banner;

    public Port() {
    }

    public Port(String portNo, String service, String banner) {
        this.portNo = portNo;
        this.service = service;
        this.banner = banner;
    }

    public String getPortNo() {
        return portNo;
    }

    public void setPortNo(String portNo) {
        this.portNo = portNo;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
