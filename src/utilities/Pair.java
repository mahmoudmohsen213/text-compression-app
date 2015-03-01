package utilities;


public class Pair<Type1 extends Comparable<Type1> ,Type2 extends Comparable<Type2> >
		implements Comparable<Pair<Type1,Type2> > {

    public Type1 first;
    public Type2 second;

    public Pair() {}
    
    public Pair(Type1 first, Type2 second) {
        this.first = first;
        this.second = second;
    }

    public void Copy(Pair<Type1,Type2> p){
    	this.first = p.first;
    	this.second = p.second;
    }

    @Override
    public int compareTo(Pair<Type1,Type2> p) {
    	int firstComparison = this.first.compareTo(p.first);
    	if(firstComparison != 0) return firstComparison;
    	return this.second.compareTo(p.second);
    }    
    
	@Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
		try{
			if (this == obj) return true;
        	return ((this.first.equals(((Pair<Type1,Type2>)obj).first)) &&
        			(this.second.equals(((Pair<Type1,Type2>)obj).second)));
		}catch(Exception e) { e.printStackTrace(); }
		return false;
    }
}