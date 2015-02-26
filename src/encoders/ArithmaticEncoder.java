package encoders;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

public class ArithmaticEncoder extends Encoder {

	static{
		try {
			EncoderFactory.register("ArithmaticEncoder", ArithmaticEncoder.class.getConstructor());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void encode(BufferedInputStream inputStream,
			BufferedOutputStream outputStream) {
		System.out.println("ArithmaticEncoder.encode()");
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
		System.out.println("ArithmaticEncoder.decode()");
		setChanged();
		notifyObservers(50);
		try { Thread.sleep(3000); } 
		catch (Exception e) { e.printStackTrace(); }
		setChanged();
		notifyObservers(100);
		System.out.println("here");
	}
}
