package com.pettitbusiness.buildlego;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayer.Provider;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("ValidFragment")
public class VideoFragment extends Fragment implements YouTubePlayer.OnInitializedListener{

	static private final String DEVELOPER_KEY = "AIzaSyD_zzkDfhh6jwYdEvv4SfvOoYzdL7DZKdo";
	private String videoId = null;
	
	public  YouTubePlayer youTubePlayer = null;
	private YouTubePlayerFragment youTubePlayerFragment;
	
	private static VideoFragment instance = null;
	
	public VideoFragment getInstance()
	{
		if (instance == null)
		{
			synchronized(VideoFragment.class)
			{
				if (instance == null)
				{
					//System.out.println("getInstance(): First time getInstance was invoked!");
					instance = new VideoFragment();
				}
			}            
		}

		return instance;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.activity_video_fragment, container, false);
	}
	
	@Override
	public void onStart() {
		
		if(youTubePlayer == null){
			 youTubePlayerFragment = (YouTubePlayerFragment)getFragmentManager()
		        	    .findFragmentById(R.id.youtubeplayerfragment);
		     youTubePlayerFragment.initialize(DEVELOPER_KEY, this);
		}

		super.onStart();
	}

	@Override
	public void onInitializationFailure(Provider arg0,
			YouTubeInitializationResult arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInitializationSuccess(Provider provider, YouTubePlayer player,boolean wasRestored) {
		if(youTubePlayer == null){
			youTubePlayer = player;
		}
		
//		Toast.makeText(this.getView().getContext(), 
//				"YouTubePlayer.onInitializationSuccess()", 
//				Toast.LENGTH_LONG).show();
		play();
		
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	
	public void play() {
		if (videoId != null && youTubePlayer != null) {
			youTubePlayer.cueVideo(videoId);
			youTubePlayer.setFullscreen(true);
		}

	}

	
}
