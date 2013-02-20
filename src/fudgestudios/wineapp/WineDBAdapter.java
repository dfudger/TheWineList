package fudgestudios.wineapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WineDBAdapter 
{
	public static final String KEY_TITLE = "title";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_ROWID = "_id";
    
    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "bottles";
    private static final int DATABASE_VERSION = 2;

    private final Context mCtx;
    /**
     * Database creation sql statement
     */
    private static final String DATABASE_CREATE =
    		"create table " + DATABASE_TABLE + "(_id integer primary key autoincrement, "
        + "title text not null, image text not null);";

    private static final String TAG = "WineDBAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    
    
    
    private static class DatabaseHelper extends SQLiteOpenHelper 
    {
    
        DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) 
        {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS bottles");
            onCreate(db);
        }
    }
    
    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx the Context within which to work
     */
    public WineDBAdapter(Context ctx) 
    {
        this.mCtx = ctx;
    }
    
    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public WineDBAdapter open() throws SQLException 
    {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }
    
    public void close() 
    {
        mDbHelper.close();
    }
    
    /**
     * Create a new wine using the title and image provided. If the wine is
     * successfully created return the new rowId for that wine, otherwise return
     * a -1 to indicate failure.
     * 
     * @param title the title of the wine
     * @param image the image corresponding with the wine
     * @return rowId or -1 if failed
     */
    public long createWine(String title, String image) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_IMAGE, image);

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }
    
    /**
     * Delete the wine with the given rowId
     * 
     * @param rowId id of wine to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteWine(long rowId) 
    {

        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }
    
    
    /**
     * Return a Cursor over the list of all notes in the database
     * 
     * @return Cursor over all notes
     */
    public Cursor fetchAllWines() 
    {
        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE,
                KEY_IMAGE}, null, null, null, null, null);
    }
    
    /**
     * Return a Cursor positioned at the wine that matches the given rowId
     * 
     * @param rowId id of note to retrieve
     * @return Cursor positioned to matching wine, if found
     * @throws SQLException if wine could not be found/retrieved
     */
    public Cursor fetchWine(long rowId) throws SQLException 
    {

        Cursor mCursor =
            mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                    KEY_TITLE, KEY_IMAGE}, KEY_ROWID + "=" + rowId, null,
                    null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }
    
    
    /**
     * Update the wine using the details provided. The wine to be updated is
     * specified using the rowId, and it is altered to use the title and image
     * values passed in
     * 
     * @param rowId id of wine to update
     * @param title value to set wine title to
     * @param image value to set wine image to
     * @return true if the wine was successfully updated, false otherwise
     */
    public boolean updateWine(long rowId, String title, String image) {
        ContentValues args = new ContentValues();
        args.put(KEY_TITLE, title);
        args.put(KEY_IMAGE, image);

        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
    
    
    
    
}
