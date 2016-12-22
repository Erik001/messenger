package com.tree.practices.restws.messenger.model;

import javax.xml.bind.annotation.XmlRootElement;


public class Link {

	public String link;
	public String rel;


	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

}
