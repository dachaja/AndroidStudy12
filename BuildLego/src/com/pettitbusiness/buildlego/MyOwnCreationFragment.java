package com.pettitbusiness.buildlego;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyOwnCreationFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		

		return inflater.inflate(R.layout.activity_my_own_creation_fragment, container, false);
	}

}
