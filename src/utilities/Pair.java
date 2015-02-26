package utilities;

public class Pair implements Comparable<Pair> {

    public long first;
    public String second;

    public Pair() {}
    
    public Pair(long first, String second) {
        this.first = first;
        this.second = second;
    }

    public void Copy(Pair p){
    	this.first = p.first;
    	this.second = p.second;
    }

    @Override
    public int compareTo(Pair p) {
        if(this.first<p.first)  return -1;
        if(this.first>p.first) return 1;
        if(this.first==p.first){
        	if(this.second.equals(p.second)) return 0;
        	if(this.second.length()<p.second.length()) return -1;
        	if(this.second.length()>p.second.length()) return 1;
        	for(int i=0;i<this.second.length();i++){
        		if(this.second.charAt(i)<p.second.charAt(i)) return -1;
        		if(this.second.charAt(i)>p.second.charAt(i)) return 1;
        	}
        }
        
        return 0;
    }    

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair))
            return false;
        if (this == obj)
            return true;
        return ((this.first == ((Pair)obj).first ) && (this.second.equals(((Pair)obj).second)));
    }
}
