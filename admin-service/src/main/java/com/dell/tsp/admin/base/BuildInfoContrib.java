package com.dell.tsp.admin.base;

import java.util.Map;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;

public class BuildInfoContrib implements InfoContributor {

    private final Map<String, Object> details;

    public BuildInfoContrib(Map<String, Object> details) {
        this.details = details;
    }

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetails(details);
    }
    
}

