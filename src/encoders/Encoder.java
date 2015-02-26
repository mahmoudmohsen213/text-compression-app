package encoders;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.util.Observable;

public abstract class Encoder extends Observable{
	public abstract void encode(BufferedInputStream inputStream,
			BufferedOutputStream outputStream);
	public abstract void decode(BufferedInputStream inputStream,
			BufferedOutputStream outputStream);
}
