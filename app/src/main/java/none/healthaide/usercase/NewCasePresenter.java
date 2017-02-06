package none.healthaide.usercase;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import none.healthaide.data.HealthAidContract;
import none.healthaide.model.Case;
import rx.Observable;
import rx.Subscriber;

import static rx.android.schedulers.AndroidSchedulers.mainThread;
import static rx.schedulers.Schedulers.io;

public class NewCasePresenter {

    private ContentResolver contentResolver;
    private NewCaseView newCaseView;

    public NewCasePresenter(ContentResolver contentResolver, NewCaseView newCaseView) {
        this.contentResolver = contentResolver;
        this.newCaseView = newCaseView;
    }

    public void submitNewCase() {
        storeNewCase(newCaseView.getCase());
    }

    private void storeNewCase(final Case newCase) {
        Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {

                ContentValues values = new ContentValues();
                values.put(HealthAidContract.CaseEntry.COLUMN_NAME_TITLE, newCase.getTitle());
                values.put(HealthAidContract.CaseEntry.COLUMN_NAME_START_DATE, newCase.getStartDate());
                values.put(HealthAidContract.CaseEntry.COLUMN_NAME_END_DATE, newCase.getEndDate());
                values.put(HealthAidContract.CaseEntry.COLUMN_NAME_DESCRIPTION, newCase.getCaseDescribe());
                values.put(HealthAidContract.CaseEntry.COLUMN_NAME_TYPE, newCase.getCaseType());
                values.put(HealthAidContract.CaseEntry.COLUMN_NAME_HOSPITAL, newCase.getHospital());
                values.put(HealthAidContract.CaseEntry.COLUMN_NAME_DOCTOR, newCase.getDoctor());
                values.put(HealthAidContract.CaseEntry.COLUMN_NAME_CURE_DESCRIPTION, newCase.getCureDescription());
                values.put(HealthAidContract.CaseEntry.COLUMN_NAME_PHOTO_URI, newCase.getPhotoUriStr());
                Uri uri = contentResolver.insert(HealthAidContract.CASE_TABLE_CONTENT_URI, values);

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
                        newCaseView.loadFailed();
                    }

                    @Override
                    public void onNext(Long id) {
                        newCaseView.loadSuccess(id);
                    }
                });
    }
}
