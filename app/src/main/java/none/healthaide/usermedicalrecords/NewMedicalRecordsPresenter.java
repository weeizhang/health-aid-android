package none.healthaide.usermedicalrecords;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import none.healthaide.data.HealthAidContract;
import none.healthaide.model.MedicalRecords;
import rx.Observable;
import rx.Subscriber;

import static rx.android.schedulers.AndroidSchedulers.mainThread;
import static rx.schedulers.Schedulers.io;

public class NewMedicalRecordsPresenter {

    private ContentResolver contentResolver;
    private NewMedicalRecordsView newMedicalRecordsView;

    public NewMedicalRecordsPresenter(ContentResolver contentResolver, NewMedicalRecordsView newMedicalRecordsView) {
        this.contentResolver = contentResolver;
        this.newMedicalRecordsView = newMedicalRecordsView;
    }

    public void submitNewCase() {
        storeNewCase(newMedicalRecordsView.getMedicalRecords());
    }

    private void storeNewCase(final MedicalRecords newMedicalRecords) {
        Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {

                ContentValues values = new ContentValues();
                values.put(HealthAidContract.MedicalRecordsEntry.COLUMN_NAME_TITLE, newMedicalRecords.getTitle());
                values.put(HealthAidContract.MedicalRecordsEntry.COLUMN_NAME_START_DATE, newMedicalRecords.getStartDate());
                values.put(HealthAidContract.MedicalRecordsEntry.COLUMN_NAME_END_DATE, newMedicalRecords.getEndDate());
                values.put(HealthAidContract.MedicalRecordsEntry.COLUMN_NAME_DESCRIPTION, newMedicalRecords.getMedicalRecordsDescribe());
                values.put(HealthAidContract.MedicalRecordsEntry.COLUMN_NAME_TYPE, newMedicalRecords.getMedicalRecordsType());
                values.put(HealthAidContract.MedicalRecordsEntry.COLUMN_NAME_HOSPITAL, newMedicalRecords.getHospital());
                values.put(HealthAidContract.MedicalRecordsEntry.COLUMN_NAME_DOCTOR, newMedicalRecords.getDoctor());
                values.put(HealthAidContract.MedicalRecordsEntry.COLUMN_NAME_CURE_DESCRIPTION, newMedicalRecords.getCureDescription());
                values.put(HealthAidContract.MedicalRecordsEntry.COLUMN_NAME_PHOTO_URI, newMedicalRecords.getPhotoUriStr());
                Uri uri = contentResolver.insert(HealthAidContract.MEDICAL_RECORDS_TABLE_CONTENT_URI, values);

                if (uri != null) {
                    String idStr = uri.getPathSegments().get(1);
                    subscriber.onNext(Integer.valueOf(idStr).longValue());
                } else {
                    subscriber.onError(new Exception());
                }
            }
        })
                .subscribeOn(io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        newMedicalRecordsView.loadFailed();
                    }

                    @Override
                    public void onNext(Long id) {
                        newMedicalRecordsView.loadSuccess(id);
                    }
                });
    }
}
