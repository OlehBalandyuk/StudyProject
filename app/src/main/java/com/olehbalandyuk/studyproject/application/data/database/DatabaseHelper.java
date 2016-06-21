package com.olehbalandyuk.studyproject.application.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * StudyProject local database
 *
 * Have tables:
 * logged state
 * tokens
 * user info
 * packet
 * channels
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name and version
    // Database version should be incremented each time the database is changed
    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "user_info.db";

    // Table, which contains user's logged state
    static final String LOGGED_STATE_TABLE = "logged_table";
    static final String LOGGED_IN_APP = "is_logged";
    static final String LOGGED_IN_PACKED = "is_logged_by_packet";

    //Table, which contains tokens
    static final String TOKENS_TABLE = "tokens";
    static final String LOGIN_ACCESS_TOKEN = "login_token";
    static final String LOGIN_REFRESH_TOKEN = "refresh_token";
    static final String PACKET_ACCESS_TOKEN = "access_token";
    static final String PACKET_REFRESH_TOKEN = "packet_refresh_token";

    /**
     * Name of user info table.
     *
     *
     * <br/>
     * Table contains such columns as: id, email, name, balance, bonus.
     * <br/>
     * @see #USER_ID
     * @see #USER_EMAIL
     * @see #USER_NAME
     * @see #USER_BALANCE
     * @see #USER_BONUS
     */
    static final String USER_INFO_TABLE = "info";
    static final String USER_ID = "id";
    static final String USER_EMAIL = "email";
    static final String USER_NAME = "name";
    static final String USER_BALANCE = "balance";
    static final String USER_BONUS = "bonus";

    /**
     * Name of packet info table.
     *
     *
     * <br/>
     * Table contains such columns as: id, title, password, date end, status.
     * <br/>
     * @see #PACKET_ID
     * @see #PACKET_TITLE
     * @see #PACKET_PASSWORD
     * @see #PACKET_DATE_END
     * @see #PACKET_STATUS
     */
    static final String PACKET_TABLE = "packets";
    static final String PACKET_ID = "id";
    static final String PACKET_TITLE = "title";
    static final String PACKET_PASSWORD = "password";
    static final String PACKET_DATE_END = "date_end";
    static final String PACKET_STATUS = "status";
    private static final String PACKET_USER_EMAIL = "user_email";

    /**
     * Name of channels info table.
     *
     *
     * <br/>
     * Table contains such columns as: id, url, title, genre id, number, archive, archive range,
     * prv (no idea what is it), censored (boolean), favorite(boolean), logo, date end,
     * monitoring_status(boolean).
     * <br/>
     * @see #CHANNEL_ID
     * @see #CHANNEL_URL
     * @see #CHANNEL_TITLE
     * @see #CHANNEL_GENRE_ID
     * @see #CHANNEL_NUMBER
     * @see #CHANNEL_ARCHIVE
     * @see #CHANNEL_ARCHIVE_RANGE
     * @see #CHANNEL_PVR
     * @see #CHANNEL_CENSORED
     * @see #CHANNEL_FAVORITE
     * @see #CHANNEL_LOGO
     * @see #CHANNEL_MONITORING_STATUS
     *
     */
    static final String CHANNELS_TABLE = "tv_channels";
    static final String CHANNEL_ID = "_id";
    static final String CHANNEL_URL = "url";
    static final String CHANNEL_TITLE = "title";
    static final String CHANNEL_GENRE_ID = "genre_id";
    static final String CHANNEL_NUMBER = "number";
    static final String CHANNEL_ARCHIVE = "archive";
    static final String CHANNEL_ARCHIVE_RANGE = "archive_range";
    static final String CHANNEL_PVR = "pvr";
    static final String CHANNEL_CENSORED = "censored";
    static final String CHANNEL_FAVORITE = "favorite";
    static final String CHANNEL_LOGO = "logo";
    static final String CHANNEL_MONITORING_STATUS = "monitoring_status";
    private static final String CHANNEL_PACKET_ID = "packet_id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        declareTokensTable(db);
        declareUserDataTable(db);
        declareLoggedStateTable(db);
        declarePacketTable(db);
        declareChannelsTable(db);

        fillDatabaseWithDefaultValues(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropExistingTables(db);

        onCreate(db);
    }

    static void logout (SQLiteDatabase database) {
        dropExistingTables(database);

        declareTokensTable(database);
        declareUserDataTable(database);
        declareLoggedStateTable(database);
        declarePacketTable(database);
        declareChannelsTable(database);

        fillDatabaseWithDefaultValues(database);

    }

    // Creating logged state table in the database
    private static void declareLoggedStateTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + LOGGED_STATE_TABLE + "(" +
                LOGGED_IN_APP + " TEXT, " +
                LOGGED_IN_PACKED + " TEXT " +
                ")");
    }

    // Creating Token's table in the database
    private static void declareTokensTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TOKENS_TABLE + "(" +
                LOGIN_ACCESS_TOKEN + " TEXT, " +
                LOGIN_REFRESH_TOKEN + " TEXT, " +
                PACKET_ACCESS_TOKEN + " TEXT, " +
                PACKET_REFRESH_TOKEN + " TEXT" +
                ")");
    }

    // Creating User's data table in the database
    private static void declareUserDataTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + USER_INFO_TABLE + "(" +
                USER_ID + " TEXT," +
                USER_EMAIL + " TEXT PRIMARY KEY, " +
                USER_NAME + " TEXT, " +
                USER_BALANCE + " TEXT, " +
                USER_BONUS + " TEXT " +
                ")");
    }

    // Creating Packet's data table in the database
    private static void declarePacketTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + PACKET_TABLE + "(" +
                PACKET_ID + " TEXT PRIMARY KEY, " +
                PACKET_TITLE + " TEXT, " +
                PACKET_PASSWORD + " TEXT, " +
                PACKET_DATE_END + " TEXT, " +
                PACKET_STATUS + " TEXT, " +
                PACKET_USER_EMAIL + " TEXT, " +
                "FOREIGN KEY (" + PACKET_USER_EMAIL + ") REFERENCES " + USER_INFO_TABLE + "(" + USER_EMAIL + ")" +
                ")");
    }

    // Creating Channel's data table in the database
    private static void declareChannelsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + CHANNELS_TABLE + "(" +
                CHANNEL_ID + " TEXT PRIMARY KEY, " +
                CHANNEL_TITLE + " TEXT, " +
                CHANNEL_GENRE_ID + " TEXT, " +
                CHANNEL_NUMBER + " TEXT, " +
                CHANNEL_URL + " TEXT, " +
                CHANNEL_ARCHIVE + " TEXT, " +
                CHANNEL_ARCHIVE_RANGE + " TEXT, " +
                CHANNEL_PVR + " TEXT, " +
                CHANNEL_CENSORED + " TEXT, " +
                CHANNEL_FAVORITE + " TEXT, " +
                CHANNEL_LOGO + " TEXT, " +
                CHANNEL_MONITORING_STATUS + " TEXT, " +
                CHANNEL_PACKET_ID + " TEXT, " +
                "FOREIGN KEY (" + CHANNEL_PACKET_ID + ") REFERENCES " + PACKET_TABLE + "(" + PACKET_ID + ")" +
                ")");
    }

    // Fill the database with values by default
    private static void fillDatabaseWithDefaultValues(SQLiteDatabase db) {

        fillLoggedTableWithDefaultValues(db);
        fillTokensTableWithDefaultValues(db);

        // TODO delete
        tempMethod_fillUserInfoTableWithDefaultValues(db);
        // TODO delete
        tempMethod1_fillPacketTableWithDefaultValues(db);
        tempMethod2_fillPacketTableWithDefaultValues(db);
    }

    private static void fillLoggedTableWithDefaultValues(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + LOGGED_STATE_TABLE + " VALUES (" +
                "\"false\", " + // LOGGED_IN_APP
                "\"false\" )"); // LOGGED_IN_PACKET
    }

    private static void fillTokensTableWithDefaultValues(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TOKENS_TABLE + " VALUES (" +
                "\"null\", " + // LOGIN_ACCESS_TOKEN
                "\"null\", " + // LOGIN_REFRESH_TOKEN
                "\"null\", " + // PACKET_ACCESS_TOKEN
                "\"null\" )"); // PACKET_REFRESH_TOKEN
    }

    // Drop existing tables
    private static void dropExistingTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TOKENS_TABLE + " ;");
        db.execSQL("DROP TABLE IF EXISTS " + USER_INFO_TABLE + " ;");
        db.execSQL("DROP TABLE IF EXISTS " + LOGGED_STATE_TABLE + " ;");
        db.execSQL("DROP TABLE IF EXISTS " + PACKET_TABLE + " ;");
        db.execSQL("DROP TABLE IF EXISTS " + CHANNELS_TABLE + " ;");
    }

    private static void tempMethod_fillUserInfoTableWithDefaultValues(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + USER_INFO_TABLE + " VALUES (" +
                "\"no id\", " +     // USER_ID
                "\"no email\", " +  // USER_EMAIL
                "\"unnamed\", " +   // USER_NAME
                "\"zero\", " +      // USER_BALANCE
                "\"zero\")");       // USER_BONUS
    }

    private static void tempMethod1_fillPacketTableWithDefaultValues(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + PACKET_TABLE + " VALUES (" +
                "\"1\", " + // PACKET_ID
                "\"Test packet\", " + // PACKET_NAME
                "\"123456\", " + // PACKET_PASSWORD
                "\"" + System.currentTimeMillis() + "\", " + // PACKET_DATE_END
                "\"1\", " + // PACKET_STATUS
                "\"some email\")");  // PACKET_USER_EMAIL
    }

    private static void tempMethod2_fillPacketTableWithDefaultValues(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + PACKET_TABLE + " VALUES (" +
                "\"2\", " + // PACKET_ID
                "\"Test packet 2\", " + // PACKET_NAME
                "\"324574\", " + // PACKET_PASSWORD
                "\"" + (System.currentTimeMillis() + 5000000) + "\", " + // PACKET_DATE_END
                "\"null\", " + // PACKET_STATUS
                "\"null\")");  // PACKET_USER_EMAIL
    }
}
