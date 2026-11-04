package com.malbert.tempo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A node is present in the filtered hierarchy iff its node ID passes the
 * predicate and all of its ancestors pass it as well.
 */
public class HierarchyFilter {

	public static Hierarchy filter(Hierarchy hierarchy, java.util.function.IntPredicate nodeIdPredicate) {

		final List<Integer> ids = new ArrayList<>();
		final List<Integer> depth = new ArrayList<>();

		// Loop on all nodes
		for (int idx = 0; idx < hierarchy.size(); idx++) {
			// Test the predicate
			if (nodeIdPredicate.test(hierarchy.nodeId(idx))) {
				// Get all ancestors
				Hierarchy ancestors = hierarchy.ancestors(idx);

				// Test all the ancestors, if true add them to the list.
				if (testAncestors(nodeIdPredicate, ancestors)) {
					ids.add(hierarchy.nodeId(idx));
					depth.add(hierarchy.depth(idx));
				}
			}
		}

		return new ArrayBasedHierarchy(ids.stream().mapToInt(Integer::intValue).toArray(),
				depth.stream().mapToInt(Integer::intValue).toArray());
	}

	/**
	 * Test all the ancestors on the nodeIdPredicate.
	 *
	 * @param nodeIdPredicate The predicate
	 * @param ancestors       The Hierarchy of ancestors
	 */
	private static boolean testAncestors(java.util.function.IntPredicate nodeIdPredicate, Hierarchy ancestors) {
		boolean test = true;
		for (int i = 0; i < ancestors.size(); i++) {
			if (!nodeIdPredicate.test(ancestors.nodeId(i))) {
				test = false;
			}
		}
		return test;
	}
}
