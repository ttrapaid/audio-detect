package main;

import java.io.IOException;
import java.util.Date;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 * Utility class to capture audio from an input - by default 
 * the microphone is the input.
 * @author timmyt
 *
 */
public class GetAudioInput {
	static TargetDataLine line;
	static DataLine.Info info; 
	static AudioFormat format;
	
	static boolean stopped = false;
	
	/**
	 * Will return a two-dimensional array with time data and sample data. 
	 * Time goes in 0 column, samples go in 1 column. 
	 * @param line
	 * @param format
	 * @return
	 * @throws LineUnavailableException
	 */
	public static double[][] openLineForTenSeconds(TargetDataLine line, AudioFormat format) throws LineUnavailableException {
		line.open(format);
		
		byte[] data = new byte[line.getBufferSize() / 5];
		
		double scale = Math.pow(2.0, 23);
		double[] samples = new double[8000]; 
		double[] time = new double[8000];
		
		line.start();
		long startTime = System.currentTimeMillis();
		long elapsedTime = 0L;
		
		while (elapsedTime < 10) {
			float sample = 0f; 
			int s = 0;
			for (int i = 0; i < data.length - 2; i++) {
				elapsedTime = ((new Date()).getTime() - startTime)/1000;
				long temp = 	 (((data[i]) << 16) | ((data[i + 1]) << 8) | (data[i + 2]));
				
				sample = (float) (temp / scale);
				time[s] = elapsedTime;
				samples[s++] = sample;
			}
		}
		
		double[][] dataSet = new double[8000][8000];
		
		for (int i = 0; i < data.length; i++) {
			dataSet[0][i] = time[i];
			dataSet[1][i] = samples[i];
		}
		
		return dataSet;	
	}
	
	public static void main(String[] args) throws LineUnavailableException, IOException {
		
		AudioFormat format 	= new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 8000, 16, 1, 2, 8000, true);
		DataLine.Info info 	= new DataLine.Info(TargetDataLine.class, format);
		TargetDataLine line 	= (TargetDataLine) AudioSystem.getLine(info);
		
		openLineForTenSeconds(line, format);
		
		
//		line.open(format);
//		
//		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		int numBytesRead; 
//		byte[] data = new byte[line.getBufferSize() / 5];
//		
//		double scale = Math.pow(2.0, 23);
//		double[] samples = new double[8000]; 
//		double[] sampleNum = new double[8000];
//		
//		final XYChart chart = QuickChart.getChart(
//				"Sample vs SampleNum", "Sample Num", "Sample", "sample", sampleNum, samples);
//		
//		final SwingWrapper<XYChart> sw = new SwingWrapper<XYChart>(chart);
//	
//	    sw.displayChart();
//	    
//		line.start();
//		long startTime = System.currentTimeMillis();
//		long elapsedTime = 0L;
//
//		
//		while (!stopped) {
//			float sample = 0f; 
//			numBytesRead =  line.read(data, 0, data.length);
//			//out.write(data, 0, numBytesRead);
//			int s = 0;
//			for (int i = 0; i < data.length - 2; i++) {
//				elapsedTime = ((new Date()).getTime() - startTime)/1000;
//				long temp = (((data[i    ]) << 16)
//						| ((data[i + 1]) <<  8)
//						|  (data[i + 2]));
//				
//				sample = (float) (temp / scale);
//				sampleNum[s] = s;
//				samples[s++] = sample;
//				
//				
//			    
//			}
//
//			chart.updateXYSeries("sample", sampleNum, samples, null);
//		    
//			samples[s] = 1;
//			samples[s + 1] = -1;
//			sw.repaintChart();
//		}     
//		
//		
//		
	}
}

