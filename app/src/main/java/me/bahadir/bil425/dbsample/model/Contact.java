package me.bahadir.bil425.dbsample.model;

import android.provider.BaseColumns;

/**
 * Created by bahadir on 09/02/15.
 */
public class Contact implements BaseColumns {
    public static final String TABLE_NAME = "contacts";
    public static final String COLUMN_NAME_CONTACT_ID = "_id";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_EMAIL = "email";
    public static final String COLUMN_NAME_PHONE = "phone";
    public static final String COLUMN_NAME_STREET = "street";
    public static final String COLUMN_NAME_CITY = "city";
}
