package com.entelgy.mediapro.spaininaday.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryRequest {

    private Category[] data;

    public CategoryRequest() {

    }

    public Category[] getData() {
        return data;
    }

    public void setData(Category[] data) {
        this.data = data;
    }
}
