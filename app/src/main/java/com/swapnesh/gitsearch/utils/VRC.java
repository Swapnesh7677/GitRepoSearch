package com.swapnesh.gitsearch.utils;

import java.util.HashMap;
import java.util.Map;

public class VRC {
    private String url;
    private Map<String, String> params = new HashMap<>();

    public VRC(String url, Map<String, String> params) {
        this.url = url;
        this.params.putAll(params);
    }

    public String getRequestedUrl() {
        StringBuilder new_url = new StringBuilder(url + "?");

        for (int i = 0; i < params.size(); i++) {
            String key = (params.keySet().toArray())[i].toString();
            new_url.append(key).append("=").append(params.get(key)).append("&");
        }

        new_url = new StringBuilder(new_url.substring(0, new_url.length() - 1));
        return new_url.toString();
    }
}



