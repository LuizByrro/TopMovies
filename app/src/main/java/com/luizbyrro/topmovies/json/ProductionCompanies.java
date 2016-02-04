package com.luizbyrro.topmovies.json;

import java.io.Serializable;

/**
 * Created by Luiz Byrro on 03/02/2016.
 */
public class ProductionCompanies implements Serializable {


    private String id;
    private String name;

    public ProductionCompanies() {
    }

    public ProductionCompanies(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ProductionCompanies{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
