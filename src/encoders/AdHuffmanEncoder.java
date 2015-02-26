package encoders;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

public class AdHuffmanEncoder extends Encoder {
	
	static{
		try {
			EncoderFactory.register("AdHuffmanEncoder", AdHuffmanEncoder.class.getConstructor());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void encode(BufferedInputStream inputStream,
			BufferedOutputStream outputStream) {
		System.out.println("AdHuffmanEncoder.encode()");
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
			BufferedOutputStream outputStream) {
		System.out.println("AdHuffmanEncoder.decode()");
		setChanged();
		notifyObservers(50);
		try { Thread.sleep(3000); } 
		catch (Exception e) { e.printStackTrace(); }
		setChanged();
		notifyObservers(100);
		System.out.println("here");
	}
}
