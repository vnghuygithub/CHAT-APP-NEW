package com.example.projectappchat.controller;

import com.example.projectappchat.dto.BoxChatFriendDTO;
import com.example.projectappchat.entity.*;
import com.example.projectappchat.service.friend.FriendService;
import com.example.projectappchat.service.groupChat.GroupChatService;
import com.example.projectappchat.service.groupChatDetail.GroupChatDetailService;
import com.example.projectappchat.service.groupChatMessage.GroupChatMessageService;
import com.example.projectappchat.service.message.MessageService;
import com.example.projectappchat.service.role.RoleService;
import com.example.projectappchat.service.user.UserService;
import com.example.projectappchat.service.userRole.UserRoleService;
import com.example.projectappchat.utils.AppUtils;
import org.apache.tomcat.util.http.fileupload.UploadContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GroupChatController {

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
    private GroupChatDetailService groupChatDetailService;

    @Autowired
    private GroupChatService groupChatService;

    @Autowired
    private GroupChatMessageService groupChatMessageService;

    ModelMapper modelMapper = new ModelMapper();

    @PostMapping("/groupChat/create")
    public ModelAndView createGroupChat(Principal principal,
                                        @RequestParam(value = "group-name", required = false) String groupName) throws IOException {
        ModelAndView modelAndView = new ModelAndView("redirect:/box-chat");

        /*User spring trả cho server khi đăng nhập thành công*/
        User userPrincipal = (User) ((Authentication) principal).getPrincipal();

        /*Tìm thông tin user từ principal*/
        com.example.projectappchat.entity.User userInfo = userService.findUserByAccount(userPrincipal.getUsername());

        GroupChat groupChat = new GroupChat();
        groupChat.setGroupChatName(groupName);
        groupChat.setGroupChatQuantityMember(1L);
        byte[] array = Files.readAllBytes(Paths.get("C:\\Windows-D\\PICTURE\\community-group.jpg"));
        groupChat.setGroupChatLogo(array);
        groupChatService.save(groupChat);


        GroupChatDetail groupChatDetail = new GroupChatDetail();
        groupChatDetail.setGroupChatId(groupChat);
        groupChatDetail.setUserId(userInfo);
        groupChatDetailService.save(groupChatDetail);


        return modelAndView;
    }

    @GetMapping("/group-chat/{groupChatId}")
    public ModelAndView groupChatView(Principal principal, @PathVariable("groupChatId") Long groupChatId) {
        ModelAndView modelAndView = new ModelAndView("user/groupChat");

        /*User spring trả cho server khi đăng nhập thành công*/
        User userPrincipal = (User) ((Authentication) principal).getPrincipal();

        /*Tìm thông tin user từ principal*/
        com.example.projectappchat.entity.User userInfo = userService.findUserByAccount(userPrincipal.getUsername());

        /*Tìm danh sách bạn bè của user*/
        List<Friend> friendList = friendService
                .findFriendByUserIdAndFriendStatus(userInfo.getUserId(), AppUtils.AcceptFriend);

        List<BoxChatFriendDTO> boxChatFriendDTOList = friendList
                .stream()
                .map(friend -> modelMapper.map(friend, BoxChatFriendDTO.class))
                .collect(Collectors.toList());

        /*Duyệt danh sách bạn bè của user để tìm message của tất cả các bạn bè nhắn tin với user*/
        for (BoxChatFriendDTO friend : boxChatFriendDTOList) {
            List<Message> messageListOfAllFriend = messageService.
                    findMessageByMessageSendId1AndMessageReceiverId1OrByMessageSendId2AndMessageReceiverId2
                            (userInfo.getUserId(), friend.getFriendReceiverId().getUserId());

            if (!messageListOfAllFriend.isEmpty()) {
                Message lastMessage = messageListOfAllFriend.get(messageListOfAllFriend.size() - 1);
                friend.setLastMessage(lastMessage.getMessageBody());
            }
        }





        /*=============================================================================*/
        /*Tim danh sach cac group chat cua nguoi dung*/

        List<GroupChatDetail> groupChatDetailList = groupChatDetailService.findGroupChatDetailByUserId(userInfo.getUserId());

        /*=============================================================================*/
        /*Tim thong tin cua group chat*/
        GroupChat groupChat = groupChatService.findById(groupChatId);

        /*=============================================================================*/
        /*Tim friend chua vao group chat*/
        List<Friend> friendListNotInGroupChat = friendService.findFriendNotInGroupByUserIdAndGroupChatId(userInfo.getUserId(), groupChatId);


        /*=============================================================================*/
        /*Tim user dang trong group chat*/
        List<GroupChatDetail> groupChatDetailListInGroupChat =
                groupChatDetailService.findGroupChatDetailInGroupChatByGroupChatId(groupChatId);


        /*=============================================================================*/
        /*Tim message trong group chat*/
        List<GroupChatMessage> groupChatMessages = groupChatMessageService.findGroupChatMessageByGroupChatId(groupChatId);

        modelAndView.addObject("groupChatMessages",groupChatMessages);
        modelAndView.addObject("groupChatDetailList", groupChatDetailList);
        modelAndView.addObject("friendList", boxChatFriendDTOList);
        modelAndView.addObject("groupChatDetailListInGroupChat", groupChatDetailListInGroupChat);
        modelAndView.addObject("friendListNotInGroupChat", friendListNotInGroupChat);
        modelAndView.addObject("groupChatInfo", groupChat);
        modelAndView.addObject("userInfo", userInfo);


        return modelAndView;
    }

    @PostMapping("/addFriend/{friendId}/toGroupChat/{groupChatId}")
    public ResponseEntity<?> addFriendToGroupChat(Principal principal,
                                                  @PathVariable("friendId") Long friendId,
                                                  @PathVariable("groupChatId") Long groupChatId) {
        GroupChatDetail groupChatDetail = new GroupChatDetail();

        groupChatDetail.setUserId(userService.findUserById(friendId));
        groupChatDetail.setGroupChatId(groupChatService.findById(groupChatId));

        GroupChat groupChat = groupChatService.findById(groupChatId);
        groupChat.setGroupChatQuantityMember(groupChat.getGroupChatQuantityMember() + 1);

        groupChatService.save(groupChat);
        groupChatDetailService.save(groupChatDetail);
        return new ResponseEntity<>(null,
                HttpStatus.valueOf(201));
    }

    @PostMapping("/editLogoGroupChat/{groupChatId}")
    public ResponseEntity<?> editLogoGroupChat(Principal principal,
                                               @PathVariable("groupChatId") Long groupChatId,
                                               @RequestParam("file") MultipartFile multipartFile) {

        GroupChat groupChat = groupChatService.findById(groupChatId);


        byte[] imageData = new byte[0];
        try {
            imageData = multipartFile.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        groupChat.setGroupChatLogo(imageData);
        groupChatService.save(groupChat);


        return new ResponseEntity<>(null,
                HttpStatus.valueOf(201));
    }

    @GetMapping("/image/group-chat/{groupChatId}")
    @ResponseBody
    void showImageGroup(@PathVariable("groupChatId") String groupChatId, HttpServletResponse response)
            throws ServletException, IOException {
        GroupChat groupChat = groupChatService.findById(Long.parseLong(groupChatId));
        response.getOutputStream().write(groupChat.getGroupChatLogo());
        response.getOutputStream().close();

    }

}
