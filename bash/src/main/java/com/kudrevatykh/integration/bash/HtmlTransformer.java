package com.kudrevatykh.integration.bash;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.integration.annotation.Splitter;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

public class HtmlTransformer {

	public String quoteSelector;

	public String bodySelector;

	public String idSelector;

	public String dateSelector;

	@Splitter()
	public List<Message<String>> transform(String message) {
		Document doc = Jsoup.parse((String) message);
		List<Message<String>> result = new ArrayList<Message<String>>();
		for (Element e : doc.select(quoteSelector)) {
			result.add(MessageBuilder
					.withPayload(e.select(bodySelector).html())
					.setHeader("quoteId", e.select(idSelector).text())
					.setHeader("quoteDate", e.select(dateSelector).text())
					.build());
		}
		return result;
	}

	public String getQuoteSelector() {
		return quoteSelector;
	}

	public void setQuoteSelector(String quoteSelector) {
		this.quoteSelector = quoteSelector;
	}

	public String getBodySelector() {
		return bodySelector;
	}

	public void setBodySelector(String bodySelector) {
		this.bodySelector = bodySelector;
	}

	public String getIdSelector() {
		return idSelector;
	}

	public void setIdSelector(String idSelector) {
		this.idSelector = idSelector;
	}

	public String getDateSelector() {
		return dateSelector;
	}

	public void setDateSelector(String dateSelector) {
		this.dateSelector = dateSelector;
	}

}
