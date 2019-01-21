package main;

public class AudioTimeDataPair {
	static int TIME_SIZE = Constants.TIME_SIZE;
	static int SAMPLE_FREQ = Constants.SAMPLE_FREQ;
	static int SAMPLE_SIZE = Constants.SAMPLE_SIZE;
	
	private double[] timeData;
	private double[] sampleData;
	
	public AudioTimeDataPair(double[] times, double[] samples) {
		timeData = times;
		sampleData = samples;
	}
	
	public double[] getTime() {
		return timeData;
	}
	
	public double[] getSamples() {
		return sampleData;
	}
	
}
