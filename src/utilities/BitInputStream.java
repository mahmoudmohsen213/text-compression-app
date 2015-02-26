package utilities;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitInputStream extends InputStream {
	private BufferedInputStream inputStream;
	private byte currentByte;
	private byte currentIndex;
	
	public BitInputStream(BufferedInputStream inputStream){
		this.inputStream = inputStream;
		currentByte = 0;
		currentIndex = 8;
	}

	@Override
	public int read() throws IOException {
		if(currentIndex == 8){
			int tempCurrentByte = inputStream.read();
			if(tempCurrentByte == -1)
				return -1;
			else{
				currentByte = (byte)tempCurrentByte;
				currentIndex = 0;
			}
		}
		
		return ((currentByte>>(currentIndex++))&1);
	}
	
	public byte readByte() throws IOException{
		byte inByte = 0;
		int tempBit;
		for(int i=0;i<8;++i){
			tempBit = this.read();
			if(tempBit == -1)
				return inByte;
			inByte |= (tempBit<<i);
		}
		
		return inByte;
	}
	
	public char readChar() throws IOException{
		char inChar = 0;
		int tempBit;
		for(int i=0;i<16;++i){
			tempBit = this.read();
			if(tempBit == -1)
				return inChar;
			inChar |= (tempBit<<i);
		}
		
		return inChar;
	}
	
	public String readBitString(int siz) throws IOException{
		String inStr = "";
		int tempBit;
		for(int i=0;i<siz;++i){
			tempBit = this.read();
			if(tempBit == -1)
				return inStr;
			inStr += ((tempBit == 1)?'1':'0');
		}
		
		return inStr;
	}
	
	public void close() throws IOException{
		inputStream.close();
	}
}
