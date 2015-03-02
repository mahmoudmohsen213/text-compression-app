package encoders;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import utilities.BitInputStream;
import utilities.BitOutputStream;
import utilities.Pair;
import utilities.StdHuffmanNode;

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
			BufferedOutputStream outputStream, Object...args) throws Exception  {
		setChanged();
		notifyObservers(0);
		
		char currentChar;
		int tempReadValue;
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BitOutputStream bitOutputStream = new BitOutputStream(outputStream);
		HashMap<Character,Long> frequencyTable = new HashMap<Character,Long>();
		
		long dataCounter = 0;
		long inputSize = (Integer)args[0];
		
		if(!inputStream.markSupported()){
			inputStreamReader.close();
			bitOutputStream.close();
			throw new UnsupportedOperationException(
					"mark not supported by the input stream");
		}
		
		inputStream.mark(Integer.MAX_VALUE);
		tempReadValue = inputStreamReader.read();
		while(tempReadValue != -1){ // counting each character
			currentChar = (char) tempReadValue;
			tempReadValue = inputStreamReader.read();
			Long tempFrequency = frequencyTable.get(currentChar);
			if(tempFrequency == null) frequencyTable.put(currentChar, (long) 1);
			else frequencyTable.put(currentChar, tempFrequency + 1);
		}
		frequencyTable.put(Character.MAX_VALUE, (long) 1);
		
		inputStream.reset();
		
		ArrayList<Pair<Long, String>> frequencyArray =
				new ArrayList<Pair<Long, String>>();
		for(Character key : frequencyTable.keySet())
			frequencyArray.add(new Pair<Long, String>(frequencyTable.get(key), key.toString()));
		
		HashMap<String,String> tempCodeTable = buildCodeTable(frequencyArray);
		HashMap<Character,String> codeTable = new HashMap<Character, String>();
		for(String key : tempCodeTable.keySet()){
			String binaryCode = tempCodeTable.get(key);
			//System.out.println(key + "  " + binaryCode);
			codeTable.put(key.charAt(0), binaryCode);
			bitOutputStream.writeChar(key.charAt(0)); // the data
			bitOutputStream.writeByte((byte) binaryCode.length()); // the length the binaryCode
			bitOutputStream.writeBitString(binaryCode); // the bitCode
		}
		bitOutputStream.writeChar((char)(Character.MAX_VALUE - 1)); // add end of table bit mark
		tempCodeTable = null;
		
		tempReadValue = inputStreamReader.read();
		while(tempReadValue != -1){
			currentChar = (char) tempReadValue;
			tempReadValue = inputStreamReader.read();
			bitOutputStream.writeBitString(codeTable.get(currentChar));
			if(((dataCounter+=2)%1024) == 0){
				setChanged();
				notifyObservers((int)((dataCounter*100)/inputSize));
			}
		}
		bitOutputStream.writeBitString(codeTable.get(Character.MAX_VALUE)); // add EOF bit mark
		
		inputStreamReader.close();
		bitOutputStream.close();
		setChanged();
		notifyObservers(100);
	}

	@Override
	public void decode(BufferedInputStream inputStream,
			BufferedOutputStream outputStream, Object...args) throws Exception {
		setChanged();
		notifyObservers(0);
		
		char currentChar;
		int currentBit = 0;
		StdHuffmanNode root = new StdHuffmanNode(), node = root;
		BitInputStream bitInputStream = new BitInputStream(inputStream);
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
		
		long dataCounter = 16;
		long inputSize = (Integer)args[0];
		
		currentChar = bitInputStream.readChar();
		while(currentChar != ((char)(Character.MAX_VALUE - 1))){ // reading the code book
			byte binaryCodeSiz = bitInputStream.readByte();
			String binaryCode = bitInputStream.readBitString(binaryCodeSiz);
			root.insert(currentChar, binaryCode);
			dataCounter+=(16+8+binaryCodeSiz);
			currentChar = bitInputStream.readChar();
		}
		
		currentBit = bitInputStream.read();
		while(currentBit != -1){
			++dataCounter;
			if(currentBit == 0) node = node.first;
			else node = node.second;
			
			if(node.isDataNode){
				if(node.data == Character.MAX_VALUE) break;
				outputStreamWriter.write(node.data);
				node = root;
			}
			
			if(((dataCounter>>3)%1024) == 0){
				setChanged();
				notifyObservers((int)(((dataCounter>>3)*100)/inputSize));
			}
			currentBit = bitInputStream.read();
		}
		
		bitInputStream.close();
		outputStreamWriter.close();
		setChanged();
		notifyObservers(100);
	}
	
	private HashMap<String,String> buildCodeTable(
			ArrayList<Pair<Long, String>> frequencyArray){
		
		if(frequencyArray.size() == 0)
			return new HashMap<String, String>();
		
		if(frequencyArray.size() == 1){
			HashMap<String, String> tempCodeTable = new HashMap<String, String>();
			tempCodeTable.put(frequencyArray.get(0).second, "1");
			return tempCodeTable;
		}
		
		if(frequencyArray.size() == 2){
			HashMap<String, String> tempCodeTable = new HashMap<String, String>();
			tempCodeTable.put(frequencyArray.get(0).second, "1");
			tempCodeTable.put(frequencyArray.get(1).second, "0");
			return tempCodeTable;
		}
		
		Collections.sort(frequencyArray, Collections.reverseOrder());
		Pair<Long, String> secondLast = frequencyArray.get(frequencyArray.size()-2); 
		Pair<Long, String> last = frequencyArray.get(frequencyArray.size()-1);
		String secondLastStr = secondLast.second.substring(0);
		String lastStr = last.second.substring(0);
		secondLast.first += last.first;
		secondLast.second += last.second;
		frequencyArray.remove(frequencyArray.size()-1);
		HashMap<String, String> tempCodeTable = buildCodeTable(frequencyArray);
		String jointBinaryCode = tempCodeTable.remove(secondLastStr + lastStr);
		tempCodeTable.put(secondLastStr, jointBinaryCode + '1');
		tempCodeTable.put(lastStr, jointBinaryCode + '0');
		return tempCodeTable;
	}
}
