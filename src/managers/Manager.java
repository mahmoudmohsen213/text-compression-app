package managers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.JOptionPane;

import ui.MainFrame;
import encoders.Encoder;
import encoders.EncoderFactory;

public abstract class Manager implements Runnable {
	protected String inputFileName;
	protected String outputFileName;
	protected String encoderID;
	protected BufferedInputStream inputStream;
	protected BufferedOutputStream outputStream;
	protected Encoder encoder;
	protected MainFrame mainFrame;
	protected Thread thread;
	protected boolean isGood;
	protected long inputSize;
	
	public Manager(String inputFileName, String outputFileName,
			String encoderID, MainFrame mainFrame){
		this.inputFileName = inputFileName;
		this.outputFileName = outputFileName;
		this.encoderID = encoderID;
		this.mainFrame = mainFrame;
		this.thread = new Thread(this);
	}
	
	protected boolean initialize(){
		try{
			File tempFile = new File(inputFileName);
			inputSize = tempFile.length();
			
			inputStream = new BufferedInputStream(
					new FileInputStream(inputFileName));
		} catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
			return false;
		}
		
		try{
			outputStream = new BufferedOutputStream(
					new FileOutputStream(outputFileName));
		} catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
			return false;
		}
		
		try {
			encoder = EncoderFactory.create(encoderID);
			encoder.addObserver(mainFrame);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public final void start(){
		if(isGood) thread.start();
	}
}
