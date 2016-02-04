package com.luizbyrro.topmovies.json;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Luiz Byrro on 03/02/2016.
 */
public class RetornoTMDB implements Serializable {

    private FilmeInfo[] results;
    private int page;
    private int total_results;
    private int total_pages;

    public RetornoTMDB(FilmeInfo[] results, int page, int total_results, int total_pages) {
        this.results = results;
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
    }

    public RetornoTMDB() {
    }

    public FilmeInfo[] getResults() {
        return results;
    }

    public void setResults(FilmeInfo[] results) {
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    @Override
    public String toString() {
        return "RetornoTMDB{" +
                "results=" + Arrays.toString(results) +
                ", page=" + page +
                ", total_results=" + total_results +
                ", total_pages=" + total_pages +
                '}';
    }
}
