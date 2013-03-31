package fudgestudios.wineapp;

import java.util.ArrayList;

import fudgestudios.wineapp.R.id;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

/**
 * @author dfudger
 *
 */

public class GalleryActivity extends Activity
{
	private WineDBAdapter mDbHelper;
    private Cursor mCursor;
    ArrayList<String> mArrayList = new ArrayList<String>();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.gallery);

	  
	    
	    GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new ImageAdapter(this, mArrayList));

	    gridview.setOnItemClickListener(new OnItemClickListener() 
	    {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
	        {
	            //Toast.makeText(GalleryActivity.this, "" + position, Toast.LENGTH_SHORT).show();
	        	
	        	dispatchViewBottleIntent();
	        }
	    });
	    
	    mDbHelper = new WineDBAdapter(this);
        mDbHelper.open();
        
        fetchImages();

        for(int i = 0; i < mArrayList.size(); i++)
        {
        	System.out.println(mArrayList.get(i));
        }
	}	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
	
	public void fetchImages()
	{
		System.out.println("Fetch Images");
		mCursor = mDbHelper.fetchAllWines();
		for(mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) 
		{
			//The Cursor is now set to the right position
			mArrayList.add(mCursor.getString(2));
		}
	} 
	
	/**** Edit Wine Info ****/
    private void dispatchViewBottleIntent()
    {
    	Intent bottleIntent = new Intent(this, ViewBottleActivity.class); //Request an image from an existing camera application
    	//System.out.println("File Name Before Sent: " + imageFileName);
    	//bottleIntent.putExtra("fileName", imageFileName);
    	startActivity(bottleIntent); 
    }

}
