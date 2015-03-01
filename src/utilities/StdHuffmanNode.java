package utilities;

public class StdHuffmanNode {
	public char data;
	public boolean isDataNode;
	public StdHuffmanNode first;
	public StdHuffmanNode second;
	
	public StdHuffmanNode(){
		data = 0;
		isDataNode = false;
		first = null;
		second = null;
	}
	
	public StdHuffmanNode(char data){
		this.data = data;
		this.isDataNode = true;
		this.first = null;
		this.second = null;
	}
	
	public void insert(char data, String binaryCode){
		StdHuffmanNode currentNode = this;
		for(int i=0;i<binaryCode.length();++i){
			char currentBit = binaryCode.charAt(i);
			if(currentBit == '0'){
				if(currentNode.first == null)
					currentNode.first = new StdHuffmanNode();
				currentNode = currentNode.first;
			}
			else if(currentBit == '1'){
				if(currentNode.second == null)
					currentNode.second = new StdHuffmanNode();
				currentNode = currentNode.second;
			}
			else throw new RuntimeException("Invalid argument");
		}
		
		currentNode.data = data;
		currentNode.isDataNode = true;
	}
	
	public StdHuffmanNode lookUp(String binaryCode){
		StdHuffmanNode currentNode = this;
		for(int i=0;i<binaryCode.length();++i){
			char currentBit = binaryCode.charAt(i);
			if(currentBit == '0')
				currentNode = currentNode.first;
			else if(currentBit == '1')
				currentNode = currentNode.second;
			else throw new RuntimeException("Invalid argument");
			if(currentNode == null) return null;
		}
		
		return ((currentNode.isDataNode)?currentNode:null);
	}
}
