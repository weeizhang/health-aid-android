package none.healthaide;

import android.app.Application;

import com.facebook.stetho.Stetho;

import none.healthaide.utils.PreferencesUtil;

public class HealthAidApplication extends Application {

    private HealthAidComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = createComponent();
        component.inject(this);
        Stetho.initializeWithDefaults(this);
        PreferencesUtil.init(this);
    }

    public HealthAidComponent getComponent() {
        return component;
    }

    protected HealthAidComponent createComponent() {
        return DaggerHealthAidComponent.builder().healthAidModule(new HealthAidModule(this)).build();
    }
}
