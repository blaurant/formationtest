package com.lb.formationtest.infrastructure.restapi.adaptor;

import com.lb.formationtest.domain.Document;
import com.lb.formationtest.domain.Documents;
import com.lb.formationtest.infrastructure.restapi.dto.UploadDoc;

import static java.util.stream.Collectors.toSet;


public class UploadDocAdaptor {

    public final UploadDoc uploadDocDto;

    public UploadDocAdaptor(UploadDoc uploadDocDto) {
        this.uploadDocDto = uploadDocDto;
    }

    public static UploadDocAdaptor toDomain(UploadDoc uploadDocDto) {
        return new UploadDocAdaptor(uploadDocDto);
    }

    public Documents documents() {
        return new Documents(
                uploadDocDto.urls.stream()
                        .map(urlStr -> new Document(urlStr))
                        .collect(toSet()));
    }
}
