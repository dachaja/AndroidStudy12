package com.pettitbusiness.buildlego;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.zip.Inflater;

import com.pettitbusiness.buildlego.asynctask.FetchImage;
import com.pettitbusiness.buildlego.data.YouTubeVideo;
import com.pettitbusiness.buildlego.youtubeapi.AsyncSearchKeyWord;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class VideoListFragment extends Fragment {

	RelativeLayout rl;
	
	private static final int IMAGE_WIDTH = 400;
	private static final int IMAGE_HEIGHT = (IMAGE_WIDTH / 3) * 2;
	private static final int BOTTOM_MARGIN = 16;
	
	private ArrayList<YouTubeVideo> youTubeVideos = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
//		AsyncSearchKeyWord asyncsw = new AsyncSearchKeyWord(this);
//		asyncsw.execute("");
		
		View view = inflater.inflate(R.layout.activity_video_list_fragment, container, false);
		
		rl = (RelativeLayout) view.findViewById(R.id.videoListLayout);
		rl.setVerticalScrollBarEnabled(true);
		rl.setHorizontalScrollBarEnabled(true);

		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		youTubeVideos = ((MainActivity)activity).youTubeVideos;
		super.onAttach(activity);
	}
	
	@Override
	public void onStart() {
		if(youTubeVideos != null){
			float x = 0, y = 0;
			
			Fragment videofragment = new VideoFragment();
			FragmentTransaction ft = getChildFragmentManager().beginTransaction();
			ft.add(R.id.contentfragment, videofragment).commit();
			//getChildFragmentManager().executePendingTransactions();
			
			//rl = (RelativeLayout) this.getView().findViewById(R.id.videoListLayout);
	
			for(int i=0;i<youTubeVideos.size();i++){
				ImageButton imgbtn = new ImageButton(getActivity());
				imgbtn.setId(i+1);
				
				TextView textView= new TextView(getActivity());
				textView.setText(youTubeVideos.get(i).getTitle());
				
				try {
					AsyncTask<String, Integer, Bitmap> btmap = new FetchImage().execute(youTubeVideos.get(i).getImageUrl());
					imgbtn.setImageBitmap(btmap.get());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
				
				imgbtn.setLayoutParams(params2);
				//imgbtn.setBackgroundColor(Color.RED);
				imgbtn.setScaleType(ScaleType.FIT_XY);
				imgbtn.getLayoutParams().width = IMAGE_WIDTH;
				imgbtn.getLayoutParams().height = IMAGE_HEIGHT;
				imgbtn.setX(x);
				imgbtn.setY(y);
				
				textView.setTextSize(20);
				textView.setX(x + IMAGE_WIDTH + 10);
				textView.setY(y);
				textView.setTextColor(Color.YELLOW);
				
				//textView.setLayoutParams(params2);
				rl.addView(imgbtn);
				rl.addView(textView);
				
				//sv.addView(imgbtn);
				//sv.addView(textView);
				
				y = imgbtn.getY() + IMAGE_HEIGHT;
				
				//tv.setText(textView.getText().toString());
				
				Log.v(Float.toString(y), Float.toString(y));
				
				imgbtn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						String videoId = youTubeVideos.get(v.getId()-1).getVideoId();
						VideoFragment vf = new VideoFragment().getInstance();
						
						FragmentManager fm = getFragmentManager();
						FragmentTransaction ft = fm.beginTransaction();
						ft.replace(R.id.contentfragment, vf);
						ft.commit();
						
						vf.setVideoId(videoId);
						if(vf.youTubePlayer != null){
							vf.play();	
						}
					}
				});
			}
			
			rl.setMinimumWidth((int)x);
			rl.setMinimumHeight((int)y + BOTTOM_MARGIN);
		}
		
		super.onStart();
	}
	
//	public void showVideoList(ArrayList<YouTubeVideo> youTubeVideo){
////		Toast.makeText(this.getView().getContext(), 
////				"YouTubePlayer.showVideoList()", 
////				Toast.LENGTH_LONG).show();
//		Log.v("", "" + youTubeVideo.size());
//		
//		if(youTubeVideo != null){
//			_youTubeVideo = youTubeVideo;
//			
//			float x = 0, y = 0;
//			
//			//rl = (RelativeLayout) this.getView().findViewById(R.id.videoListLayout);
//	
//			for(int i=0;i<youTubeVideo.size();i++){
//				ImageButton imgbtn = new ImageButton(getActivity());
//				imgbtn.setId(i+1);
//				
//				TextView textView= new TextView(getActivity());
//				textView.setText(youTubeVideo.get(i).getTitle());
//				
//				try {
//					AsyncTask<String, Integer, Bitmap> btmap = new FetchImage().execute(youTubeVideo.get(i).getImageUrl());
//					imgbtn.setImageBitmap(btmap.get());
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//				LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
//						LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
//				
//				imgbtn.setLayoutParams(params2);
//				//imgbtn.setBackgroundColor(Color.RED);
//				imgbtn.setScaleType(ScaleType.FIT_XY);
//				imgbtn.getLayoutParams().width = IMAGE_WIDTH;
//				imgbtn.getLayoutParams().height = IMAGE_HEIGHT;
//				imgbtn.setX(x);
//				imgbtn.setY(y);
//				
//				textView.setTextSize(20);
//				textView.setX(x + IMAGE_WIDTH + 10);
//				textView.setY(y);
//				textView.setTextColor(Color.YELLOW);
//				
//				//textView.setLayoutParams(params2);
//				rl.addView(imgbtn);
//				rl.addView(textView);
//				
//				//sv.addView(imgbtn);
//				//sv.addView(textView);
//				
//				y = imgbtn.getY() + IMAGE_HEIGHT;
//				
//				//tv.setText(textView.getText().toString());
//				
//				Log.v(Float.toString(y), Float.toString(y));
//				
//				imgbtn.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						String videoId = _youTubeVideo.get(v.getId()-1).getVideoId();
//						VideoFragment vf = new VideoFragment().getInstance();
//						
//						FragmentManager fm = getFragmentManager();
//						FragmentTransaction ft = fm.beginTransaction();
//						ft.replace(R.id.contentfragment, vf);
//						ft.commit();
//						
//						vf.setVideoId(videoId);
//						if(vf.youTubePlayer != null){
//							vf.play();	
//						}
//					}
//				});
//			}
//			
//			rl.setMinimumWidth((int)x);
//			rl.setMinimumHeight((int)y + BOTTOM_MARGIN);
//		}
//	}
}
