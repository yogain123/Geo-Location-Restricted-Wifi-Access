package com.pickachu.momma.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaibhavsinghal on 19/12/15.
 */
public class SqliteHelper extends SQLiteOpenHelper {

    private final String TAG = this.getClass().getSimpleName();

    public static final String RIDER_DATABASE_NAME = "SfxRiderDatabase.db";

    /*
    v2 to v3: Added cols (time, km, lat, long) in OrderStageTable
     */
    private static final int RIDER_DATABASE_VERSION = 3;

    /*********************************************/
    //COLUMNS
    /*********************************************/
    private static final String ORDER_DISPLAY_DATA_ORDER_ID = "order_id";


    /*********************************************/
    //TABLES
    /*********************************************/
    private static final String TABLE_ORDER_DISPLAY_DATA = "table_order_display_data";

    /*********************************************/
    //CREATE TABLE QUERIES
    /*********************************************/
    private static final String CREATE_TABLE_ORDER_DISPLAY_DATA = "CREATE TABLE "
            + "IF NOT EXISTS "
            + TABLE_ORDER_DISPLAY_DATA
            + "("
            + ORDER_DISPLAY_DATA_ORDER_ID + " TEXT PRIMARY KEY,"
            + ")";

    /*********************************************/
    //SqliteHelper Util Methods

    /*********************************************/
    public SqliteHelper(Context context) {
        super(context, RIDER_DATABASE_NAME, null, RIDER_DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_ORDER_DISPLAY_DATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        onCreate(db);

    }

    //Delete DB
    public void deleteDatabase(Context context) {
        context.deleteDatabase(RIDER_DATABASE_NAME);
    }

    //Common Delete
    public long deleteData(String TableName, String ColumnName, String ColumnValue) {
        long rowsModified;
        SQLiteDatabase db = DbSingleton.openDatabase();
        rowsModified = db.delete(TableName, ColumnName + " = ?", new String[]{String.valueOf(ColumnValue)});
        DbSingleton.closeDatabase();

        return rowsModified;
    }

    public long deleteData(String TableName, String whereClause) {
        long rowsModified;
        SQLiteDatabase db = DbSingleton.openDatabase();
        rowsModified = db.delete(TableName, whereClause, null);
        DbSingleton.closeDatabase();

        return rowsModified;
    }

    public Cursor getData(String Query) {
        SQLiteDatabase db = DbSingleton.openDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        return cursor;
    }

    /*********************************************/
    //CRUD TABLE_ORDER_DISPLAY_DATA

    /*********************************************/
    /*public long createOrderDisplayData(OrderDisplayData orderDisplayData) {

        long createdRowNumber = 0;
        SQLiteDatabase db = DbSingleton.openDatabase();
        ContentValues values = new ContentValues();
        String orderId = orderDisplayData.getOrderId();

        if (!isOrderDelivered(orderId)) {
            if (!isOrderPresentInDisplayTable(orderId)) {

                values.put(ORDER_DISPLAY_DATA_ORDER_ID, orderId);

                createdRowNumber = db.insert(TABLE_ORDER_DISPLAY_DATA, null, values);
                DbSingleton.closeDatabase();

                Log.i(TAG, "TABLE_ORDER_DISPLAY_DATA -> ROW # ADDED: " + createdRowNumber);
            } else {

                return updateOrderDisplayData(orderDisplayData);
            }
        }

        return createdRowNumber;
    }

    public List<OrderDisplayData> readAllOrderDisplayData() {

        SQLiteDatabase db = DbSingleton.openDatabase();

        String rawQuery = "SELECT * FROM" + " " + TABLE_ORDER_DISPLAY_DATA;
        Cursor cursor = db.rawQuery(rawQuery, null);
        List<OrderDisplayData> orderDisplayDataList = new ArrayList<>();

        while (cursor.moveToNext()) {
            OrderDisplayData orderDisplayData = new OrderDisplayData();
            String orderId = cursor.getString(cursor.getColumnIndex(ORDER_DISPLAY_DATA_ORDER_ID));

            orderDisplayData.setOrderCOID(cursor.getString(cursor.getColumnIndex(ORDER_DISPLAY_DATA_ORDER_COID)));

            orderDisplayDataList.add(orderDisplayData);
        }

        Log.i(TAG, "TABLE_ORDER_DISPLAY_DATA -> ALL ROWS READ: " + cursor.getCount());

        cursor.close();
        DbSingleton.closeDatabase();

        return orderDisplayDataList;
    }

    public OrderDisplayData readOrderDisplayData(String orderId) {

        SQLiteDatabase db = DbSingleton.openDatabase();

        String rawQuery = "SELECT * FROM" + " " + TABLE_ORDER_DISPLAY_DATA + " "
                + "WHERE" + " " + ORDER_DISPLAY_DATA_ORDER_ID + " = " + "'" + orderId + "'";
        Cursor cursor = db.rawQuery(rawQuery, null);
        OrderDisplayData orderDisplayData = new OrderDisplayData();

        while (cursor.moveToNext()) {
            orderDisplayData = new OrderDisplayData();

            orderDisplayData.setOrderId(cursor.getString(cursor.getColumnIndex(ORDER_DISPLAY_DATA_ORDER_ID)));
        }

        Log.i(TAG, "TABLE_ORDER_DISPLAY_DATA -> ROWS READ: " + cursor.getCount());

        cursor.close();
        DbSingleton.closeDatabase();

        return orderDisplayData;
    }

    public long updateOrderDisplayData(OrderDisplayData orderDisplayData) {

        long rowsModified = 0;
        SQLiteDatabase db = DbSingleton.openDatabase();
        ContentValues values = new ContentValues();
        String whereClause = ORDER_DISPLAY_DATA_ORDER_ID + " = " + "'" + orderDisplayData.getOrderId() + "'";

        values.put(ORDER_DISPLAY_DATA_DELIVERY_LONGITUDE, orderDisplayData.getDeliveryLongitude());

        rowsModified = db.update(TABLE_ORDER_DISPLAY_DATA, values, whereClause, null);

        Log.i(TAG, "TABLE_ORDER_DISPLAY_DATA -> ROWS UPDATED: " + rowsModified);

        DbSingleton.closeDatabase();
        return rowsModified;
    }

    public long deleteOrderDisplayData(String orderId) {
        long rowsModified = 0;
        String whereClause = ORDER_DISPLAY_DATA_ORDER_ID + " = " + "'" + orderId + "'";

        rowsModified = deleteData(TABLE_ORDER_DISPLAY_DATA, whereClause);

        Log.i(TAG, "TABLE_ORDER_DISPLAY_DATA -> ROWS DELETED: " + rowsModified);
        return rowsModified;
    }

    public boolean isOrderPresentInDisplayTable(String orderId) {
        SQLiteDatabase db = DbSingleton.openDatabase();

        String rawQuery = "SELECT * FROM" + " " + TABLE_ORDER_DISPLAY_DATA + " "
                + "WHERE" + " " + ORDER_DISPLAY_DATA_ORDER_ID + " = " + "'" + orderId + "'";
        Cursor cursor = db.rawQuery(rawQuery, null);

        while (cursor.moveToNext()) {
            cursor.close();
            DbSingleton.closeDatabase();
            return true;
        }

        cursor.close();
        DbSingleton.closeDatabase();
        return false;
    }

    public long getCountInOrderDisplayTable() {
        SQLiteDatabase db = DbSingleton.openDatabase();

        long ordersInDisplayTable = 0;
        String rawQuery = "SELECT COUNT(*) FROM" + " " + TABLE_ORDER_DISPLAY_DATA;

        ordersInDisplayTable = DatabaseUtils.longForQuery(db, rawQuery, null);

        return ordersInDisplayTable;
    }*/
}