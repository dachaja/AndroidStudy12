package com.pettitbusiness.buildlego.youtubeapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.pettitbusiness.buildlego.LegoBookFragment;
import com.pettitbusiness.buildlego.MainActivity;
import com.pettitbusiness.buildlego.VideoFragment;
import com.pettitbusiness.buildlego.VideoListFragment;
import com.pettitbusiness.buildlego.data.YouTubeVideo;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;

public class AsyncSearchKeyWord extends
		AsyncTask<String, Void, ArrayList<YouTubeVideo>> {

	static private final String APIKEY = "AIzaSyD_zzkDfhh6jwYdEvv4SfvOoYzdL7DZKdo";
	static private final String SEARCHKEYWORD = "build-lego-ideas";
	static private final String MAXRESULTS = "10";

	static String testURLv3 = "https://www.googleapis.com/youtube/v3/search?"
			+ "q="
			+ SEARCHKEYWORD
			+ "&order=rating&maxResults="
			+ MAXRESULTS
			+ "&key="
			+ APIKEY
			+ "&part=id,snippet&fields=items(id(videoId),snippet(title,thumbnails(high)))";

	// https://www.googleapis.com/youtube/v3/search?q=build-lego-ideas&order=rating&maxResults=10
	// &key=AIzaSyD_zzkDfhh6jwYdEvv4SfvOoYzdL7DZKdo&part=id,snippet
	// &fields=items%28id%28videoId%29,snippet%28title,thumbnails%28high%29%29%29

	private Fragment returnVideoFragment;
	private Activity returnActivity;
	ArrayList<YouTubeVideo> youTubeVideos = new ArrayList<YouTubeVideo>();

	private static AsyncSearchKeyWord instance;
	private static StringBuilder builder = null;

	public AsyncSearchKeyWord(Fragment fg) {
		this.returnVideoFragment = fg;
	}
	
	public AsyncSearchKeyWord(Activity atv) {
		this.returnActivity = atv;
	}

//	public static AsyncSearchKeyWord getInstance(Fragment fg) {
//		if (instance == null) {
//			synchronized (AsyncSearchKeyWord.class) {
//				if (instance == null) {
//					// System.out.println("getInstance(): First time getInstance was invoked!");
//					instance = new AsyncSearchKeyWord(fg);
//				}
//			}
//		}
//
//		return instance;
//	}

	@Override
	protected ArrayList<YouTubeVideo> doInBackground(String... params) {
		try {
			if(builder == null) {
				builder = getHTTPQuery();
			}

			JSONObject json = new JSONObject(builder.toString());
			JSONArray youtubeVideos = json.getJSONArray("items");

			for (int i = 0; i < youtubeVideos.length(); i++) {
				JSONObject item = youtubeVideos.getJSONObject(i);
				JSONObject id = item.getJSONObject("id");
				String videoId = id.getString("videoId");
				JSONObject snippet = item.getJSONObject("snippet");
				String title = snippet.getString("title");

				JSONObject thumbnails = snippet.getJSONObject("thumbnails");
				JSONObject high = thumbnails.getJSONObject("high");
				String imageUrl = high.getString("url");

				YouTubeVideo ytv = new YouTubeVideo();
				ytv.setVideoId(videoId);
				ytv.setTitle(title);
				ytv.setImageUrl(imageUrl);

				youTubeVideos.add(ytv);

				// Log.v("", videoId + title);
			}

			return youTubeVideos;

		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());

			return null;
		}
	}

	@Override
	protected void onPostExecute(ArrayList<YouTubeVideo> result) {

		//((VideoListFragment) returnVideoFragment).showVideoList(_youTubeVideos);
		//((LegoBookFragment) returnVideoFragment).set_youTubeVideo(_youTubeVideos);
		((MainActivity) returnActivity).youTubeVideos = youTubeVideos;
		
		super.onPostExecute(result);
	}

	private static StringBuilder getHTTPQuery() {
		HttpClient httpclient = new DefaultHttpClient();

		// //////////////////////////
		// HTTP GET
		HttpGet httpGet = new HttpGet(testURLv3);
		StringBuilder _builder = new StringBuilder();
		
		try {
			HttpResponse response = httpclient.execute(httpGet);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), "UTF-8"));
			
			for (String line = null; (line = reader.readLine()) != null;) {
				_builder.append(line).append("\n");
			}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());

			return null;
		}

		return _builder;
	}
}
