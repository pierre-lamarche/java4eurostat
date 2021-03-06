/**
 * 
 */
package eu.ec.java4eurostat.base;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

/**
 * 
 * A statistical value, described by dimension-coordinates in the hypercube, and possibly flags.
 * 
 * @author julien Gaffuri
 *
 */
public class Stat {

	/**
	 * The value
	 */
	public double value;

	/**
	 * The dimensions
	 * Ex: gender -> male ; time -> 2015 ; country -> PL
	 */
	public HashMap<String,String> dims = new HashMap<String,String>();

	/**
	 * The flags providing some metadata information
	 */
	private HashSet<Flag.FlagType> flags = null;

	/**
	 * Add a flag to the statistical value.
	 * @param flag
	 * @return
	 */
	public boolean addFlag(Flag.FlagType flag){
		if(flags == null) flags = new HashSet<Flag.FlagType>();
		return flags.add(flag);
	}

	/**
	 * Add flags to the statistical value.
	 * @param flags
	 */
	public void addAllFlags(String flags) {
		for (int i=0; i<flags.length(); i++){
			Flag.FlagType flag = Flag.code.get(""+flags.charAt(i));
			if(flag == null) {
				System.err.println("Unexpected flag: "+flags.charAt(i)+" for "+this);
				continue;
			}
			addFlag(flag);
		}
	}
	public boolean removeFlag(Flag.FlagType flag){
		if(flags == null) return false;
		return flags.remove(flag);
	}
	public boolean isFlagged(Flag.FlagType flag){
		if(flags == null) return false;
		return flags.contains(flag);
	}

	public String getFlags(){
		if(flags == null || flags.size()==0) return "";
		StringBuffer sb = new StringBuffer();
		for(Flag.FlagType f : flags) sb.append(f);
		return sb.toString();
	}

	public String getValueFlagged(){
		return value + getFlags();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(Entry<String,String> dim:dims.entrySet())
			sb.append(dim.getKey()).append(":").append(dim.getValue()).append(", ");
		sb.append(getValueFlagged());
		return sb.toString();
	}

}
