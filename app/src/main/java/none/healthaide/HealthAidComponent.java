package none.healthaide;


import javax.inject.Singleton;

import dagger.Component;
import none.healthaide.revisting.RevisitingFragment;
import none.healthaide.usercase.NewCaseFragment;

@Singleton
@Component(modules = {HealthAidModule.class})
public interface HealthAidComponent {
    void inject(HealthAidApplication application);

    void inject(NewCaseFragment newCaseFragment);

    void inject(RevisitingFragment revisitingFragment);
}
