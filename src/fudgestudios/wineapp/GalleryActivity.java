package fudgestudios.wineapp;

import java.util.ArrayList;

import fudgestudios.wineapp.R.id;

import android.app.Activity;
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
    //View V;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.gallery);

	  
	    
	    GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new ImageAdapter(this, mArrayList));

	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            Toast.makeText(GalleryActivity.this, "" + position, Toast.LENGTH_SHORT).show();
	        }
	    });
	    
	    mDbHelper = new WineDBAdapter(this);
        mDbHelper.open();
        
        fetchImages();
        System.out.println("Test");
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
	
	/**
	 * 
	 
	private void getDataAndPopulate() {

	    ArrayList<byte[]> image = new ArrayList<byte[]>();
	    //ArrayList<String> caption = new ArrayList<String>();

	    //mCursor=db.rawQuery("select * from NAME",null);


	    while (mCursor.moveToNext()) {

	        byte[] temp_image = mCursor.getBlob(2);
	        String temp_caption = mCursor.getString(1);
	        String temp_id= mCursor.getString(0);
	        image.add(temp_image);
	        caption.add(temp_caption);
	        id.add(temp_id);
	    }
	    String[] captionArray = (String[]) caption.toArray(
	            new String[caption.size()]);

	    ImageAdapter itemsAdapter = new ImageAdapter(Item_show_grid.this, R.layout.item_grid,captionArray);
	    gv.setAdapter(itemsAdapter);

	}*/
	

}
