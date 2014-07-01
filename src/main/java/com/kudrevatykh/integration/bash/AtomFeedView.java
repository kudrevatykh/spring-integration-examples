package com.kudrevatykh.integration.bash;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.feed.AbstractAtomFeedView;

import com.sun.syndication.feed.atom.Content;
import com.sun.syndication.feed.atom.Entry;
import com.sun.syndication.feed.atom.Feed;

@Component
public class AtomFeedView extends AbstractAtomFeedView {

	private static String FEED_URL = "http://bash.im/abyssbest";

	@Override
	protected List<Entry> buildFeedEntries(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rss = (List<Map<String, Object>>) model
				.get("rss");
		List<Entry> list = new ArrayList<Entry>(rss.size());
		for (Map<String, Object> m : rss) {
			Entry e = new Entry();
			e.setPublished((Date) m.get("date"));
			e.setTitle((String) m.get("id"));
			Content c = new Content();
			c.setType(Content.HTML);
			c.setValue((String) m.get("text"));
			e.setContents(Collections.singletonList(c));
			list.add(e);
		}
		return list;
	}

	@Override
	protected void buildFeedMetadata(Map<String, Object> model, Feed feed,
			HttpServletRequest request) {
		feed.setTitle("bash abyss");
		feed.setId(FEED_URL);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rss = (List<Map<String, Object>>) model
				.get("rss");
		if (!rss.isEmpty()) {
			feed.setUpdated((Date) rss.get(0).get("date"));
		}
	}

}
