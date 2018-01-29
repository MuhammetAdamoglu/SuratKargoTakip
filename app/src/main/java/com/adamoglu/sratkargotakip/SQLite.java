package com.adamoglu.sratkargotakip;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLite extends SQLiteOpenHelper {


    private static final String VERITABANI = "Cargo";
    private static final String TABLE = "cargo";
    private static String CARGO_NUMBER = "cargo_number";
    private static String CARGO_NAME = "cargo_name";

    private static String KEY_PRODUCT = "key_product";
    private static String VALUE_PRODUCT = "value_product";

    private static String KEY_SENDER = "key_sender";
    private static String VALUE_SENDER = "value_sender";

    private static String KEY_BUYER = "key_buyer";
    private static String VALUE_BUYER = "value_buyer";

    private static String ID = "ID";


    public SQLite(Context context) {
        super(context, VERITABANI, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE " + TABLE + " ( "

                        + CARGO_NUMBER + " TEXT, "
                        + CARGO_NAME + " TEXT, "
                        + KEY_PRODUCT + " TEXT, "
                        + VALUE_PRODUCT + " TEXT, "
                        + KEY_SENDER + " TEXT, "
                        + VALUE_SENDER + " TEXT, "
                        + KEY_BUYER + " TEXT, "
                        + VALUE_BUYER + " TEXT, "
                        + ID + " INTEGER PRIMARY KEY AUTOINCREMENT"
                        + " )"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXITS" + TABLE);
        onCreate(db);

    }


    public boolean Add(String cargo_number, String cargo_name,String key_product,String value_product, String key_sender, String value_sender,String key_buyer,String value_buyer) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues veriler = new ContentValues();

        veriler.put(CARGO_NUMBER, cargo_number);
        veriler.put(CARGO_NAME, cargo_name);
        veriler.put(KEY_PRODUCT, key_product);
        veriler.put(VALUE_PRODUCT, value_product);
        veriler.put(KEY_SENDER, key_sender);
        veriler.put(VALUE_SENDER, value_sender);
        veriler.put(KEY_BUYER, key_buyer);
        veriler.put(VALUE_BUYER, value_buyer);

        long result = db.insert(TABLE, null, veriler);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery("select * from " + TABLE +
                " ORDER BY "+ID + " DESC", null);

        return result;
    }

    String[] getKey(String CargoNumber){

        CargoNumber=CargoNumber.trim();
        String[] keys =new String[3];

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT "+KEY_PRODUCT+" FROM "+TABLE+" WHERE "+ CARGO_NUMBER +"=?";
        Cursor c = db.rawQuery(selectQuery, new String[] { CargoNumber });

        if (c.moveToFirst()) {
            keys[0] = c.getString(c.getColumnIndex(KEY_PRODUCT));
        }else
            keys[0] ="";
        c.close();

        db = this.getWritableDatabase();
        selectQuery = "SELECT "+KEY_BUYER+" FROM "+TABLE+" WHERE "+ CARGO_NUMBER +"=?";
        c = db.rawQuery(selectQuery, new String[] { CargoNumber });

        if (c.moveToFirst()) {
            keys[1] = c.getString(c.getColumnIndex(KEY_BUYER));
        }else
            keys[1] ="";
        c.close();

        db = this.getWritableDatabase();
        selectQuery = "SELECT "+KEY_SENDER+" FROM "+TABLE+" WHERE "+ CARGO_NUMBER +"=?";
        c = db.rawQuery(selectQuery, new String[] { CargoNumber });

        if (c.moveToFirst()) {
            keys[2] = c.getString(c.getColumnIndex(KEY_SENDER));
        }else
            keys[2] ="";
        c.close();

        return keys;
    }

    String[] getValue(String CargoNumber){

        CargoNumber=CargoNumber.trim();
        String[] values =new String[3];

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT "+VALUE_PRODUCT+" FROM "+TABLE+" WHERE "+ CARGO_NUMBER +"=?";
        Cursor c = db.rawQuery(selectQuery, new String[] { CargoNumber });

        if (c.moveToFirst()) {
            values[0] = c.getString(c.getColumnIndex(VALUE_PRODUCT));
        }else
            values[0]="";
        c.close();

        db = this.getWritableDatabase();
        selectQuery = "SELECT "+VALUE_BUYER+" FROM "+TABLE+" WHERE "+ CARGO_NUMBER +"=?";
        c = db.rawQuery(selectQuery, new String[] { CargoNumber });

        if (c.moveToFirst()) {
            values[1] = c.getString(c.getColumnIndex(VALUE_BUYER));
        }else
            values[1]="";
        c.close();

        db = this.getWritableDatabase();
        selectQuery = "SELECT "+VALUE_SENDER+" FROM "+TABLE+" WHERE "+ CARGO_NUMBER +"=?";
        c = db.rawQuery(selectQuery, new String[] { CargoNumber });

        if (c.moveToFirst()) {
            values[2] = c.getString(c.getColumnIndex(VALUE_SENDER));
        }else
            values[2]="";
        c.close();

        return values;
    }

    String getCargoName(String CargoNumber){
        CargoNumber=CargoNumber.trim();
        String value;

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT "+CARGO_NAME+" FROM "+TABLE+" WHERE "+ CARGO_NUMBER +"=?";
        Cursor c = db.rawQuery(selectQuery, new String[] { CargoNumber });

        if (c.moveToFirst()) {
            value = c.getString(c.getColumnIndex(CARGO_NAME));
        }else
            value=CargoNumber;
        c.close();

        return value;
    }

    boolean ControlCargo(String CargoNumber){
        CargoNumber=CargoNumber.trim();

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT "+VALUE_PRODUCT+" FROM "+TABLE+" WHERE "+ CARGO_NUMBER +"=?";
        Cursor c = db.rawQuery(selectQuery, new String[] { CargoNumber });

        if (c.moveToFirst()) {
            c.close();
            return true;
        }else{
            c.close();
            return false;
        }
    }

    public void UpdataCargoName(String cargoNumber, String cargoName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CARGO_NAME, cargoName);

        db.update(TABLE, contentValues, CARGO_NUMBER+" = ?", new String[]{cargoNumber});
    }

    void DeleteCargo(String CargoNumber) {
        CargoNumber=CargoNumber.trim();

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE, CARGO_NUMBER + " = ?", new String[]{CargoNumber});
    }

    public void DeleteAllData(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE,null,null);
        db.close();

    }


}