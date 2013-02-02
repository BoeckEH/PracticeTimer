package com.boeckeh.practicetimer;

import java.util.Arrays;
import java.util.Timer;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.AudioRecord.OnRecordPositionUpdateListener;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

public class GetAudioFromMic {
	
	private AudioRecord aRecorder; 
	
	private int audioSource = MediaRecorder.AudioSource.VOICE_RECOGNITION;
	private int sampleRateInHz = 44100;
	private int channelConfig = AudioFormat.CHANNEL_IN_MONO;
	private int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
	private int minBufferSize;
	private int bufferReadNum;
	public static short[] buffer;

	
	GetAudioFromMic() {
        minBufferSize = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);
        aRecorder = new AudioRecord(audioSource, sampleRateInHz, channelConfig, audioFormat, minBufferSize * 10);
		buffer = new short[minBufferSize*2];
		Arrays.fill(buffer, (short) 0);
		aRecorder.setRecordPositionUpdateListener(myListener);
		aRecorder.setPositionNotificationPeriod(minBufferSize);
		
		
	}
	
	private OnRecordPositionUpdateListener myListener = new OnRecordPositionUpdateListener() {
		
		public void onPeriodicNotification(AudioRecord recorder) {
			bufferReadNum = recorder.read(buffer, 0, minBufferSize);
			
		}
		
		public void onMarkerReached(AudioRecord recorder) {
			
		}
	};
	
	public short[] startRecording() {

		
        try {
			aRecorder.startRecording();
			// just do it once, and it's self sustaining it would seem
			bufferReadNum = aRecorder.read(buffer, 0, minBufferSize);
		} catch (IllegalStateException e) {
			aRecorder.stop();
			e.printStackTrace();
			Log.d("GetAudio", "GetAudio failed");
		}
		return buffer;

    }
	
	public void stopRecording(){
		aRecorder.stop();
		aRecorder.release();
	}
	
	public int GetMinBufferSize()
	{
		return minBufferSize;
	}
	
	public short[] getAudioBuffer(){
		return buffer;
	}

}

