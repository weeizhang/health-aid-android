package none.healthaide;


import javax.inject.Singleton;

import dagger.Component;
import none.healthaide.data.HealthAidData;
import none.healthaide.usercase.CaseService;
import none.healthaide.usercase.CaseServiceInteractor;
import none.healthaide.usercase.NewCaseFragment;

@Singleton
@Component(modules = {HealthAidModule.class})
public interface HealthAidComponent {
    HealthAidData getHealthAidData();
    void inject(HealthAidApplication application);
    void inject(CaseServiceInteractor caseServiceInteractor);
    void inject(NewCaseFragment newCaseFragment);
    void inject(HealthAidData healthAidData);
    void inject(CaseService caseService);
}
