package none.healthaide.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MedicalRecords implements Parcelable {
    private Integer id;
    private String title;
    private String startDate;
    private String endDate;
    private String medicalRecordsDescribe;
    private String medicalRecordsType;
    private String cureDescription;
    private String hospital;
    private String doctor;
    private String revisitingDate;
    private String photoUriStr;

    protected MedicalRecords(Parcel in) {
        id = in.readInt();
        title = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        medicalRecordsDescribe = in.readString();
        medicalRecordsType = in.readString();
        cureDescription = in.readString();
        hospital = in.readString();
        doctor = in.readString();
        revisitingDate = in.readString();
        photoUriStr = in.readString();
    }

    public MedicalRecords() {
    }

    public static final Creator<MedicalRecords> CREATOR = new Creator<MedicalRecords>() {
        @Override
        public MedicalRecords createFromParcel(Parcel in) {
            return new MedicalRecords(in);
        }

        @Override
        public MedicalRecords[] newArray(int size) {
            return new MedicalRecords[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public MedicalRecords setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MedicalRecords setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getStartDate() {
        return startDate;
    }

    public MedicalRecords setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    public MedicalRecords setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getMedicalRecordsDescribe() {
        return medicalRecordsDescribe;
    }

    public MedicalRecords setMedicalRecordsDescribe(String medicalRecordsDescribe) {
        this.medicalRecordsDescribe = medicalRecordsDescribe;
        return this;
    }

    public String getMedicalRecordsType() {
        return medicalRecordsType;
    }

    public MedicalRecords setMedicalRecordsType(String medicalRecordsType) {
        this.medicalRecordsType = medicalRecordsType;
        return this;
    }

    public String getCureDescription() {
        return cureDescription;
    }

    public MedicalRecords setCureDescription(String cureDescription) {
        this.cureDescription = cureDescription;
        return this;
    }

    public String getHospital() {
        return hospital;
    }

    public MedicalRecords setHospital(String hospital) {
        this.hospital = hospital;
        return this;
    }

    public String getDoctor() {
        return doctor;
    }

    public MedicalRecords setDoctor(String doctor) {
        this.doctor = doctor;
        return this;
    }

    public String getRevisitingDate() {
        return revisitingDate;
    }

    public MedicalRecords setRevisitingDate(String revisitingDate) {
        this.revisitingDate = revisitingDate;
        return this;
    }

    public String getPhotoUriStr() {
        return photoUriStr;
    }

    public MedicalRecords setPhotoUriStr(String photoUriStr) {
        this.photoUriStr = photoUriStr;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(startDate);
        parcel.writeString(endDate);
        parcel.writeString(medicalRecordsDescribe);
        parcel.writeString(medicalRecordsType);
        parcel.writeString(cureDescription);
        parcel.writeString(hospital);
        parcel.writeString(doctor);
        parcel.writeString(revisitingDate);
        parcel.writeString(photoUriStr);
    }
}
