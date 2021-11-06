package org.neogroup.warp.http;

import java.util.Map;

public class MultipartItem {

    private Map<String,String> headers;

    private byte[] content;

    public MultipartItem (Map<String,String> headers, byte[] content) {
        this.headers = headers;
        this.content = content;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public boolean hasHeader(String name) {
        return headers.containsKey(name);
    }

    public byte[] getContent() {
        return content;
    }
}
