import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

public class getInput {
	static TargetDataLine line;
	static DataLine.Info info; 
	static AudioFormat format;
	
	static boolean stopped = false;
	
	public static void main(String[] args) throws LineUnavailableException, IOException {
		
		format 	= new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 8000, 16, 1, 2, 8000, true);
		info 	= new DataLine.Info(TargetDataLine.class, format);
		line 	= (TargetDataLine) AudioSystem.getLine(info);
		
		line.open(format);
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int numBytesRead; 
		byte[] data = new byte[line.getBufferSize() / 5];
		
		double scale = Math.pow(2.0, 23);
		double[] samples = new double[8000]; 
		double[] sampleNum = new double[8000];
		
		final XYChart chart = QuickChart.getChart(
				"Sample vs SampleNum", "Sample Num", "Sample", "sample", sampleNum, samples);
		
		final SwingWrapper<XYChart> sw = new SwingWrapper<XYChart>(chart);
	
	    sw.displayChart();
	    
		line.start();
		
		while (!stopped) {
			float sample = 0f; 
			numBytesRead =  line.read(data, 0, data.length);
			out.write(data, 0, numBytesRead);
			
			int s = 0;
			for (int i = 0; i < data.length - 2; i++) {
				long temp = (((data[i    ]) << 16)
						| ((data[i + 1]) <<  8)
						|  (data[i + 2]));
				
				sample = (float) (temp / scale);
				sampleNum[s] = s;
				samples[s++] = sample;
				samples[s] = 1;
				samples[s + 1] = -1;
				
				chart.updateXYSeries("sample", sampleNum, samples, null);
			    sw.repaintChart();
				
			}
			   
		}     
		
//        AudioInputStream ais = new AudioInputStream(line);
//        AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
//        File wavFile = new File("/Users/timmyt/Desktop/output.wav");
//        AudioSystem.write(ais, fileType, wavFile);
		
	}
}

