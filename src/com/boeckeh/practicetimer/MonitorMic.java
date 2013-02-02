package com.boeckeh.practicetimer;

import java.util.Arrays;
import java.util.Timer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


public class MonitorMic
{
	public boolean shouldStop = false;
	public Timer tmr_CUCD = new Timer();
	public Handler maxHandler;
	
	MonitorMic(Handler aHandler)
	{
		maxHandler = aHandler;
	}
	
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

	public void StopMonitoringMic()
	{
		shouldStop = true;
	}
	
	public Runnable GetRunnable()
	{
		return audioRunnable;
	}
	
}
