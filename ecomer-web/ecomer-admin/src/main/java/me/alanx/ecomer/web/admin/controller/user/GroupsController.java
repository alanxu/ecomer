package me.alanx.ecomer.web.admin.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import me.alanx.ecomer.core.model.auth.Group;
import me.alanx.ecomer.core.model.auth.GroupType;
import me.alanx.ecomer.core.services.auth.GroupService;
import me.alanx.ecomer.core.services.auth.PermissionService;
import me.alanx.ecomer.core.services.reference.country.CountryService;
import me.alanx.ecomer.core.services.reference.language.LanguageService;
import me.alanx.ecomer.web.ajax.AjaxPageableResponse;
import me.alanx.ecomer.web.ajax.AjaxResponse;
import me.alanx.ecomer.web.dto.admin.web.Menu;
import me.alanx.ecomer.web.utils.LabelUtils;

@Controller
public class GroupsController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GroupsController.class);

	@Inject
	LanguageService languageService;

	@Inject
	protected GroupService groupService;
	
	@Inject
	PermissionService permissionService;

	@Inject
	CountryService countryService;

	@Inject
	LabelUtils messages;



	@PreAuthorize("hasRole('STORE_ADMIN')")
	@RequestMapping(value = "/admin/groups/editGroup.html", method = RequestMethod.GET)
	public String displayGroup(@RequestParam("id") Long groupId, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// display menu
		setMenu(model, request);

		Group group = groupService.getById(groupId);

		model.addAttribute("group", group);

		return "admin-user-group";
	}



	@PreAuthorize("hasRole('STORE_ADMIN')")
	@RequestMapping(value = "/admin/groups/groups.html", method = RequestMethod.GET)
	public String displayGroups(Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setMenu(model, request);
		List<Group> groups = groupService.listGroup(GroupType.ADMIN);
		model.addAttribute("groups", groups);

		return "admin-user-groups";
	}

	
	@PreAuthorize("hasRole('STORE_ADMIN')")
	@RequestMapping(value = "/admin/groups/paging.html", method = RequestMethod.POST)
	public @ResponseBody
	ResponseEntity<String> pageGroups(HttpServletRequest request,
			HttpServletResponse response, Locale locale) {

		AjaxResponse resp = new AjaxResponse();
		try {

				List<Group> groups = groupService.list();

				for(Group group : groups) {
					Map entry = new HashMap();
					entry.put("groupId", group.getId());
					entry.put("name", group.getGroupName());

					StringBuilder key = new StringBuilder().append("security.group.description.").append(group.getGroupName());
					try {
					
						String message =  messages.getMessage(key.toString(), locale);
						entry.put("description",message);
					
					} catch(Exception noLabelException) {
						LOGGER.error("No label found for key [" + key.toString() + "]");
					}
					
					
					

					resp.addDataEntry(entry);
				}

			resp.setStatus(AjaxPageableResponse.RESPONSE_STATUS_SUCCESS);
		
		} catch (Exception e) {
			LOGGER.error("Error while paging permissions", e);
			resp.setStatus(AjaxPageableResponse.RESPONSE_STATUS_FAIURE);
			resp.setErrorMessage(e);
		}
		
		String returnString = resp.toJSONString();
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
	}
	

	private void setMenu(Model model, HttpServletRequest request)
			throws Exception {

		// display menu
		Map<String, String> activeMenus = new HashMap<String, String>();
		activeMenus.put("profile", "profile");
		activeMenus.put("security", "security");

		@SuppressWarnings("unchecked")
		Map<String, Menu> menus = (Map<String, Menu>) request
				.getAttribute("MENUMAP");

		Menu currentMenu = (Menu) menus.get("profile");
		model.addAttribute("currentMenu", currentMenu);
		model.addAttribute("activeMenus", activeMenus);
		//

	}

}
