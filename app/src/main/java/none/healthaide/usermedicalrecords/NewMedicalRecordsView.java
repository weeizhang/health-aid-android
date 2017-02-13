package none.healthaide.usermedicalrecords;

import none.healthaide.model.MedicalRecords;

public interface NewMedicalRecordsView {
    MedicalRecords getMedicalRecords();

    void loadSuccess(long id);

    void loadFailed();
}
