package fudgestudios.wineapp;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class CreateBottleActivity extends Activity
{
	private int mWineNumber = 1;
	private WineDBAdapter mDbHelper;
	private ImageView y;
	private Bitmap mImageBitmap;
	private String filename;
	private String bottleTitle;
	EditText mEditTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        Log.w("WineApp","Edit Bottle View");
        
        /*** Display the GUI ***/
        setContentView(R.layout.new_bottle);
        
        /*** Display the image on the screen ***/
        mImageBitmap = (Bitmap) getIntent().getExtras().get("bitmap");
        y = (ImageView) findViewById(R.id.imageView2);
 
        y.setImageBitmap(mImageBitmap);
        y.setVisibility(View.VISIBLE);
        
        /*** Open up DB ***/
        mDbHelper = new WineDBAdapter(this);
        mDbHelper.open();   
        
        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() 
        {
            public void onClick(View v) 
            {
                //DisplayToast("You have clicked the Save button");
            	createWine();
            }
        });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
	
    private void createWine() 
    {
    	Log.w("WineApp","Create Wine");
    	filename = getIntent().getExtras().getString("fileName");//(String) getIntent().getExtras().get("filename");
		String noteName = "Wine " + mWineNumber++;
		System.out.println("Wine Name: " + noteName);
		System.out.println("File Name: " + filename);
        
		mEditTitle = (EditText) findViewById(R.id.editTitle);
		bottleTitle = mEditTitle.getText().toString();
		Log.v("EditText", bottleTitle);
		
		if(bottleTitle == null)
			Log.w("WineApp", "EMPTY");
		else
		{
			mDbHelper.createWine(bottleTitle, filename);
			mDbHelper.close();
			finish();
		}
    }

}
