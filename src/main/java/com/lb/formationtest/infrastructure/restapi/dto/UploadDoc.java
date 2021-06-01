package com.lb.formationtest.infrastructure.restapi.dto;

import java.util.List;

public class UploadDoc {

    public final List<String> urls;

    public UploadDoc(List<String> urls) {
        this.urls = urls;
    }
}
