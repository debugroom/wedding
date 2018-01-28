package org.debugroom.wedding.app.web.common.tag;

import java.util.List;

import org.springframework.web.servlet.tags.form.TagWriter;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import org.debugroom.wedding.domain.model.entity.User;
import org.debugroom.wedding.domain.common.service.MenuService;
import org.debugroom.wedding.domain.model.entity.Menu;

public class MenuTag extends RequestContextAwareTag {

	private static final long serialVersionUID = 1L;

	private static final String MENU_REGION_ELEMENT = "div";
	private static final String MENU_REGION_ATTRIBUTE = "class";
	private static final String OUTER_ELEMENT = "ul";
	private static final String INNER_ELEMENT = "li";
	private static final String LINK_ELEMENT = "a";
	private static final String LINK_ATTRIBUTE = "href";
	
	private String cssStyle;
	
	@Override
	protected int doStartTagInternal() throws Exception {
		MenuService menuService = (MenuService) getRequestContext().getWebApplicationContext().getBean("menuService");
		User user = User.builder().userId("00000000").authorityLevel(9).build();
		List<Menu> menuList = menuService.getUsableMenu(user);
		TagWriter tagWriter = new TagWriter(this.pageContext);
		tagWriter.startTag(MENU_REGION_ELEMENT);
		tagWriter.writeAttribute(MENU_REGION_ATTRIBUTE, cssStyle);
		tagWriter.startTag(OUTER_ELEMENT);

		StringBuilder stringBuilder = null;
		for(Menu menu : menuList){
			tagWriter.startTag(INNER_ELEMENT);
			tagWriter.startTag(LINK_ELEMENT);
			stringBuilder = new StringBuilder();
			stringBuilder.append(getRequestContext().getContextPath()).append(menu.getUrl());
			tagWriter.writeAttribute(LINK_ATTRIBUTE, stringBuilder.toString());
			tagWriter.appendValue(menu.getMenuName());
			tagWriter.endTag();
			tagWriter.endTag();
		}
		tagWriter.endTag();
		tagWriter.endTag();
		return SKIP_BODY;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}
	

}
