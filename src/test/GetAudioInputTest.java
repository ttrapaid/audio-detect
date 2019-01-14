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
import main.Constants;

/**
 * Simple test to determine success and accuracy of GetAudioInput. 
 * @author timmyt
 *
 */
public class GetAudioInputTest {
	static int TIME_SIZE = Constants.TIME_SIZE;
	static int SAMPLE_SIZE = Constants.SAMPLE_SIZE;
	public static void main(String [] args) throws LineUnavailableException {
		AudioFormat format 	= new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, SAMPLE_SIZE, 16, 1, 2, 8000, true);
		DataLine.Info info 	= new DataLine.Info(TargetDataLine.class, format);
		TargetDataLine line 	= (TargetDataLine) AudioSystem.getLine(info);
		
		double[][] data = GetAudioInput.openLineForTenSeconds(line, format);
		
		double[] time = new double[TIME_SIZE * 4];
		double[] samples = new double[SAMPLE_SIZE * 4];
		
		for (int i = 0; i < data.length; i++) {
			time[i] = data[0][i];
			samples[i] = data[1][i];
		}
		
		WaveUtils.getFrequencyFromData(samples, time, SAMPLE_SIZE, 2);
		
		
		final XYChart chart = QuickChart.getChart("Sample vs SampleNum", "Sample Num", "Sample", "sample", time, samples);
		final SwingWrapper<XYChart> sw = new SwingWrapper<XYChart>(chart);
		sw.displayChart();
		
		chart.updateXYSeries("sample", time, samples, null);
	    
		sw.repaintChart();
	
		
	
	}
}
