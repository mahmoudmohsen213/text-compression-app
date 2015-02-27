package encoders;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import utilities.BitInputStream;
import utilities.BitOutputStream;
import utilities.Node;

public class AdHuffmanEncoder extends Encoder {
	
	static{
		try {
			EncoderFactory.register("AdHuffmanEncoder",
					AdHuffmanEncoder.class.getConstructor());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void encode(BufferedInputStream inputStream,
			BufferedOutputStream outputStream, int inputSize) throws Exception  {
		setChanged();
		notifyObservers(0);
		
		char currentChar;
		Node root = new Node();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BitOutputStream bitOutputStream = new BitOutputStream(outputStream);
		
		int counter = 0;
		
		currentChar = (char) inputStreamReader.read();
		bitOutputStream.writeChar(currentChar);
		root.addData(currentChar);
		++root.weight;
		root.renumber();
		root.recode("");
		
		while(inputStreamReader.ready()){
			currentChar = (char) inputStreamReader.read();
			Node node = root.findData(currentChar);
			if(node.weight == 0) { // new character
				bitOutputStream.writeBitString(node.code); // add NYT code
				bitOutputStream.writeChar(currentChar); // add ASCII code
				node = node.addData(currentChar); // node points to old NYT-node
				root.renumber();
				root.recode("");
			}
			else bitOutputStream.writeBitString(node.code);
			
			// update the tree
			while(node != root){
				Node tempNode = root.findCandidate(node); // find a swap candidate
				if(tempNode != null) { // swap candidate found
					swapNode(node, tempNode);
					root.renumber();
					root.recode("");
				}
				node.weight++;
				node = node.parent;
			}
			root.weight++;
			
			if(((counter+=2)%1024) == 0){
				setChanged();
				notifyObservers((int)((counter*100)/inputSize));
			}
		}
		
		Node node = root.findData(Character.MAX_VALUE); // node points to NYT-node
		bitOutputStream.writeBitString(node.code); // add NYT code
		bitOutputStream.writeChar(Character.MAX_VALUE); // add EOF bit mark
		
		inputStreamReader.close();
		bitOutputStream.close();
		setChanged();
		notifyObservers(100);
	}

	@Override
	public void decode(BufferedInputStream inputStream,
			BufferedOutputStream outputStream, int inputSize) throws Exception {
		setChanged();
		notifyObservers(0);
		
		int currentBit = 0;
		Node root = new Node(), node = root;
		BitInputStream bitInputStream = new BitInputStream(inputStream);
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
		
		int counter = 0;
		
		do{
			if((node.left == null)&&(node.right == null)){ // leaf
				if(node.weight == 0){ // NYT node
					char currentChar = bitInputStream.readChar();
					if(currentChar == Character.MAX_VALUE) break; // EOF reached
					outputStreamWriter.write(currentChar);
					node = node.addData(currentChar); // point to old NYT node
					root.renumber();
					root.recode("");
				}
				else outputStreamWriter.write(node.data); // old character
				
				while(node != root){
					Node tempNode = root.findCandidate(node); // find a swap candidate
					if(tempNode != null) { // swap candidate found
						swapNode(node, tempNode);
						root.renumber();
						root.recode("");
					}
					node.weight++;
					node = node.parent;
				}
				root.weight++;
				node = root;
				counter+=16;
			}
			else {
				currentBit = bitInputStream.read();
				if(currentBit == 1) node = node.right;
				else if(currentBit == 0) node = node.left;
				++counter;
			}
			
			if(((counter>>3)%1024) == 0){
				setChanged();
				notifyObservers((int)(((counter>>3)*100)/inputSize));
			}
			
		}while(currentBit != -1);
		
		bitInputStream.close();
		outputStreamWriter.close();
		setChanged();
		notifyObservers(100);
	}
	
	private void swapNode(Node n1, Node n2){
		if(n1.parent.left == n1) n1.parent.left = n2;
		else n1.parent.right = n2;
		
		if(n2.parent.left == n2) n2.parent.left = n1;
		else n2.parent.right = n1;
		
		Node temp = n1.parent;
		n1.parent = n2.parent;
		n2.parent = temp;
		temp = null;
	}
}
