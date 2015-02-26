package managers;

import main.MainFrame;

public class EncodingManager extends Manager {
	
	public EncodingManager(String inputFileName, String outputFileName,
			String encoderID, MainFrame mainFrame) {
		super(inputFileName, outputFileName, encoderID, mainFrame);
		isGood = this.initialize();
	}

	@Override
	public void run() {
		this.encoder.encode(inputStream, outputStream);
	}
}
