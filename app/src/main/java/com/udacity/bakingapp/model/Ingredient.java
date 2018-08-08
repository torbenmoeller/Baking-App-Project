package com.udacity.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ingredient implements Parcelable {

    @JsonProperty("ingredient")
    private String ingredient;
    @JsonProperty("measure")
    private String measure;
    @JsonProperty("quantity")
    private int quantity;

    public Ingredient() {
        this.ingredient = "";
        this.measure = "";
        this.quantity = 0;
    }

    public Ingredient(Parcel in) {
        this.ingredient = in.readString();
        this.measure = in.readString();
        this.quantity = in.readInt();
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ingredient);
        dest.writeString(this.measure);
        dest.writeInt(this.quantity);
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}