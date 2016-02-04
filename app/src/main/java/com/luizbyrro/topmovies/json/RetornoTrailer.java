package com.luizbyrro.topmovies.json;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Luiz Byrro on 04/02/2016.
 */
public class RetornoTrailer implements Serializable{
    private Trailer[] results;
    private String id;

    public RetornoTrailer(Trailer[] results, String id) {
        this.results = results;
        this.id = id;
    }

    public RetornoTrailer() {
    }

    public Trailer[] getResults() {
        return results;
    }

    public void setResults(Trailer[] results) {
        this.results = results;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RetornoTrailer{" +
                "results=" + Arrays.toString(results) +
                ", id='" + id + '\'' +
                '}';
    }
}
