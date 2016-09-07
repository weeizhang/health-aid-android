package none.healthaide.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

public class DataProvider extends ContentProvider {

    public static final int CASE_QUERY = 1;
    public static final int INVALID_URI = -1;

    private static final UriMatcher uriMatcher;

    private HealthAidDbHelper healthAidDbHelper;

    static {
        uriMatcher = new UriMatcher(0);

        uriMatcher.addURI(
                HealthAidContract.AUTHORITY,
                HealthAidContract.CaseEntry.TABLE_NAME,
                CASE_QUERY);
    }

    @Override
    public boolean onCreate() {
        healthAidDbHelper = new HealthAidDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case CASE_QUERY:
                Cursor cursor = healthAidDbHelper.getReadableDatabase().query(
                        HealthAidContract.CaseEntry.TABLE_NAME,
                        projection,
                        null, null, null, null, null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            case INVALID_URI:
                throw new IllegalArgumentException("Query -- Invalid URI:" + uri);
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        return 0;
    }
}
