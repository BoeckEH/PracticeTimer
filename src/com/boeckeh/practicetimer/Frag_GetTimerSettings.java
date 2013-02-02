package com.boeckeh.practicetimer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class Frag_GetTimerSettings extends Fragment {
	
	SeekBar mSeekBar;
	View root;
	String toBeSet;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
 
		root = inflater.inflate(R.layout.get_timer_settings, container, false);
		mSeekBar = (SeekBar) root.findViewById(R.id.sb_time2run);
		mSeekBar.setOnSeekBarChangeListener(mySBCL);
		return root;
	}
	
	OnSeekBarChangeListener mySBCL = new OnSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			
			TextView tvTextVersion = (TextView) root.findViewById(R.id.tv_hrminsec);
			
			int hours = progress / 60;
			int mins = progress % 60;
			
			toBeSet = String.format("%02d:%02d", hours, mins);
			
			
			tvTextVersion.setText(toBeSet);

			
			// TODO Auto-generated method stub
			
		}
	};

	public String GetTimerValue()
	{
		return toBeSet;
	}
	
	
}
