package com.luizbyrro.topmovies.json;

import android.widget.ImageView;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Luiz Byrro on 03/02/2016.
 */
public class RetornoConfiguration implements Serializable{

    private Images images;
    private String[] change_keys;

    public RetornoConfiguration(Images images, String[] change_keys) {
        this.images = images;
        this.change_keys = change_keys;
    }

    public RetornoConfiguration() {

        this.images = new Images();
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String[] getChange_keys() {
        return change_keys;
    }

    public void setChange_keys(String[] change_keys) {
        this.change_keys = change_keys;
    }

    @Override
    public String toString() {
        return "RetornoConfiguration{" +
                "images=" + images +
                ", change_keys=" + Arrays.toString(change_keys) +
                '}';
    }
}
