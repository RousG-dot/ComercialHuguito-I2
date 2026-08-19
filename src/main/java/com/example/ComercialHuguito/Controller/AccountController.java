package com.example.ComercialHuguito.Controller;

import com.example.ComercialHuguito.Models.AppUser;
import com.example.ComercialHuguito.Models.RegisterDto;
import com.example.ComercialHuguito.Repository.AppUserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class AccountController {
    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping("/register")
    public String register (Model model){
        RegisterDto registerDto = new RegisterDto();
        model.addAttribute(registerDto);
        model.addAttribute("success",false);
        return "Registro";
    }

    @PostMapping("/register")
    public String register( Model model, @Valid @ModelAttribute RegisterDto registerDto,
                            BindingResult result){
        if(!registerDto.getPassword().equals(registerDto.getConfirmPassword())){
            result.addError(new FieldError("registerDto","confirmPassword",
                    "Password and Confirm Password do not match"));
        }
        AppUser appUser = appUserRepository.findByEmail(registerDto.getEmail());
        if(appUser!=null){
            result.addError( new FieldError("registerDto","email",
                    "El email address is already used"));
        }
        if(result.hasErrors()){       return "Registro";     }
        try{
            var bCryptEncoder = new BCryptPasswordEncoder();
            AppUser newUser = new AppUser();
            newUser.setFirstName(registerDto.getFirstName());
            newUser.setLastName(registerDto.getLastName());
            newUser.setEmail(registerDto.getEmail());
            newUser.setPhone(registerDto.getPhone());
            newUser.setAddress(registerDto.getAddress());
            newUser.setRole("administrador");
            newUser.setCreatedAt(new Date());
            newUser.setPassword(bCryptEncoder.encode(registerDto.getPassword()));
            appUserRepository.save(newUser);
            model.addAttribute("registerDto",new RegisterDto());
            model.addAttribute("success",true);
        }catch(Exception ex){
            result.addError(new FieldError("registerDto","firstName",
                    ex.getMessage()));      }
        return "Registro";
    }
}
