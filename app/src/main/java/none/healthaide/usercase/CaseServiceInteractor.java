package none.healthaide.usercase;

import javax.inject.Inject;

import none.healthaide.data.HealthAidData;
import none.healthaide.model.Case;
import rx.functions.Action1;
import rx.functions.Func1;

import static rx.Observable.just;
import static rx.schedulers.Schedulers.io;

public class CaseServiceInteractor {

    @Inject
    HealthAidData healthAidData;

    public interface Callback {
        void onLoadNewCaseSuccess(long id);

        void onLoadNewCaseFailed();
    }

    public void storeNewCase(Case newCase, final Callback callback) {
        just(newCase).observeOn(io()).map(new Func1<Case, Long>() {
            @Override
            public Long call(Case newCase) {
                return healthAidData.insertCase(newCase);
            }
        }).subscribe(new Action1<Long>() {
            @Override
            public void call(Long id) {
                callback.onLoadNewCaseSuccess(id);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                callback.onLoadNewCaseFailed();
            }
        });
    }
}
