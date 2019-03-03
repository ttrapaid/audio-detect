package main;

import org.jtransforms.fft.DoubleFFT_2D;

import main.Constants;

public class WaveUtils {
	
	static int TIME_SIZE = Constants.TIME_SIZE;
	static int SAMPLE_SIZE = Constants.SAMPLE_SIZE;
	
	/**
	 * Pull out frequencies from sample data; uses FFT (JTransform). 
	 * 
	 * Assumes that in the two-dimensional array parameter, 0 column is for time data
	 * and 1 column is for sample data.
	 * @param data
	 */ 
	public static void getFrequencyFromData(double[] samples, double[] times, int rows, int cols) {
		DoubleFFT_2D FFT = new DoubleFFT_2D(rows, cols);
		FFT.complexForward(samples);
		
	}
	
}
