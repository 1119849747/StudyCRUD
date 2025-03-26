package com.longsong.myspringboot.controller;

import com.longsong.myspringboot.entity.User;
import com.longsong.myspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    /**
     * author:longsong
     * @date 2025/03/24
     * @description 实验六任务
     */
    @Autowired
    private  UserService userService;

    /**
     * 用户首页，获取所有用户
     * @param model
     * @return
     */
    @GetMapping
    public String ListUsers(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("users",users);
        return "index";
    }

    /**
     * 打开新增用户页面
     * @param model
     * @return
     */
    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("user",new User());
        return "add";
    }
    private static final String UPLOAD_DIR ="static/uploads/";

    /**
     * 新增用户方法
     * @param name
     * @param age
     * @param sex
     * @param photo
     * @return
     * @throws IOException
     */
    @PostMapping("/add")
    public String addUser(@RequestParam("name") String name,
                          @RequestParam("age") Integer age,
                          @RequestParam("sex") String sex,
                          @RequestParam("photo") MultipartFile photo) throws IOException {
        User user =new User();
        user.setName(name);
        user.setAge(age);
        user.setSex(sex);
        if (!photo.isEmpty()){
            String fileName = photo.getOriginalFilename();
            Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath,photo.getBytes());
        user.setPhoto(fileName);
        }
        userService.addUser(user);
        return "redirect:/users";
    }

    /**
     * 通过id打开编辑用户页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id,Model model){
        model.addAttribute("user",userService.getUserById(id));
        return "edit";
    }

    /**
     * 更新用户信息方法
     * @param id
     * @param name
     * @param age
     * @param sex
     * @param photo
     * @return
     * @throws IOException
     */
    @PostMapping("/edit/{id}")
    public String UpDateUser(  @PathVariable Integer id,
                                @RequestParam("name") String name,
                               @RequestParam("age") Integer age,
                               @RequestParam("sex") String sex,
                               @RequestParam("photo") MultipartFile photo) throws IOException{
        User user =userService.getUserById(id);
        user.setName(name);
        user.setAge(age);
        user.setSex(sex);
        if (!photo.isEmpty()){
            String fileName = photo.getOriginalFilename();
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath,photo.getBytes());
            user.setPhoto(fileName);
        }
        userService.addUser(user);
        return "redirect:/users";
    }

    /**
     * 查看用户详情方法
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/find/{id}")
    public String showDetail(@PathVariable Integer id,Model model){
        model.addAttribute("user",userService.getUserById(id));
        return "detail";
    }

    /**
     * 删除用户方法
     * @param id
     * @return
     */
    @GetMapping("/del/{id}")
    public String deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
        return "redirect:/users";
    }

}
