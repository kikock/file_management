package com.kiko.file.manager.model;


public class InitiateData {

    private String id = "/";

    private String type = "initiate";

    private InitiateAttributes attributes;

    public String getId() {
        return id;
    }

    public InitiateAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(InitiateAttributes attributes) {
        this.attributes = attributes;
    }

    public String getType() {
        return type;
    }
}
