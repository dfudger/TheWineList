package fudgestudios.wineapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ViewBottleActivity extends Activity
{
	private WineDBAdapter mDbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        Log.w("WineApp","View Bottle View");
        
        /*** Display the GUI ***/
        setContentView(R.layout.view_bottle);
        
        /*** Display the image on the screen ***/
        //mImageBitmap = (Bitmap) getIntent().getExtras().get("bitmap");
        //y = (ImageView) findViewById(R.id.imageView2);
 
        //y.setImageBitmap(mImageBitmap);
        //y.setVisibility(View.VISIBLE);
        
        /*** Open up DB ***/
        mDbHelper = new WineDBAdapter(this);
        mDbHelper.open();   
        
        //Button btnSave = (Button) findViewById(R.id.btnSave);
        //btnSave.setOnClickListener(new View.OnClickListener() 
        //{
          //  public void onClick(View v) 
           // {
                //DisplayToast("You have clicked the Save button");
           // 	createWine();
           // }
        //});
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}
