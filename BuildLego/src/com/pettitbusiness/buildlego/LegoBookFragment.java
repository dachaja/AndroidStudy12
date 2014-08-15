package com.pettitbusiness.buildlego;

import java.util.ArrayList;

import com.pettitbusiness.buildlego.data.YouTubeVideo;
import com.pettitbusiness.buildlego.youtubeapi.AsyncSearchKeyWord;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

public class LegoBookFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.activity_lego_book_fragment, container, false);
	}	
}
