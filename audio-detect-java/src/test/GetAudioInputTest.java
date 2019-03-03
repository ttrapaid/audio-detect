package test;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import main.WaveUtils;
import main.GetAudioInput;
import main.AudioTimeDataPair;
import main.Constants;

/**
 * Simple test to determine success and accuracy of GetAudioInput. 
 * @author timmyt
 *
 */
public class GetAudioInputTest {
	static int TIME_SIZE = Constants.TIME_SIZE;
	static int SAMPLE_SIZE = Constants.SAMPLE_SIZE;
	static int SAMPLE_FREQ = Constants.SAMPLE_FREQ;
	public static void main(String [] args) throws LineUnavailableException {
		AudioFormat format 	= new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, SAMPLE_FREQ, 16, 1, 2, SAMPLE_SIZE, true);
		DataLine.Info info 	= new DataLine.Info(TargetDataLine.class, format);
		TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
		AudioTimeDataPair data = GetAudioInput.openLineForTenSeconds(line, format);
		
		double[] times = data.getTime();
		double[] samples = data.getSamples();
		

		
		//WaveUtils.getFrequencyFromData(samples, time, SAMPLE_SIZE, 2);
		
		final XYChart chart = QuickChart.getChart("Sample vs Time", "Time", "Sample", "sample", times, samples);
		final SwingWrapper<XYChart> sw = new SwingWrapper<XYChart>(chart);
		sw.displayChart();
		
		chart.updateXYSeries("sample", times, samples, null);
	    
		sw.repaintChart();
	
		
	
	}
}
