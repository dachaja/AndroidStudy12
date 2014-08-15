package com.pettitbusiness.buildlego.asynctask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class FetchImage extends AsyncTask<String, Integer, Bitmap> {

	@Override
	protected Bitmap doInBackground(String... params) {
		try
	    {
	        URL url;
	        url = new URL(params[0]);

	        HttpURLConnection c = ( HttpURLConnection ) url.openConnection();
	        c.setDoInput( true );
	        c.connect();
	        InputStream is = c.getInputStream();
	        Bitmap img;
	        img = BitmapFactory.decodeStream( is );
	        return img;
	    }
	    catch ( MalformedURLException e )
	    {
	        Log.d( "RemoteImageHandler", "fetchImage passed invalid URL: " + params[0] );
	    }
	    catch ( IOException e )
	    {
	        Log.d( "RemoteImageHandler", "fetchImage IO exception: " + e );
	    }
	    return null;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}
}
