package registrationform.com.registrationform;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pramo on 10/16/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="Registration.db"; //database name
    public static final String TABLE_NAME="RegDetails"; // table name
    // columns
    public static final String COL_1="ID";
    public static final String COL_2="NAME";
    public static final String COL_3="COLLEGE_NAME";
    public static final String COL_4="EMAIL";
    public static final String COL_5="EVENT";
    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME, null, 1); //to create database

        //SQLiteDatabase db=this.getWritableDatabase(); //checking db is writable
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,COLLEGE_NAME TEXT,EMAIL TEXT,EVENT TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String name,String c_name,String email,String event){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,c_name);
        contentValues.put(COL_4,email);
        contentValues.put(COL_5,event);
        long result=db.insert(TABLE_NAME,null,contentValues);
        if(result==-1) {
            return false;
        }
        return true;
    }
}
