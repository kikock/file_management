package com.kiko.file.manager.model;


public class ConfigRoot {

    private ConfigSecurity security;

    private ConfigUpload upload;

    public ConfigSecurity getSecurity() {
        return security;
    }

    public void setSecurity(ConfigSecurity security) {
        this.security = security;
    }

    public ConfigUpload getUpload() {
        return upload;
    }

    public void setUpload(ConfigUpload upload) {
        this.upload = upload;
    }
}
