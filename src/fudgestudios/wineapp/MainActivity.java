package fudgestudios.wineapp;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity 
{
	private int mWineNumber = 1;
	
	private ImageView mImageView;
	private Bitmap mImageBitmap;
	
	/****DB Variables****/
	private WineDBAdapter mDbHelper;
    public static final int INSERT_ID = Menu.FIRST;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mImageView = (ImageView) findViewById(R.id.imageView1);
        
        mDbHelper = new WineDBAdapter(this);
        mDbHelper.open();
        //fillData();
        
//      Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //Request an image from an existing camera application
//    	startActivityForResult(takePictureIntent, 1); //Start the camera intent
        // dispatchTakePictureIntent(1);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    /**** Take a Photo with the Camera App ****/
    private void dispatchTakePictureIntent(int actionCode)
    {
    	//Compose a camera intent
    	Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //Request an image from an existing camera application
    	startActivityForResult(takePictureIntent, actionCode); //Start the camera intent
    	// handleSmallCameraPhoto(takePictureIntent);
    	
    }
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		/*case ACTION_TAKE_PHOTO_B: {
			if (resultCode == RESULT_OK) {
				handleBigCameraPhoto();
			}
			break;
		} // ACTION_TAKE_PHOTO_B*/

		case 1: {
			if (resultCode == RESULT_OK) {
				handleSmallCameraPhoto(data);
			}
			break;
		} // ACTION_TAKE_PHOTO_S

		} // switch
	}

   
    public void photoButtonResponse(View v) 
    {
    	Log.w("WineApp","LOL taking photo.");
    	dispatchTakePictureIntent(1);
    	
    	//handleSmallCameraPhoto();
    }
    
    public static boolean isIntentAvailable(Context context, String action)
    {
    	final PackageManager packageManager = context.getPackageManager();
    	final Intent intent = new Intent(action);
    	
    	List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
    	
    	return list.size() > 0;
    }
    
    private void handleSmallCameraPhoto(Intent intent) 
    {
    	Bundle extras = intent.getExtras();
		mImageBitmap = (Bitmap) extras.get("data");
		
		createWine();
		
		//mImageView.setImageBitmap(mImageBitmap);
		//mImageView.setVisibility(View.VISIBLE);
    }

    private void createWine() {
        
    	String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "wineapp_" + timeStamp;
    	
		String noteName = "Wine " + mWineNumber++;
        mDbHelper.createWine(noteName, imageFileName);
    }
    
    
    public void viewGalleryResponse(View v) 
    {
    	Intent myIntent = new Intent(v.getContext(), GalleryActivity.class);
        startActivityForResult(myIntent, 0);
    }
    
}
