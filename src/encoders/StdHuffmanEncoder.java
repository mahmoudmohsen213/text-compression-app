package encoders;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

public class StdHuffmanEncoder extends Encoder {

	static{
		try {
			EncoderFactory.register("StdHuffmanEncoder",
					StdHuffmanEncoder.class.getConstructor());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void encode(BufferedInputStream inputStream,
			BufferedOutputStream outputStream, long inputSize) throws Exception  {
		System.out.println("StdHuffmanEncoder.encode()");
		setChanged();
		notifyObservers(50);
		try { Thread.sleep(3000); } 
		catch (Exception e) { e.printStackTrace(); }
		setChanged();
		notifyObservers(100);
		System.out.println("here");
	}

	@Override
	public void decode(BufferedInputStream inputStream,
			BufferedOutputStream outputStream, long inputSize) throws Exception {
		System.out.println("StdHuffmanEncoder.decode()");
		setChanged();
		notifyObservers(50);
		try { Thread.sleep(3000); } 
		catch (Exception e) { e.printStackTrace(); }
		setChanged();
		notifyObservers(100);
		System.out.println("here");
	}
}
