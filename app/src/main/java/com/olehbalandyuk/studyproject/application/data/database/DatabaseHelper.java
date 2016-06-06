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
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "user_info.db";

    // Table, which contains user's logged state
    public static final String LOGGED_STATE_TABLE = "logged_table";
    public static final String LOGGED_IN_APP = "is_logged";
    public static final String LOGGED_IN_PACKED = "is_logged_by_packet";

    //Table, which contains tokens
    public static final String TOKENS_TABLE = "tokens";
    public static final String LOGIN_ACCESS_TOKEN = "login_token";
    public static final String LOGIN_REFRESH_TOKEN = "refresh_token";
    public static final String PACKET_ACCESS_TOKEN = "access_token";
    public static final String PACKET_REFRESH_TOKEN = "packet_refresh_token";

    // Table, which contains user data
    public static final String USER_INFO_TABLE = "info";
    public static final String USER_ID = "_id";
    public static final String USER_EMAIL = "email";
    public static final String USER_NAME = "name";
    public static final String USER_BALANCE = "balance";
    public static final String USER_BONUS = "bonus";

    // Table, which contains packet data
    public static final String PACKET_TABLE = "packets";
    public static final String PACKET_ID = "packet_id";
    public static final String PACKET_NAME = "packet_name";
    public static final String PACKET_PASSWORD = "packet_password";
    public static final String PACKET_DATE_END = "packet_date_end";
    public static final String PACKET_STATUS = "packet_status";
    public static final String PACKET_USER_EMAIL = "packet_user_id";

    // Table, which contains tv channel's data
    public static final String CHANNELS_TABLE = "tv_channels";
    public static final String CHANNEL_ID = "_id";
    public static final String CHANNEL_URL = "url";
    public static final String CHANNEL_NAME = "name";
    public static final String CHANNEL_GENRE_ID = "genre_id";
    public static final String CHANNEL_NUMBER = "number";
    public static final String CHANNEL_ARCHIVE = "archive";
    public static final String CHANNEL_ARCHIVE_RANGE = "archive_range";
    public static final String CHANNEL_PVR = "pvr";
    public static final String CHANNEL_CENSORED = "censored";
    public static final String CHANNEL_FAVORITE = "favorite";
    public static final String CHANNEL_LOGO = "logo";
    public static final String CHANNEL_MONITORING_STATUS = "monitoring_status";
    public static final String CHANNEL_PACKET_ID = "packet_id";

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

    // Creating logged state table in the database
    private void declareLoggedStateTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + LOGGED_STATE_TABLE + "(" +
                LOGGED_IN_APP + " TEXT, " +
                LOGGED_IN_PACKED + " TEXT " +
                ")");
    }

    // Creating Token's table in the database
    private void declareTokensTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TOKENS_TABLE + "(" +
                LOGIN_ACCESS_TOKEN + " TEXT, " +
                LOGIN_REFRESH_TOKEN + " TEXT, " +
                PACKET_ACCESS_TOKEN + " TEXT, " +
                PACKET_REFRESH_TOKEN + " TEXT" +
                ")");
    }

    // Creating User's data table in the database
    private void declareUserDataTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + USER_INFO_TABLE + "(" +
                USER_ID + " TEXT," +
                USER_EMAIL + " TEXT PRIMARY KEY, " +
                USER_NAME + " TEXT, " +
                USER_BALANCE + " TEXT, " +
                USER_BONUS + " TEXT " +
                ")");
    }

    // Creating Packet's data table in the database
    private void declarePacketTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + PACKET_TABLE + "(" +
                PACKET_ID + " TEXT PRIMARY KEY, " +
                PACKET_NAME + " TEXT, " +
                PACKET_PASSWORD + " TEXT, " +
                PACKET_DATE_END + " TEXT, " +
                PACKET_STATUS + " TEXT, " +
                PACKET_USER_EMAIL + " TEXT, " +
                "FOREIGN KEY (" + PACKET_USER_EMAIL + ") REFERENCES " + USER_INFO_TABLE + "(" + USER_EMAIL + ")" +
                ")");
    }

    // Creating Channel's data table in the database
    private void declareChannelsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + CHANNELS_TABLE + "(" +
                CHANNEL_ID + " TEXT PRIMARY KEY, " +
                CHANNEL_NAME + " TEXT, " +
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
    private void fillDatabaseWithDefaultValues(SQLiteDatabase db) {

        fillLoggedTableWithDefaultValues(db);
        fillTokensTableWithDefaultValues(db);
        fillUserInfoTableWithDefaultValues(db);
        fillPacketTableWithDefaultValues(db);
        fillChannelsTableWithDefaultValues(db);
    }

    private void fillLoggedTableWithDefaultValues(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + LOGGED_STATE_TABLE + " VALUES (" +
                "\"false\", " + // LOGGED_IN_APP
                "\"false\" )"); // LOGGED_IN_PACKET
    }

    private void fillTokensTableWithDefaultValues(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TOKENS_TABLE + " VALUES (" +
                "\"null\", " + // LOGIN_ACCESS_TOKEN
                "\"null\", " + // LOGIN_REFRESH_TOKEN
                "\"null\", " + // PACKET_ACCESS_TOKEN
                "\"null\" )"); // PACKET_REFRESH_TOKEN
    }

    private void fillUserInfoTableWithDefaultValues(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + USER_INFO_TABLE + " VALUES (" +
                "\"null\", " + // USER_ID
                "\"null\", " + // USER_EMAIL
                "\"null\", " + // USER_NAME
                "\"null\", " + // USER_BALANCE
                "\"null\")");  // USER_BONUS
    }

    private void fillPacketTableWithDefaultValues(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + PACKET_TABLE + " VALUES (" +
                "\"null\", " + // PACKET_ID
                "\"null\", " + // PACKET_NAME
                "\"null\", " + // PACKET_PASSWORD
                "\"null\", " + // PACKET_DATE_END
                "\"null\", " + // PACKET_STATUS
                "\"null\")");  // PACKET_USER_EMAIL
    }

    private void fillChannelsTableWithDefaultValues(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + CHANNELS_TABLE + " VALUES (" +
                "\"null\", " + // CHANNEL_ID
                "\"null\", " + // CHANNEL_URL
                "\"null\", " + // CHANNEL_NAME
                "\"null\", " + // CHANNEL_GENRE_ID
                "\"null\", " + // CHANNEL_NUMBER
                "\"null\", " + // CHANNEL_ARCHIVE
                "\"null\", " + // CHANNEL_ARCHIVE_RANGE
                "\"null\", " + // CHANNEL_PVR
                "\"null\", " + // CHANNEL_CENSORED
                "\"null\", " + // CHANNEL_FAVORITE
                "\"null\", " + // CHANNEL_LOGO
                "\"null\", " + // CHANNEL_MONITORING_STATUS
                "\"null\" )"); // CHANNEL_PACKET_ID
    }

    // Drop existing tables
    private void dropExistingTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TOKENS_TABLE + " ;");
        db.execSQL("DROP TABLE IF EXISTS " + USER_INFO_TABLE + " ;");
        db.execSQL("DROP TABLE IF EXISTS " + LOGGED_STATE_TABLE + " ;");
        db.execSQL("DROP TABLE IF EXISTS " + PACKET_TABLE + " ;");
        db.execSQL("DROP TABLE IF EXISTS " + CHANNELS_TABLE + " ;");
    }
}
