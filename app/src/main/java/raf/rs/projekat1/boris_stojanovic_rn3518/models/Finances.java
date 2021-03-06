package raf.rs.projekat1.boris_stojanovic_rn3518.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Finances implements Parcelable {

    private int id;

    private String description;
    private boolean isIncome;
    private boolean isAudio;
    private String title;
    private Integer quantity;

    public Finances(int id, String description, boolean isIncome, boolean isAudio, String title, Integer quantity) {
        this.id = id;
        this.description = description;
        this.isIncome = isIncome;
        this.title = title;
        this.quantity = quantity;
        this.isAudio = isAudio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIncome() {
        return isIncome;
    }

    public void setIncome(boolean income) {
        isIncome = income;
    }

    public void setAudio(boolean audio) {
        isAudio = audio;
    }

    public boolean isAudio() {
        return isAudio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.quantity);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeInt(this.isIncome? 1:0);
        dest.writeInt(this.isAudio? 1:0);
    }

    public static final Parcelable.Creator<Finances> CREATOR
            = new Parcelable.Creator<Finances>() {
        public Finances createFromParcel(Parcel in) {
            return new Finances(in);
        }

        public Finances[] newArray(int size) {
            return new Finances[size];
        }
    };

    private Finances(Parcel in) {
        this.id = in.readInt();
        this.quantity = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        int income = in.readInt();
        if(income == 0){
            this.isIncome = false;
        }else{
            this.isIncome = true;
        }
        int audio = in.readInt();
        if(audio == 0){
            this.isAudio = false;
        }else{
            this.isAudio = true;
        }
    }
}
