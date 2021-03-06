package utilities;

import java.util.Queue;
import java.util.LinkedList;

public class AdHuffmanNode {
	public int number;
	public int weight;
	public char data;	
	public String code;	
	public AdHuffmanNode left;
	public AdHuffmanNode right;
	public AdHuffmanNode parent;
	
	public AdHuffmanNode(){
		number = 65536;
		weight = 0;
		data = 0;
		code = "";
		left = null;
		right = null;
		parent = null;
	}
	
	public AdHuffmanNode(int w, AdHuffmanNode p){
		number = 0;
		weight = w;
		data = 0;
		code = "";
		left = null;
		right = null;
		parent = p;
	}
	
	public AdHuffmanNode findData(char d){
		Queue<AdHuffmanNode> q = new LinkedList<AdHuffmanNode>();
		AdHuffmanNode n = null;
		AdHuffmanNode NYT = null;
		q.add(this);
		while(!q.isEmpty()){
			n=q.poll();
			if(n.weight == 0) NYT = n;
			if(n.data == d) return n;
			if(n.right != null) q.add(n.right);
			if(n.left != null) q.add(n.left);
		}
		
		return NYT;
	}
	
	public AdHuffmanNode addData(char d){
		if(this.weight == 0)
		{
			this.right = new AdHuffmanNode(1,this);
			this.right.data = d;
			this.right.code = this.code + '1';
			this.left = new AdHuffmanNode(0,this);
		}
		return this;
	}
	
	public void renumber(){
		int counter = 65536;
		Queue<AdHuffmanNode> q = new LinkedList<AdHuffmanNode>();
		AdHuffmanNode n = null;
		q.add(this);
		while(!q.isEmpty()){
			n=q.poll();
			n.number = counter; counter--;
			if(n.right != null) q.add(n.right);
			if(n.left != null) q.add(n.left);
		}
	}
	
	public void recode(String c){
		this.code = c;
		if(right != null) right.recode(c+'1');
		if(left != null) left.recode(c+'0');
	}
	
	public AdHuffmanNode findCandidate(AdHuffmanNode node){
		Queue<AdHuffmanNode> q = new LinkedList<AdHuffmanNode>();
		AdHuffmanNode n = null;
		q.add(this);
		while(!q.isEmpty()){
			n=q.poll();
			if((n.number > node.number)&&(n.weight == node.weight)&&(node.parent != n)) return n;
			if(n.number < node.number) return null;
			
			if(n.right != null) q.add(n.right);
			if(n.left != null) q.add(n.left);
		}
		
		return null;
	}
	
	public void treeTrace(){
		Queue<AdHuffmanNode> q = new LinkedList<AdHuffmanNode>();
		AdHuffmanNode n = null;
		q.add(this);
		while(!q.isEmpty()){
			n=q.poll();
			System.out.println(n.data + "   " + n.weight + "  " + n.code + "  " + n.number);
			if(n.right != null) q.add(n.right);
			if(n.left != null) q.add(n.left);
		}
	}
}
