package none.healthaide.data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class HealthAidContract {

    public static final String SCHEME = "content";
    public static final String AUTHORITY = "none.healthaide.data";
    public static final Uri CONTENT_URI = Uri.parse(SCHEME + "://" + AUTHORITY);

    public static final Uri CASE_TABLE_CONTENTURI =
            Uri.withAppendedPath(CONTENT_URI, CaseEntry.TABLE_NAME);

    public HealthAidContract() {
    }

    public static abstract class CaseEntry implements BaseColumns {
        public static final String TABLE_NAME = "user_case";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_START_DATE = "start_date";
        public static final String COLUMN_NAME_END_DATE = "end_date";
        public static final String COLUMN_NAME_HOSPITAL = "hospital";
        public static final String COLUMN_NAME_DOCTOR = "doctor";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_CURE_DESCRIPTION = "cure_description";
    }
}
