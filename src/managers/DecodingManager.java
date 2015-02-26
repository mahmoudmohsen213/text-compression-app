package managers;

import main.MainFrame;

public class DecodingManager extends Manager {
	
	public DecodingManager(String inputFileName, String outputFileName,
			String encoderID, MainFrame mainFrame) {
		super(inputFileName, outputFileName, encoderID, mainFrame);
		isGood = this.initialize();
	}

	@Override
	public void run() {
		this.encoder.decode(inputStream, outputStream);
	}
}
