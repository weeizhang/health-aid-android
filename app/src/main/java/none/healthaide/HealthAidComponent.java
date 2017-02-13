package none.healthaide;


import javax.inject.Singleton;

import dagger.Component;
import none.healthaide.revisting.RevisitingFragment;
import none.healthaide.usermedicalrecords.NewMedicalRecordsFragment;

@Singleton
@Component(modules = {HealthAidModule.class})
public interface HealthAidComponent {
    void inject(HealthAidApplication application);

    void inject(NewMedicalRecordsFragment newMedicalRecordsFragment);

    void inject(RevisitingFragment revisitingFragment);
}
