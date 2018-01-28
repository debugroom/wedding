package org.debugroom.wedding.app.web.tag;

import java.util.List;

import org.springframework.web.servlet.tags.form.TagWriter;
import org.springframework.web.servlet.tags.RequestContextAwareTag;
import org.debugroom.wedding.app.web.adapter.docker.PortalServiceAdapterController;
import org.debugroom.wedding.app.model.Menu;
import org.debugroom.wedding.domain.entity.User;
import org.debugroom.wedding.domain.service.common.MenuSharedService;

public class MenuTag extends RequestContextAwareTag{

	private static final long serialVersionUID = -5809829162537455362L;

	private static final String MENU_REGION_ELEMENT = "div";
	private static final String MENU_REGION_ATTRIBUTE = "class";
	private static final String OUTER_ELEMENT = "ul";
	private static final String INNER_ELEMENT = "li";
	private static final String LINK_ELEMENT = "a";
	private static final String LINK_ATTRIBUTE = "href";
	private static final String PATH_SEPARATOR = "/";
	
	private String cssStyle;
	private String userId;
	private Integer authorityLevel;
	
	@Override
	protected int doStartTagInternal() throws Exception{

		PortalServiceAdapterController controller = (PortalServiceAdapterController)
				getRequestContext().getWebApplicationContext().getBean("portalServiceAdapterController");

		if(null == userId){
			userId = "00000000";
		}

		List<Menu> menuList = controller.getUsableMenu(userId);
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
			if(menu.isPathvariables()){
				stringBuilder.append(PATH_SEPARATOR).append(userId);
			}
			tagWriter.writeAttribute(LINK_ATTRIBUTE, stringBuilder.toString());
			tagWriter.appendValue(menu.getMenuName());
			tagWriter.endTag();
			tagWriter.endTag();
		}
		tagWriter.endTag();
		tagWriter.endTag();
		return SKIP_BODY;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

	public void setAuthorityLevel(String authorityLevel){
		this.authorityLevel = Integer.parseInt(authorityLevel);
	}
}
