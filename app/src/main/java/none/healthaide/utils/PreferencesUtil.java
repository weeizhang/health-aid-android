package none.healthaide.utils;


import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesUtil {
    private static final String PREF_NAME = "health_aid_pref";
    private static final String USER_GENDER = "user_gender";
    private static final String USER_AGE = "user_age";
    private static final String USER_HEIGHT = "user_height";
    private static final String USER_WEIGHT = "user_weight";
    private static Context context;
    private static SharedPreferences sharedPreferences;

    public static void init(Context ctx) {
        context = ctx;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static String getUserGender() {
        return sharedPreferences.getString(USER_GENDER, "女");
    }

    public static void setUserGender(String gender) {
        sharedPreferences.edit().putString(USER_GENDER, gender).apply();
    }

    public static int getUserAge() {
        return sharedPreferences.getInt(USER_AGE, 0);
    }

    public static void setUserAge(int age) {
        sharedPreferences.edit().putInt(USER_AGE, age).apply();
    }

    public static int getUserHeight() {
        return sharedPreferences.getInt(USER_HEIGHT, 0);
    }

    public static void setUserHeight(int height) {
        sharedPreferences.edit().putInt(USER_HEIGHT, height).apply();
    }

    public static int getUserWeight() {
        return sharedPreferences.getInt(USER_WEIGHT, 0);
    }

    public static void setUserWeight(int weight) {
        sharedPreferences.edit().putInt(USER_WEIGHT, weight).apply();
    }
}
