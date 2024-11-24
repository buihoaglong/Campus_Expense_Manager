package com.btec.fpt.campus_expense_manager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ExpenseDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "expenses";

    // Column names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create expenses table
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_AMOUNT + " REAL, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_DATE + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert a new expense record
    public boolean insertExpense(double amount, String description, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AMOUNT, amount);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_DATE, date);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result != -1;
    }

    // Retrieve all expenses
    public Cursor getAllExpenses() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

}