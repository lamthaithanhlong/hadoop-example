package com.longltt.hadoop.examples.stripe;

import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Writable;

public class myMapWritable extends MapWritable {

	public myMapWritable() {
		super();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		for (Entry<Writable, Writable> entry : entrySet()) {
			sb.append(String.format("(%s, %s), ", entry.getKey().toString(), entry.getValue().toString()));
		}
		if (sb.length() > 1) {
			sb.setLength(sb.length() - 2);
		}
		sb.append("]");
		return sb.toString();
	}

}
