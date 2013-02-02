package com.boeckeh.practicetimer;

import java.util.Arrays;
import java.util.Timer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Frag_GoTimer extends Fragment {

	public boolean shouldStop = false;
	public boolean shouldUpdate = false;
	public Timer tmr_CUCD = new Timer();
	
	public Runnable audioRunnable = new Runnable() {

		public void run() {

			GetAudioFromMic micAudio = new GetAudioFromMic();
		    int minBuf = micAudio.GetMinBufferSize();
		    short buf[] = new short[minBuf];
		    int maxSoundLevel = 0;
		    long l_processTime = 0;
		    shouldStop = false;
		    Arrays.fill(buf, (short) 0);
		    micAudio.startRecording();
			while (!shouldStop)
			{
				l_processTime = System.currentTimeMillis();
				buf = micAudio.getAudioBuffer();
				for (int ii=0; ii < minBuf; ii++)
				{
					if (Math.abs(buf[ii]) > maxSoundLevel) maxSoundLevel = Math.abs(buf[ii]);
				}
				if (micThread.isInterrupted())  {shouldStop = true;}
				Bundle maxValueDataBundle = new Bundle();
				Message setMaxValMessage = new Message();
				maxValueDataBundle.putInt("MaxVal",maxSoundLevel);
				l_processTime = System.currentTimeMillis() - l_processTime;
				maxValueDataBundle.putLong("ProcTime", l_processTime);
				setMaxValMessage.setData(maxValueDataBundle);
				maxHandler.sendMessage(setMaxValMessage);
				maxSoundLevel = 0;
			}
			micAudio.stopRecording();
		}
	};

    public View myGoTimer;
    public long callsToHandler = 0;
	
    public Thread micThread = new Thread(audioRunnable);
    
    public Handler maxHandler = new Handler() 
    {
    	@Override
    	public void handleMessage(Message msg) 
    	{
    		TextView tv_maxValueDisplay = (TextView) myGoTimer.findViewById(R.id.tv_MaxLevel);
    		Bundle payloadBundle = msg.getData();
    		int payloadInt = payloadBundle.getInt("MaxVal");
    		long proctime = payloadBundle.getLong("ProcTime");
    		callsToHandler++;
    		if (shouldUpdate)
    		{
	    		tv_maxValueDisplay.setText(String.format("ms: %d,  max: %d,  calls: %d",proctime, payloadInt, callsToHandler));
	    		myGoTimer.refreshDrawableState();
    		}
    	}
    };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		

		myGoTimer = inflater.inflate(R.layout.start_timing, container, false);
		TextView currentTimer = (TextView) myGoTimer.findViewById(R.id.tv_Timing);
		tmr_CUCD.schedule(task, when, period)
		return myGoTimer;
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
	super.setUserVisibleHint(isVisibleToUser);

	if (isVisibleToUser) 
	{
		if (micThread.getState() == Thread.State.NEW)  micThread.start();
		shouldUpdate = true;
	} 
	else
	{
		shouldUpdate = false;
	}

	}
	
}
