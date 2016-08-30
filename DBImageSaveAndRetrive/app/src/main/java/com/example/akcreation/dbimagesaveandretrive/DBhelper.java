package com.example.akcreation.dbimagesaveandretrive;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ankita Jounjal on 30-08-2016.
 */
public class DBhelper {

    public static final String EMP_ID="id";
    public static final String EMP_NAME="name";
    public static final String EMP_AGE="age";
    public static final String EMP_PHOTO="photo";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME="EmployeesDB,db";
    private static final int DATABASE_VERSION=1;

    private static final String EMPLOYEES_TABLE="Employee";

    private static final String CREATE_EMPLOYEES_TABLE="create table" + EMPLOYEES_TABLE + "(" + EMP_ID + "integer primary key autoincrement,"
            + EMP_NAME + "text not null unique," + EMP_AGE + "integer," + EMP_PHOTO + "blob not null);";

    private final Context mCtx;



    private static class DatabaseHelper extends SQLiteOpenHelper{
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_EMPLOYEES_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXIST" + EMPLOYEES_TABLE);
            onCreate(sqLiteDatabase);
        }
    }

    public void Reset(){
        mDbHelper.onUpgrade(this.mDb,1,1);
    }

    public DBhelper(Context ctx){
        mCtx = ctx;
        mDbHelper = new DatabaseHelper(mCtx);

    }

    public DBhelper open() throws SQLException {
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        mDbHelper.close();
    }

    public void insertEmpDetails(Employee  employee){
        ContentValues cv = new ContentValues();
        cv.put(EMP_NAME, employee,getName());
        cv.put(EMP_AGE, employee.getAge());
        cv.put(EMP_PHOTO, Utility.getBytes(employee.getBitmap()));
        mDb.insert(EMPLOYEES_TABLE,null,cv);
    }

    public Employee retriveEmpDetails() throws SQLException{
        Cursor cur = mDb.query(true, EMPLOYEES_TABLE, new String[]{EMP_NAME, EMP_AGE, EMP_PHOTO}, null,null, null, null, null, null);
        if(cur.moveToFirst()){
            String name= cur.getString(cur.getColumnIndex(EMP_NAME));
            int age = cur.getInt(cur.getColumnIndex(EMP_AGE));
            byte[] blob =cur.getBlob(cur.getColumnIndex(EMP_PHOTO));
            cur.close();
            return  new Employee(name, age, Utility.getPhoto(blob));
        }
        cur.close();
        return null;
    }
}
