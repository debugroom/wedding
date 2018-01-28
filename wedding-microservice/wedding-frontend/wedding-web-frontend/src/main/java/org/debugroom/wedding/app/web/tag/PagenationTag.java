package org.debugroom.wedding.app.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.data.domain.Page;
import org.springframework.web.servlet.tags.form.TagWriter;

public class PagenationTag extends SimpleTagSupport{

	private static final String PAGENATION_REGION_ELEMENT = "div";
	private static final String PAGENATION_REGION_ATTRIBUTE = "class";
	private static final String UL_ELEMENT = "ul";
	private static final String LI_ELEMENT = "li";
	private static final String A_ELEMENT = "a";
	private static final String HREF_ATTRIBUTE = "href";
	private static final String FIRST_PAGE_ANCHOR = "&lt;&lt";
	private static final String PREVIOUS_PAGE_ANCHOR = "&lt";
	private static final String NEXT_PAGE_ANCHOR = "&gt";
	private static final String LAST_PAGE_ANCHOR = "&gt;&gt";
	private static final int DEFAULT_PAGE_SIZE = 5;

	Page page;
	String requestPath; 
	int pageSize;
	
	public void doTag() throws JspException{
		
		PageContext pageContext = (PageContext)getJspContext();

		if(pageSize == 0){
			pageSize = DEFAULT_PAGE_SIZE;
		}

		String contextPath = new StringBuilder().append(
				pageContext.getRequest().getServletContext().getContextPath())
				.toString();
		String pageParamQuery = new StringBuilder().append("?page=").toString();
		String sizeParamQuery = new StringBuilder().append("&size=").toString();

		TagWriter tagWriter = new TagWriter((PageContext)getJspContext());
		//start div tag.
		tagWriter.startTag(PAGENATION_REGION_ELEMENT);
		//add class attribute to div tag.
		tagWriter.writeAttribute(PAGENATION_REGION_ATTRIBUTE, "page");
		
		//start ul tag.
		tagWriter.startTag(UL_ELEMENT);

		//first page anchor
		//start li tag.
		tagWriter.startTag(LI_ELEMENT);
		//start a tag.
		tagWriter.startTag(A_ELEMENT);
		//add href attribute.
		if(page.getNumber() != 0){
			tagWriter.writeAttribute(HREF_ATTRIBUTE, new StringBuilder()
														.append(contextPath)
														.append(requestPath)
														.append(pageParamQuery)
														.append(0)
														.append(sizeParamQuery)
														.append(pageSize)
														.toString());
		}else{
			tagWriter.writeAttribute(HREF_ATTRIBUTE, "#");
		}
		//add anchor
		tagWriter.appendValue(FIRST_PAGE_ANCHOR);
		//end a tag.
		tagWriter.endTag();
		//end li tag.
		tagWriter.endTag();

		//previous page anchor
		//start li tag.
		tagWriter.startTag(LI_ELEMENT);
		//start a tag.
		tagWriter.startTag(A_ELEMENT);
		//add href attribute.
		if(page.getNumber() != 0){
			tagWriter.writeAttribute(HREF_ATTRIBUTE, new StringBuilder()
														.append(contextPath)
														.append(requestPath)
														.append(pageParamQuery)
														.append(page.getNumber()-1)
														.append(sizeParamQuery)
														.append(pageSize)
														.toString());
		}else{
			tagWriter.writeAttribute(HREF_ATTRIBUTE, "#");
		}

		//add anchor
		tagWriter.appendValue(PREVIOUS_PAGE_ANCHOR);
		//end a tag.
		tagWriter.endTag();
		//end li tag.
		tagWriter.endTag();
		
		// Add pagenation
		for(int i = 0 ; i < page.getTotalPages() ; i++){
			//start li tag.
			tagWriter.startTag(LI_ELEMENT);
			//start a tag.
			tagWriter.startTag(A_ELEMENT);
			//add href attribute.
			tagWriter.writeAttribute(HREF_ATTRIBUTE, new StringBuilder()
															.append(contextPath)
															.append(requestPath)
															.append(pageParamQuery)
															.append(i)
															.append(sizeParamQuery)
															.append(pageSize)
															.toString());
			//add anchor
			tagWriter.appendValue(String.valueOf(i+1));
			//end a tag.
			tagWriter.endTag();
			//end li tag.
			tagWriter.endTag();
		}
		
		//next page anchor
		//start li tag.
		tagWriter.startTag(LI_ELEMENT);
		//start a tag.
		tagWriter.startTag(A_ELEMENT);
		//add href attribute.
		if(page.getNumber() != page.getTotalPages() - 1){
			tagWriter.writeAttribute(HREF_ATTRIBUTE, new StringBuilder()
														.append(contextPath)
														.append(requestPath)
														.append(pageParamQuery)
														.append(page.getNumber()+1)
														.append(sizeParamQuery)
														.append(pageSize)
														.toString());
		}else{
			tagWriter.writeAttribute(HREF_ATTRIBUTE, "#");
		}
		//add anchor
		tagWriter.appendValue(NEXT_PAGE_ANCHOR);
		//end a tag.
		tagWriter.endTag();
		//end li tag.
		tagWriter.endTag();

		//last page anchor
		//start li tag.
		tagWriter.startTag(LI_ELEMENT);
		//start a tag.
		tagWriter.startTag(A_ELEMENT);
		//add href attribute.
		if(page.getNumber() != page.getTotalPages() - 1){
			tagWriter.writeAttribute(HREF_ATTRIBUTE, new StringBuilder()
														.append(contextPath)
														.append(requestPath)
														.append(pageParamQuery)
														.append(page.getTotalPages()-1)
														.append(sizeParamQuery)
														.append(pageSize)
														.toString());
		}else{
			tagWriter.writeAttribute(HREF_ATTRIBUTE, "#");
		}
		//add anchor
		tagWriter.appendValue(LAST_PAGE_ANCHOR);
		//end a tag.
		tagWriter.endTag();
		//end li tag.
		tagWriter.endTag();

		//end ul tag.
		tagWriter.endTag();
		//end div tag.
		tagWriter.endTag();

		page.getNumber();
		page.getNumberOfElements();
		page.getSize();
		page.getTotalElements();
		page.getTotalPages();
	}
	
	public void setPage(Page page){
		this.page = page;
	}
	
	public void setRequestPath(String requestPath){
		this.requestPath = requestPath;
	}
	
	public void setPageSize(int pageSize){
		this.pageSize = pageSize;
	}

}
