package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Citation implements Appendable {

	public static final String NODE_NAME_CITATION = "r:Citation";

	private Title title;

	public Citation(Title title) {
		setTitle(title);
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		Element citation = doc.createElementNS(namespace, NODE_NAME_CITATION);
		if (getTitle() != null) {
			getTitle().appendToElement(citation, doc, namespace);
		}
		element.appendChild(citation);
	}
}