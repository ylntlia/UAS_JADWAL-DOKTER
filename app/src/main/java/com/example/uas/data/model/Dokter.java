package com.example.uas.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Dokter implements Parcelable {
    private Integer id;
    private String nama;
    private String ahli;
    private String keterangan;

    public Dokter() {
        super();
    }

    public Dokter(Integer id, String nama, String ahli, String keterangan) {
        super();
        this.id = id;
        this.nama = nama;
        this.ahli = ahli;
        this.keterangan = keterangan;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAhli() {
        return ahli;
    }

    public void setAhli(String ahli) {
        this.ahli = ahli;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.nama);
        dest.writeString(this.ahli);
        dest.writeString(this.keterangan);
    }

    protected Dokter(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.nama = in.readString();
        this.ahli = in.readString();
        this.keterangan = in.readString();
    }

    public static final Creator<Dokter> CREATOR = new Creator<Dokter>() {
        @Override
        public Dokter createFromParcel(Parcel source) {
            return new Dokter(source);
        }

        @Override
        public Dokter[] newArray(int size) {
            return new Dokter[size];
        }
    };
}
