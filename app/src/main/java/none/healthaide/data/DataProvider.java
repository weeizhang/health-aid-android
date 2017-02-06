package none.healthaide.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.Nullable;

import none.healthaide.data.HealthAidContract.CaseEntry;
import none.healthaide.data.HealthAidContract.RevisitingEventEntry;

public class DataProvider extends ContentProvider {

    public static final int CASE_QUERY = 1;
    public static final int REVISITING_EVENT_QUERY = 2;

    private static final UriMatcher uriMatcher;

    private HealthAidDbHelper healthAidDbHelper;
    private ContentResolver resolver;

    static {
        uriMatcher = new UriMatcher(0);

        uriMatcher.addURI(
                HealthAidContract.AUTHORITY,
                CaseEntry.TABLE_NAME,
                CASE_QUERY);
        uriMatcher.addURI(
                HealthAidContract.AUTHORITY,
                RevisitingEventEntry.TABLE_NAME,
                REVISITING_EVENT_QUERY);
    }

    @Override
    public boolean onCreate() {
        healthAidDbHelper = new HealthAidDbHelper(getContext());
        resolver = getContext().getContentResolver();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        QueryParams queryParams = getQueryParams(uri, selection, projection);
        Cursor res = healthAidDbHelper.getReadableDatabase().query(queryParams.tablesWithJoins, projection, queryParams.selection, selectionArgs, null,
                null, sortOrder == null ? queryParams.orderBy : sortOrder);
        res.setNotificationUri(getContext().getContentResolver(), uri);
        return res;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String table = uri.getLastPathSegment();
        long rowId = healthAidDbHelper.getWritableDatabase().insert(table, null, values);
        if (rowId < 0) {
            throw new SQLiteException("Unable insert " + values + "for" + uri);
        }
        Uri newUri = ContentUris.withAppendedId(uri, rowId);
        resolver.notifyChange(newUri, null);
        return newUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        return 0;
    }

    private static class QueryParams {
        public String table;
        public String tablesWithJoins;
        public String selection;
        public String orderBy;
    }

    private QueryParams getQueryParams(Uri uri, String selection, String[] projection) {
        QueryParams params = new QueryParams();
        int matchedId = uriMatcher.match(uri);
        switch (matchedId) {
            case CASE_QUERY:
                params.table = CaseEntry.TABLE_NAME;
                params.tablesWithJoins = CaseEntry.TABLE_NAME;
                params.selection = selection;
                break;
            case REVISITING_EVENT_QUERY:
                params.table = RevisitingEventEntry.TABLE_NAME;
                params.tablesWithJoins = RevisitingEventEntry.TABLE_NAME;
                params.tablesWithJoins += " JOIN " + CaseEntry.TABLE_NAME + " ON " + CaseEntry.TABLE_NAME + "." + CaseEntry._ID + "=" + RevisitingEventEntry.TABLE_NAME + "." + RevisitingEventEntry.COLUMN_NAME_CASE_ID;
                break;
            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }
        return params;
    }
}
