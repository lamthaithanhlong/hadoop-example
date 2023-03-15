package com.longltt.hadoop.examples.pair;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class Pair implements WritableComparable<Pair> {

	public Text key1;

	public Text key2;

	public Pair() {
		key1 = new Text();
		key2 = new Text();
	}

	public Pair(String key1, String key2) {
		this.key1 = new Text(key1);
		this.key2 = new Text(key2);
	}

	public void setKey1(Text key1) {
		this.key1 = key1;
	}

	public void setKey2(Text key2) {
		this.key2 = key2;
	}

	public Text getKey1() {
		return key1;
	}

	public Text getKey2() {
		return key2;
	}

	@Override
	public boolean equals(Object b) {
		Pair p = (Pair) b;
		return p.key1.toString().equals(this.key1.toString())
				&& p.key2.toString().equals(this.key2.toString());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((key1 == null) ? 0 : key1.toString().hashCode());
		result = prime * result
				+ ((key2 == null) ? 0 : key2.toString().hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "(" + key1 + ", " + key2 + ")";
	}

	@Override
	public void readFields(DataInput arg0) throws IOException {
		key1.readFields(arg0);
		key2.readFields(arg0);
	}

	@Override
	public void write(DataOutput arg0) throws IOException {
		key1.write(arg0);
		key2.write(arg0);
	}

	public int compareTo(Pair p1) {
		int k = this.key1.toString().compareTo(p1.key1.toString());

		if (k != 0) {
			return k;
		} else {
			return this.key2.toString().compareTo(p1.key2.toString());
		}
	}

}
