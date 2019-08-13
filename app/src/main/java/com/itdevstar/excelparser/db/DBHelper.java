package com.itdevstar.excelparser.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.itdevstar.excelparser.model.TableDataModel;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String TRACKS_TABLE_NAME = "tracks";

    public static final String TRACKS_COLUMN_ID = "id";
    public static final String TRACKS_COLUMN_SYMBOL = "symbol";
    public static final String TRACKS_COLUMN_ENTRANCE_TYPE = "entrance_type";
    public static final String TRACKS_COLUMN_OPTION_TYPE = "option_type";
    public static final String TRACKS_COLUMN_STRIKE = "strike";
    public static final String TRACKS_COLUMN_ORDER_DATE = "order_date";
    public static final String TRACKS_COLUMN_ORDER_TIME = "order_time";
    public static final String TRACKS_COLUMN_EXPIRATION = "expiration";
    public static final String TRACKS_COLUMN_CONTACTS = "contacts";
    public static final String TRACKS_COLUMN_PREMIUM = "premium";
    public static final String TRACKS_COLUMN_TOTAL_VALUE = "total_value";
    public static final String TRACKS_COLUMN_STRATEGY = "strategy";
    public static final String TRACKS_COLUMN_BEARISH_BULLISH = "bearish_bullish";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table tracks " +
                        "(id integer primary key, symbol text,entrance_type text,option_type text, strike text, order_date text" +
                        ", order_time text, expiration text, contacts text, premium text, total_value text, strategy text, bearish_bullish text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS tracks");
        onCreate(db);
    }

    public boolean insertContact (String symbol, String entranceType, String optionType, String strike,
                                  String orderDate, String orderTime, String expiration, String contacts,
                                  String premium, String totalValue, String strategy, String bearish_bullish) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRACKS_COLUMN_SYMBOL, symbol);
        contentValues.put(TRACKS_COLUMN_ENTRANCE_TYPE, entranceType);
        contentValues.put(TRACKS_COLUMN_OPTION_TYPE, optionType);
        contentValues.put(TRACKS_COLUMN_STRIKE, strike);
        contentValues.put(TRACKS_COLUMN_ORDER_DATE, orderDate);
        contentValues.put(TRACKS_COLUMN_ORDER_TIME, orderTime);
        contentValues.put(TRACKS_COLUMN_EXPIRATION, expiration);
        contentValues.put(TRACKS_COLUMN_CONTACTS, contacts);
        contentValues.put(TRACKS_COLUMN_PREMIUM, premium);
        contentValues.put(TRACKS_COLUMN_TOTAL_VALUE, totalValue);
        contentValues.put(TRACKS_COLUMN_STRATEGY, strategy);
        contentValues.put(TRACKS_COLUMN_BEARISH_BULLISH, bearish_bullish);

        db.insert(TRACKS_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from tracks where id="+id+"", null );
        return res;
    }

    public ArrayList<TableDataModel> getDataByStrategy(String keyword) {

        ArrayList<TableDataModel> list_data = new ArrayList<TableDataModel>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from tracks where strategy like '%"+keyword+"%'", null );


        int cnt = res.getCount();
        Log.d("cnt", Integer.toString(cnt));
        //hp = new HashMap();
        res.moveToFirst();

        String symbol="", entranceType="", optionType="", strike="",
                orderDate="", orderTime="", expiration="", contacts="",
                premium="", totalValue="", strategy="", bearish_bullish="";

        while(res.isAfterLast() == false){
            symbol = res.getString(res.getColumnIndex(TRACKS_COLUMN_SYMBOL));
            entranceType = res.getString(res.getColumnIndex(TRACKS_COLUMN_ENTRANCE_TYPE));
            optionType = res.getString(res.getColumnIndex(TRACKS_COLUMN_OPTION_TYPE));
            strike = res.getString(res.getColumnIndex(TRACKS_COLUMN_STRIKE));
            orderDate = res.getString(res.getColumnIndex(TRACKS_COLUMN_ORDER_DATE));
            orderTime = res.getString(res.getColumnIndex(TRACKS_COLUMN_ORDER_TIME));
            expiration = res.getString(res.getColumnIndex(TRACKS_COLUMN_EXPIRATION));
            contacts = res.getString(res.getColumnIndex(TRACKS_COLUMN_CONTACTS));
            premium = res.getString(res.getColumnIndex(TRACKS_COLUMN_PREMIUM));
            totalValue = res.getString(res.getColumnIndex(TRACKS_COLUMN_TOTAL_VALUE));
            strategy = res.getString(res.getColumnIndex(TRACKS_COLUMN_STRATEGY));
            bearish_bullish = res.getString(res.getColumnIndex(TRACKS_COLUMN_BEARISH_BULLISH));

            TableDataModel model = new TableDataModel(symbol, entranceType, optionType, strike, orderDate, orderTime, expiration, contacts, premium, totalValue, strategy, bearish_bullish);
            list_data.add(model);
            res.moveToNext();
        }
        return list_data;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TRACKS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String symbol, String entranceType, String optionType, String strike,
                                  String orderDate, String orderTime, String expiration, String contacts,
                                  String premium, String totalValue, String strategy, String bearish_bullish) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRACKS_COLUMN_SYMBOL, symbol);
        contentValues.put(TRACKS_COLUMN_ENTRANCE_TYPE, entranceType);
        contentValues.put(TRACKS_COLUMN_OPTION_TYPE, optionType);
        contentValues.put(TRACKS_COLUMN_STRIKE, strike);
        contentValues.put(TRACKS_COLUMN_ORDER_DATE, orderDate);
        contentValues.put(TRACKS_COLUMN_ORDER_TIME, orderTime);
        contentValues.put(TRACKS_COLUMN_EXPIRATION, expiration);
        contentValues.put(TRACKS_COLUMN_CONTACTS, contacts);
        contentValues.put(TRACKS_COLUMN_PREMIUM, premium);
        contentValues.put(TRACKS_COLUMN_TOTAL_VALUE, totalValue);
        contentValues.put(TRACKS_COLUMN_STRATEGY, strategy);
        contentValues.put(TRACKS_COLUMN_BEARISH_BULLISH, bearish_bullish);

        db.update(TRACKS_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TRACKS_TABLE_NAME,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<TableDataModel> getAllTracks() {
        ArrayList<TableDataModel> list_data = new ArrayList<TableDataModel>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from tracks", null );
        res.moveToFirst();

        String symbol="", entranceType="", optionType="", strike="",
                orderDate="", orderTime="", expiration="", contacts="",
                premium="", totalValue="", strategy="", bearish_bullish="";

        while(res.isAfterLast() == false){
            symbol = res.getString(res.getColumnIndex(TRACKS_COLUMN_SYMBOL));
            entranceType = res.getString(res.getColumnIndex(TRACKS_COLUMN_ENTRANCE_TYPE));
            optionType = res.getString(res.getColumnIndex(TRACKS_COLUMN_OPTION_TYPE));
            strike = res.getString(res.getColumnIndex(TRACKS_COLUMN_STRIKE));
            orderDate = res.getString(res.getColumnIndex(TRACKS_COLUMN_ORDER_DATE));
            orderTime = res.getString(res.getColumnIndex(TRACKS_COLUMN_ORDER_TIME));
            expiration = res.getString(res.getColumnIndex(TRACKS_COLUMN_EXPIRATION));
            contacts = res.getString(res.getColumnIndex(TRACKS_COLUMN_CONTACTS));
            premium = res.getString(res.getColumnIndex(TRACKS_COLUMN_PREMIUM));
            totalValue = res.getString(res.getColumnIndex(TRACKS_COLUMN_TOTAL_VALUE));
            strategy = res.getString(res.getColumnIndex(TRACKS_COLUMN_STRATEGY));
            bearish_bullish = res.getString(res.getColumnIndex(TRACKS_COLUMN_BEARISH_BULLISH));

            TableDataModel model = new TableDataModel(symbol, entranceType, optionType, strike, orderDate, orderTime, expiration, contacts, premium, totalValue, strategy, bearish_bullish);
            list_data.add(model);
            res.moveToNext();
        }
        return list_data;
    }
}
