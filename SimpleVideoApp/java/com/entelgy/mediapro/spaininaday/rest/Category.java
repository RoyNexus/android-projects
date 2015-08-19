package com.entelgy.mediapro.spaininaday.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {

    private String name;
    private Subcategory[] subcategories;

    public Category() {

    }

    public Subcategory[] getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(Subcategory[] subcategories) {
        this.subcategories = subcategories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
