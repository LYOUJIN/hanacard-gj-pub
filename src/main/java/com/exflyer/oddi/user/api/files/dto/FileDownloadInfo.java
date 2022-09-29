package com.exflyer.oddi.user.api.files.dto;

import lombok.Data;

@Data
public class FileDownloadInfo {

    private String name;

    private byte[] contents;
}

