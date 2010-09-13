// Copyright 2010 Google Inc. All Rights Reserved.

package com.crawljax.core.state;

import com.google.common.collect.ForwardingList;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * The Path a Crawler has taken, or is about to backtrack on.
 *
 * @version $Id$
 * @author slenselink@google.com (Stefan Lenselink)
 */
public class CrawlPath extends ForwardingList<Eventable> {

	private final List<Eventable> eventablePath;

	/**
	 * Start a new empty CrawlPath.
	 */
	public CrawlPath() {
		this(Lists.<Eventable> newLinkedList());
	}

	/**
	 * Create a new CrawlPath based on a delegate.
	 *
	 * @param delegate
	 *            the List implementation where this CrawlPath is based on.
	 */
	public CrawlPath(List<Eventable> delegate) {
		this.eventablePath = delegate;
	}
	
    @Override
	protected List<Eventable> delegate() {
		return eventablePath;
	}

	/**
	 * Get the last Eventalbe in the path.
	 *
	 * @return the last Eventable in the path
	 */
    public Eventable last() {
		if (eventablePath.size() == 0) {
			return null;
		}
		return eventablePath.get(eventablePath.size() - 1);
	}

	/**
	 * Create an immutableCopy of the current CrawlPath, used for backtracking for giving them to
	 * plugins.
	 *
	 * @param removeLast
	 *            should the last element be removed?
	 * @return the CrawlPath based on an immutable list.
	 */
    public CrawlPath immutableCopy(boolean removeLast) {
		if (size() == 0) {
			return new CrawlPath();
		}
		
		// Build copy
		List<Eventable> path = Lists.newArrayList(this);
		
		// This is safe because checked above
		if (removeLast) {
			path.remove(path.size() - 1);
		}
		return new CrawlPath(ImmutableList.copyOf(path));
	}
}
