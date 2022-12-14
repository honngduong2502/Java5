package java5.asm.controllers;


import java5.asm.dto.UserDTO;
import java5.asm.entity.Role;
import java5.asm.entity.User;
import java5.asm.global.GlobalData;
import java5.asm.repo.RoleRepository;
import java5.asm.repo.UserRepository;
import java5.asm.utils.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping("/login")
    public String login(){
        GlobalData.cart.clear();
        return "login";
    }//page login

    @GetMapping("/forgotpassword")
    public String forgotPass(Model model){
        model.addAttribute("userDTO", new UserDTO());
        return "forgotpassword";
    }

    @GetMapping("/register")
    public String registerGet(Model model){
        return "register";
    } //page register




    @PostMapping("/register")
    public String registerPost(@ModelAttribute User userModel, HttpServletRequest request) throws ServletException {
        //chuyen password tu form dki thanh dang ma hoa
        String password = userModel.getPassword();
        userModel.setPassword(EncryptUtil.mhPass(password));
        //set mac dinh role user,admin
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findById(1).get());
        roles.add(roleRepository.findById(2).get());
        userModel.setRoles(roles);
        userRepository.save(userModel);
        //login & chuyen den page home
        return "redirect:/login";
    }//after register success
    @PostMapping("/login")
    public String login(
            @RequestParam(defaultValue = "", name = "email") String email,
            @RequestParam(defaultValue = "", name = "password") String password,

            Model model, HttpSession session
    ) {

        if (email.equals("")) {
            session.setAttribute("error", "T??i kho???n kh??ng ???????c b??? tr???ng");
            return "redirect:/user/home";


        } else {
            if (password.equals("")) {
                session.setAttribute("error", "M???t Kh???u kh??ng ???????c b??? tr???ng");
                return "redirect:/login";
            } else {
                Optional<User> us = userRepository.findUserByEmail(email);
                if (us == null) {
                    session.setAttribute("error", "T??i kho???n ho???c m???t kh???u kh??ng ch??nh x??c");
                    return "redirect:/login";
                } else {
                    boolean checkpass = EncryptUtil.checkpass(password, us.get().getPassword());
                    if (checkpass == false) {
                        session.setAttribute("error", "T??i kho???n ho???c m???t kh???u kh??ng ch??nh x??c");
                        return "redirect:/login";
                    } else {
                        int role =0;
                        for(int i = 0; i< us.get().getRoles().size();i++){
                            if(us.get().getRoles().get(i).getId()==1){
                                role = 1;
                                session.setAttribute("role", role);
                            }
                        }
                        session.setAttribute("userdn", us);
                        session.setAttribute("success", "????ng Nh???p Th??nh C??ng");
                        return "redirect:/shop";
                    }
                }
            }
        }


    }
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        if(session.getAttribute("userdn")==null){
            session.setAttribute("error", "B???n C???n ????ng Nh???p M???i C?? Th??? ????ng Xu???t");
            return "redirect:/login";
        }else {

            session.setAttribute("userdn", null);
            session.setAttribute("role", 0);
            session.setAttribute("success", "????ng Xu???t Th??nh C??ng");
            return "redirect:/login";
        }
    }
}
