package none.healthaide.data;

import android.provider.BaseColumns;

public final class CaseContract {

    public CaseContract() {
    }

    public static abstract class CaseEntry implements BaseColumns {
        public static final String TABLE_NAME = "case";
        public static final String COLUMN_NAME_CASE_ID = "case_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_START_DATE = "start_date";
        public static final String COLUMN_NAME_END_DATE = "end_date";
        public static final String COLUMN_NAME_HOSPITAL = "hospital";
        public static final String COLUMN_NAME_DOCTORE = "doctor";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_CURE_DEACRIPTION = "cure_description";
    }
}
