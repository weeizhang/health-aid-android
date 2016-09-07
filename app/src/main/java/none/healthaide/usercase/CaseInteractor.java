package none.healthaide.usercase;

import none.healthaide.data.HealthAidData;
import none.healthaide.model.Case;
import rx.SingleSubscriber;
import rx.functions.Func1;

import static rx.Single.just;
import static rx.android.schedulers.AndroidSchedulers.mainThread;
import static rx.schedulers.Schedulers.io;

public class CaseInteractor {

    private HealthAidData healthAidData;

    public CaseInteractor(HealthAidData healthAidData) {
        this.healthAidData = healthAidData;
    }

    public interface Callback {
        void onLoadNewCaseSuccess(long id);

        void onLoadNewCaseFailed();
    }

    public void storeNewCase(final Case newCase, final Callback callback) {
        just("").observeOn(io())
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
