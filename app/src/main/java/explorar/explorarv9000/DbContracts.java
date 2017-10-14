package explorar.explorarv9000;

import android.provider.BaseColumns;
import java.util.GregorianCalendar;
import java.util.Date;

/**
 * Created by benja on 17/09/2017.
 */

public final class DbContracts {

    // student database contract
    public static class studentDBentry implements BaseColumns { // implementing BaseColumns automatically creates a unique id for each entry
        public static final String TABLE_NAME = "studentDB";
        public static final String COLUMN_zID = "zID";
        public static final String COLUMN_NAME_STUDENT = "studentFullName";
        protected static final String COLUMN_PASSWORD_STUDENT = "studentPassword";
        public static final String COLUMN_EMAIL_STUDENT = "studentEmail";
        public static final String COLUMN_DEGREE_STUDENT = "studentDegree";
    }

    // organisation database contract
    public static class organisationsDBentry implements BaseColumns {
        public static final String TABLE_NAME = "organisationDB";
        public static final String COLUMN_NAME_ORG = "organiserName";
        public static final String COLUMN_PASSWORD_ORG = "organiserPassword";
        public static final String COLUMN_EMAIL_ORG = "organiserEmail";
    }

    // events database contract
    public static class eventsDBentry implements BaseColumns {
        public static final String TABLE_NAME = "eventsDB";
        public static final String COLUMN_NAME_EVENT = "eventName";
        public static final String COLUMN_NAME_HOSTORG = "hostOrganisation";
        public static final String COLUMN_LOCATION_EVENT = "eventLocation";
        public static final String COLUMN_DATE_EVENT = "eventDate";
        public static final String COLUMN_STARTTIME_EVENT = "eventStartTime";
        public static final String COLUMN_ENDTIME_EVENT = "eventEndTime";
        public static final String COLUMN_PRICE_EVENT = "eventPice";
        public static final String COLUMN_NAME_DESCRIPTION = "eventDescription";
        public static final String COLUMN_LATITUDE_EVENT = "eventLatitude";
        public static final String COLUMN_LONGITUDE_EVENT = "eventLongitude";
        public static final String COLUMN_EVENT_TYPE = "eventType";
    }

    //TODO: image db
    // organisation database contract
    public static class imageDBentry implements BaseColumns {
        public static final String TABLE_NAME = "imageDB";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_DESCRIPTION = "description";
    }
}