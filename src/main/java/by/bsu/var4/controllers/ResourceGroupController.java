package by.bsu.var4.controllers;

import by.bsu.var4.entity.Resource;
import by.bsu.var4.entity.ResourceGroup;
import by.bsu.var4.entity.ResourceGroupConnection;
import by.bsu.var4.entity.User;
import by.bsu.var4.exception.DAOException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;

import static by.bsu.var4.util.ConstantUtil.*;
import static by.bsu.var4.util.ConstantUtil.BLOCK_USER;
import static by.bsu.var4.util.ConstantUtil.LOGIN_PAGE;

/**
 * Created by Asus on 09.10.2016.
 */
@Controller
@RequestMapping("/resourceGroup")
public class ResourceGroupController extends BaseController{

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "/createGroupResource", method = RequestMethod.GET)
    public ModelAndView createGroupResource(HttpServletRequest req, Model model) throws DAOException {
        model.addAttribute("currentuser", getCurrentUser(req).getLogin());
        return new ModelAndView("groupResourceName", "resourceGroup", new ResourceGroup());
    }

    @RequestMapping(value = "/createGroupResource", method = RequestMethod.POST)
    public String createGroupResourceToDb(@ModelAttribute("resourceGroup") ResourceGroup resourceGroup, HttpServletRequest req, HttpServletResponse resp, Model model) throws DAOException, IOException {
        resourceGroupDAO.create(resourceGroup);
        return manageRequests(req, resp,model);
    }

    @RequestMapping(value = "/editGroupResource", method = RequestMethod.GET)
    public ModelAndView editGroupResource(@RequestParam("id") Integer id, HttpServletRequest req, Model model) throws DAOException {
        ResourceGroup resourceGroup = resourceGroupDAO.retrieve(id);
        model.addAttribute("currentuser", getCurrentUser(req).getLogin());
        return new ModelAndView("groupResourceName", "resourceGroup", resourceGroup);
    }

    @RequestMapping(value = "/editGroupResource", method = RequestMethod.POST)
    public String editGroupResourceToDb(@ModelAttribute("resourceGroup") ResourceGroup resourceGroup, HttpServletRequest req, HttpServletResponse resp, Model model) throws DAOException, IOException {
        resourceGroupDAO.update(resourceGroup);
        return manageRequests(req, resp,model);
    }

    @RequestMapping(value = "/deleteGroupResource", method = RequestMethod.GET)
    public String deleteroupResourceToDb(@RequestParam("id") Integer id, HttpServletRequest req, HttpServletResponse resp, Model model) throws DAOException, IOException {
        resourceGroupDAO.delete(id);
        return manageRequests(req, resp,model);
    }

    @RequestMapping(value = "/groupResource", method = RequestMethod.GET)
    public ModelAndView viewResourceGroups(HttpServletRequest req, Model model) throws DAOException {
        model.addAttribute("currentuser", getCurrentUser(req).getLogin());
        return new ModelAndView("groupResources", "resourceGroups", resourceGroupDAO.retrieveAll());
    }

    @RequestMapping(value = "/manageResources", method = RequestMethod.GET)
    public ModelAndView viewResources(HttpServletRequest req, Model model) throws DAOException {
        model.addAttribute("currentuser", getCurrentUser(req).getLogin());
        return new ModelAndView("resources", "resources", resourceDAO.retrieveAll());
    }

    @RequestMapping(value = "/viewResource", method = RequestMethod.GET)
    public ModelAndView viewResource(@RequestParam("id") Integer id, HttpServletRequest req, Model model) throws DAOException {
        Resource resource = resourceDAO.retrieve(id);
        model.addAttribute("currentuser", getCurrentUser(req).getLogin());
        return new ModelAndView("project", "project", resource);
    }

    @RequestMapping(value = "/addResource", method = RequestMethod.GET)
    public ModelAndView addResource(HttpServletRequest req, Model model) throws DAOException {
        model.addAttribute("currentuser", getCurrentUser(req).getLogin());
        return new ModelAndView("addResource", "resource", new Resource());
    }

    @RequestMapping(value = "/addResource", method = RequestMethod.POST)
    public String addResourceToDb(@ModelAttribute("resource") Resource resource,
                                   HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException, SQLException, DAOException {
        resourceDAO.create(resource);
        return manageRequests(req, resp, model);
    }

    @RequestMapping(value = "/editResource", method = RequestMethod.GET)
    public ModelAndView editResource(@RequestParam("id") Integer id, HttpServletRequest req, Model model) throws DAOException {
        Resource resource = resourceDAO.retrieve(id);
        model.addAttribute("currentuser", getCurrentUser(req).getLogin());
        return new ModelAndView("addResource", "resource", resource);
    }

    @RequestMapping(value = "/editResource", method = RequestMethod.POST)
    public String editResourceToDb(@ModelAttribute("resource") Resource resource, @RequestParam("pinCode") String pin, Errors errors, Locale locale,
                                   HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException, SQLException, DAOException {
        HttpSession session = req.getSession();
        String realPin = (String) session.getAttribute("pinCode");
        User userDb = userDAO.getUser((String)session.getAttribute("login"));
        if(userDb.getPinAttemptCount() > 2)
        {
            int addMinuteTime = 15 *60 * 1000;
            Timestamp targetTime = userDb.getPinLastAttempt();
            targetTime.setTime(targetTime.getTime() + addMinuteTime);
            if(targetTime.compareTo(new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis())) > 0)
            {
                errors.reject(BLOCK_USER, messageSource.getMessage(BLOCK_USER, null, locale));
                return "addResource";
            }
            else
            {
                userDb.setPinAttemptCount(0);
            }
        }
        if(realPin.equals(DigestUtils.md5Hex(pin))){
            resourceDAO.update(resource);
            userDb.setPinLastAttempt(new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
            userDAO.update(userDb);
        }
        else
        {
            userDb.setPinAttemptCount(userDb.getPinAttemptCount() + 1);
            userDb.setPinLastAttempt(new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
            userDAO.update(userDb);
            errors.reject(PIN_INCORRECT, messageSource.getMessage(PIN_INCORRECT, null, locale));
            return "addResource";
        }
        return manageRequests(req, resp, model);
    }

    @RequestMapping(value = "/deleteResource", method = RequestMethod.GET)
    public String deleteResource(@RequestParam("id") Integer id,
                               HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException, SQLException, DAOException {
        resourceDAO.delete(id);
        return manageRequests(req, resp, model);
    }

    @RequestMapping(value = "/showResources", method = RequestMethod.GET)
    public String getGroupResources(@RequestParam("id") Integer param,HttpServletRequest req,Model model) throws DAOException {
        model.addAttribute("currentuser", getCurrentUser(req).getLogin());
        model.addAttribute("resources", resourceDAO.getResources(param));
        model.addAttribute("groupResourceId", param);
        return "groupResourceManagement";
    }

    @RequestMapping(value = "/addResourceToGroup", method = RequestMethod.GET)
    public ModelAndView addUser(@RequestParam("group_id") Integer param, HttpServletRequest req, Model model) throws DAOException {
        ResourceGroupConnection resourceGroupConnection = new ResourceGroupConnection();
        resourceGroupConnection.setResourceGroupId(param);
        model.addAttribute("resources", resourceDAO.retrieveAll());
        model.addAttribute("currentuser", getCurrentUser(req).getLogin());
        return new ModelAndView("addResourceGroupConnection", "resourceGroupConnection", resourceGroupConnection);
    }

    @RequestMapping(value = "/addResourceToGroup", method = RequestMethod.POST)
    public String addUserToDb(@ModelAttribute("resourceGroupConnection") ResourceGroupConnection resourceGroupConnection,
                              HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException, SQLException, DAOException {

        resourceGroupDAO.connectResourceToGroup(resourceGroupConnection.getResourceGroupId(), resourceGroupConnection.getResourceId());
        return getGroupResources(resourceGroupConnection.getResourceGroupId(), req, model);
    }

    @RequestMapping(value = "/deleteResourceFromGroup", method = RequestMethod.GET)
    public String deleteUser(@RequestParam("resource_id") Integer userId, @RequestParam("group_id") Integer groupId,
                             HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException, SQLException, DAOException {

        resourceGroupDAO.deleteResourceFromGroup(groupId, userId);
        return getGroupResources(groupId,req, model);
    }
}
