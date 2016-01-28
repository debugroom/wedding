package org.debugroom.wedding.app.web.common.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.data.domain.Page;
import org.springframework.web.servlet.tags.form.TagWriter;
 
public class PagenationTag extends SimpleTagSupport{

	private static final String PAGENATION_REGION_ELEMENT = "div";
	private static final String PAGENATION_REGION_ATTRIBUTE = "class";
	
	Page page;
	
	public void doTag() throws JspException{
		TagWriter tagWriter = new TagWriter((PageContext)getJspContext());
		tagWriter.startTag(PAGENATION_REGION_ELEMENT);
		tagWriter.writeAttribute(PAGENATION_REGION_ATTRIBUTE, "page");
		tagWriter.endTag();
	}
	
	public void setPage(Page page){
		this.page = page;
		
	}
}
