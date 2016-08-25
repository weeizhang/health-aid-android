package none.healthaide.usercase;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import none.healthaide.HealthAidApplication;
import none.healthaide.model.Case;

public class CaseService extends IntentService implements CaseServiceInteractor.Callback {
    public static final String TAG = CaseService.class.getSimpleName();

    public static final String ACTION_NEW_CASE = "CASE_SERVICE_ACTION_NEW_CASE";
    public static final String NEW_CASE_EXTRA = "NEW_CASE_EXTRA";

    private CaseServiceInteractor caseServiceInteractor;

    public CaseService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        caseServiceInteractor = new CaseServiceInteractor();
        ((HealthAidApplication) getApplication()).getComponent().inject(caseServiceInteractor);
        ((HealthAidApplication) getApplication()).getComponent().inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String intentAction = intent.getAction();
        if (ACTION_NEW_CASE.equals(intentAction)) {
            caseServiceInteractor.storeNewCase((Case) intent.getParcelableExtra(NEW_CASE_EXTRA), this);
        }
    }

    @Override
    public void onLoadNewCaseSuccess(long id) {
        Log.d("wzhang", "insert success " + id);
    }

    @Override
    public void onLoadNewCaseFailed() {

    }
}
