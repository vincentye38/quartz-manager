package org.quartz.GWTQuartzManager.share;

import java.io.Serializable;

public class Pair<K, V> implements Serializable{
	K key;
	V value;
	
	public Pair(){}
	
	public Pair(K key, V value){
		this.key = key;
		this.value = value;
	}
	public K getKey() {
		return key;
	}
	public void setKey(K key) {
		this.key = key;
	}
	public V getValue() {
		return value;
	}
	public void setValue(V value) {
		this.value = value;
	}
	
	public String toString(){
		return "{key="+key.toString()+",value="+value.toString()+"}";
	}
}
