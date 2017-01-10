package com.decoder;

public class Code{
	public int code;
	public int length;
	
	@Override
	public boolean equals(Object o){
		if(o==null) return false;
		Code x = (Code) o;
		if(this.code == x.code && this.length == x.length) return true;
		return false;
	}
	
	@Override
	public int hashCode(){
		int result = 17;
		result = 31 * result + code;
		result = 31 * result + length;
		return result;
	}
}