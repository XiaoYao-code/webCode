package com.peixinchen.model;

import java.io.InputStream;

public class Audio {
    public String contentType;
    public InputStream inputStream;

    public Audio(String type, InputStream content) {
        this.contentType = type;
        this.inputStream = content;
    }
}
