package none.healthaide.usercase;

import javax.inject.Inject;

import none.healthaide.data.HealthAidData;
import none.healthaide.model.Case;
import rx.Single;
import rx.SingleSubscriber;
import rx.functions.Func1;

import static rx.android.schedulers.AndroidSchedulers.mainThread;
import static rx.schedulers.Schedulers.io;

public class CaseServiceInteractor {

    @Inject
    HealthAidData healthAidData;

    public interface Callback {
        void onLoadNewCaseSuccess(long id);

        void onLoadNewCaseFailed();
    }

    public void storeNewCase(final Case newCase, final Callback callback) {
        Single.just("").observeOn(io())
                .map(new Func1<String, Long>() {
                    @Override
                    public Long call(String s) {
                        return healthAidData.insertCase(newCase);
                    }
                })
                .observeOn(mainThread())
                .subscribe(new SingleSubscriber<Long>() {
                    @Override
                    public void onSuccess(Long id) {
                        callback.onLoadNewCaseSuccess(id);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onLoadNewCaseFailed();
                    }
                });
    }
}
