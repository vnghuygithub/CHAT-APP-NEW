package com.example.projectappchat.controller;

import com.example.projectappchat.dto.BoxChatFriendDTO;
import com.example.projectappchat.entity.*;
import com.example.projectappchat.service.friend.FriendService;
import com.example.projectappchat.service.groupChat.GroupChatService;
import com.example.projectappchat.service.message.MessageService;
import com.example.projectappchat.service.role.RoleService;
import com.example.projectappchat.service.user.UserService;
import com.example.projectappchat.service.userRole.UserRoleService;
import com.example.projectappchat.utils.AppUtils;
import com.example.projectappchat.utils.EncryptedPasswordUtils;
import com.example.projectappchat.utils.WebUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class MainController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private FriendService friendService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private GroupChatService groupChatService;

    ModelMapper modelMapper = new ModelMapper();

    @GetMapping({"/login"})
    public ModelAndView loginPage() {
        ModelAndView modelAndView = new ModelAndView("signIn-signUp/loginPage");
        return modelAndView;
    }

    @GetMapping("/logoutSuccessful")
    public ModelAndView logoutSuccessful() {
        ModelAndView modelAndView = new ModelAndView("signIn-signUp/loginPage");
        return modelAndView;
    }

    @PostMapping("/sign-up")
    public ModelAndView signUp(@RequestParam(value = "user-account", required = false) String userAccount,
                               @RequestParam(value = "password", required = false) String password,
                               RedirectAttributes redirectAttributes) throws IOException {
        ModelAndView modelAndView = new ModelAndView("redirect:" + "/login");

        com.example.projectappchat.entity.User userCheck = userService.findUserByAccount(userAccount);
        if (userCheck != null) {
            redirectAttributes.addFlashAttribute("message", "Đăng ký không thành công vì " +
                    "đã có người sử dụng tài khoản này, xin mời đăng ký lại!");
        } else {
            com.example.projectappchat.entity.User user = new com.example.projectappchat.entity.User();
            user.setUserAccount(userAccount);
            String encryptedPassword = EncryptedPasswordUtils.encryptedPassword(password);
            user.setUserPassword(encryptedPassword);
            user.setEnabled(true);
            byte[] user_logo = Files.readAllBytes(Paths.get("C:\\Users\\HUY\\Desktop\\Resume\\style.jpg"));
            user.setUserLogo(user_logo);
            userService.save(user);
            Role role = roleService.findById(2L);
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(role);
            userRoleService.save(userRole);
            redirectAttributes.addFlashAttribute("message", "Đăng ký thành công, xin mời đăng nhập!");
        }
        return modelAndView;
    }

/*
    @PostMapping("/checkDuplicate/{userAccount}")
    public ModelAndView checkDuplicate(@PathVariable("userAccount") String userAccount,
                                       RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView("redirect:" + "/login");
        com.example.projectappchat.entity.User user = userService.findUserByAccount(userAccount);
        if (user != null) {
            redirectAttributes.addFlashAttribute("message", "Đăng ký thành công, xin mời đăng nhập");
        }


        return modelAndView;
    }
*/

    @GetMapping("/admin")
    //Principal có thể hiểu là một người, hoặc một thiết bị,
    // hoặc một hệ thống nào đó có thể thực hiện một hành động trong ứng dụng của bạn.
    public ModelAndView adminPage(Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        String userInfo = WebUtils.toString(user);
        ModelAndView modelAndView = new ModelAndView("admin/adminPage");
        modelAndView.addObject("userInfo", userInfo);
        return modelAndView;
    }

    @GetMapping("/")
    public ModelAndView userInfo(Principal principal) {
        //Sau khi user login thanh cong se co principal
        if (principal != null) {

            /*Principal spring trả cho server khi user đăng nhập thành công*/
            User userPrincipal = (User) ((Authentication) principal).getPrincipal();

            /*Tìm thông tin user từ principal*/
            com.example.projectappchat.entity.User userInfo = userService.findUserByAccount(userPrincipal.getUsername());

            ModelAndView modelAndView = new ModelAndView("user/userProfile");

            modelAndView.addObject("userInfo", userInfo);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("signIn-signUp/loginPage");
            return modelAndView;
        }
    }


    @GetMapping("/image/display/{userId}")
    @ResponseBody
    void showImageUser(@PathVariable("userId") String userId, HttpServletResponse response)
            throws ServletException, IOException {
        com.example.projectappchat.entity.User userInfo = userService.findUserById(Long.parseLong(userId));
        response.getOutputStream().write(userInfo.getUserLogo());
        response.getOutputStream().close();
    }



    @GetMapping("/403")
    public ModelAndView accessDenied(Principal principal) {

        ModelAndView modelAndView = new ModelAndView("403Page");

        if (principal != null) {

            User loginUser = (User) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginUser);

            modelAndView.addObject("userInfo", userInfo);

            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";

            modelAndView.addObject("message", message);

        }

        return modelAndView;
    }

}