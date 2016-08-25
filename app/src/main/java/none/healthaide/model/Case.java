package none.healthaide.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Case implements Parcelable {
    private String title;
    private String startDate;
    private String endDate;
    private String caseDescribe;
    private String caseType;
    private String cureDescription;
    private String hospital;
    private String doctor;

    protected Case(Parcel in) {
        title = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        caseDescribe = in.readString();
        caseType = in.readString();
        cureDescription = in.readString();
        hospital = in.readString();
        doctor = in.readString();
    }

    public Case() {
    }

    public static final Creator<Case> CREATOR = new Creator<Case>() {
        @Override
        public Case createFromParcel(Parcel in) {
            return new Case(in);
        }

        @Override
        public Case[] newArray(int size) {
            return new Case[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public Case setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getStartDate() {
        return startDate;
    }

    public Case setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    public Case setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getCaseDescribe() {
        return caseDescribe;
    }

    public Case setCaseDescribe(String caseDescribe) {
        this.caseDescribe = caseDescribe;
        return this;
    }

    public String getCaseType() {
        return caseType;
    }

    public Case setCaseType(String caseType) {
        this.caseType = caseType;
        return this;
    }

    public String getCureDescription() {
        return cureDescription;
    }

    public Case setCureDescription(String cureDescription) {
        this.cureDescription = cureDescription;
        return this;
    }

    public String getHospital() {
        return hospital;
    }

    public Case setHospital(String hospital) {
        this.hospital = hospital;
        return this;
    }

    public String getDoctor() {
        return doctor;
    }

    public Case setDoctor(String doctor) {
        this.doctor = doctor;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(startDate);
        parcel.writeString(endDate);
        parcel.writeString(caseDescribe);
        parcel.writeString(caseType);
        parcel.writeString(cureDescription);
        parcel.writeString(hospital);
        parcel.writeString(doctor);
    }
}
