package com.malbert.tempo.model;

import java.util.ArrayList;
import java.util.List;

public class ArrayBasedHierarchy implements Hierarchy {
	private final int[] nodeIds;
	private final int[] depths;

	public ArrayBasedHierarchy(int[] nodeIds, int[] depths) {
		this.nodeIds = nodeIds;
		this.depths = depths;
	}

	@Override
	public int size() {
		return depths.length;
	}

	@Override
	public int nodeId(int index) {
		return nodeIds[index];
	}

	@Override
	public int depth(int index) {
		return depths[index];
	}

	@Override
	public Hierarchy ancestors(int index) {
		final List<Integer> ids = new ArrayList<>();
		final List<Integer> depth = new ArrayList<>();

		// Get the depth of the current index minux 1.
		int currentDepth = depth(index) - 1;

		// Loop from the index to 0
		for (int i = index; i >= 0; i--) {
			// If we change depth, return the next one (eg. it is not a sibling)
			if (currentDepth == this.depth(i)) {
				// Add the current node
				ids.add(nodeId(i));
				depth.add(depth(i));

				currentDepth--;
			}
		}

		return new ArrayBasedHierarchy(
				ids.stream().mapToInt(Integer::intValue).toArray(),
				depth.stream().mapToInt(Integer::intValue).toArray());
	}
}
