package none.healthaide;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import none.healthaide.data.HealthAidData;

@Module
public class HealthAidModule {
    private Context context;

    public HealthAidModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Context providesContext() {
        return context;
    }

    @Provides
    @Singleton
    public HealthAidData providesHealthAidData(Context context) {
        return new HealthAidData(context);
    }
}
