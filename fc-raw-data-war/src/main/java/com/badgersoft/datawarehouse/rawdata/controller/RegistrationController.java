package com.badgersoft.datawarehouse.rawdata.controller;

import com.badgersoft.datawarehouse.common.utils.UTCClock;
import com.badgersoft.datawarehouse.rawdata.dao.UserDao;
import com.badgersoft.datawarehouse.rawdata.domain.User;
import com.badgersoft.datawarehouse.rawdata.messaging.Mail;
import com.badgersoft.datawarehouse.rawdata.security.ReasonablePasswordGenerator;
import com.badgersoft.datawarehouse.rawdata.service.EmailService;
import com.badgersoft.datawarehouse.rawdata.service.SatelliteListService;
import com.badgersoft.datawarehouse.rawdata.validation.RegisterUserRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RegistrationController {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    UserDao userDao;

    @Autowired
    ReasonablePasswordGenerator passwordGenerator;

    @Autowired
    EmailService mailService;

    @Autowired
    UTCClock clock;

    private final SatelliteListService satelliteListService;

    @Autowired
    public RegistrationController(SatelliteListService satelliteListService) {
        this.satelliteListService = satelliteListService;
    }

    @GetMapping("registration")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView("content/registration");
        return modelAndView;
    }

    @PostMapping("registration")
    public ModelAndView doRegistration(@ModelAttribute("registerUserRequest") @Valid RegisterUserRequest registerUserRequest, BindingResult result) throws IOException, MessagingException {

        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("content/registration");
            final List<FieldError> fieldErrors = result.getFieldErrors();
            List<String> fieldErrorMesages = getErrorMesages(fieldErrors);
            modelAndView.addObject("errors", fieldErrorMesages);
            return modelAndView;
        }

        String authKey = RandomStringUtils.random(20, true, true);
        String registrationCode = RandomStringUtils.random(30, true, true);

        User newUser = new User(registerUserRequest.getEmail1(), RandomStringUtils.random(30, true, true), registerUserRequest.getLatitude(),
                registerUserRequest.getLongitude(), registerUserRequest.getSiteName(),
                /* enabled */false,
                /* admin */false,
                /* expired */false,
                /* locked */false,
                /* credentialsExpired */false, authKey,
                /* emailSent */true,
                /* createdDate */clock.currentDate(),
                registrationCode,
                false);

        Map<String, Object> model = new HashMap<String, Object>();

        model.put("siteName", registerUserRequest.getSiteName());
        model.put("authKey", authKey);
        model.put("registrationCode", registrationCode);

        userDao.save(newUser);

        Mail mail
                = new Mail("operations@funcube.org.uk",
                registerUserRequest.getEmail1(),
                "AMSAT Data Warehouse Registration", model);

        mailService.sendSimpleMessage(mail);

        ModelAndView modelAndView = new ModelAndView("content/registration-confirmation");
        return modelAndView;
    }

    private List<String> getErrorMesages(List<FieldError> fieldErrors) {
        List<String> errors = new ArrayList<>();
        for(FieldError fieldError : fieldErrors) {
            if (fieldError.getField().equals("siteName")) {
                errors.add("Site Name has already been registered");
            }
            else if (fieldError.getField().equals("email1") || fieldError.getField().equals("email2")) {
                if (fieldError.getDefaultMessage().equals("The email fields must match") && !errors.contains("The email fields must match")) {
                    errors.add("The email fields must match");
                    break;
                }
                else if (fieldError.getDefaultMessage().equals("Already registered") && !errors.contains("Email address has already been registered")) {
                    errors.add("Email address has already been registered");
                }
            }
        }

        if (errors.contains("The email fields must match") && errors.contains("Email address has already been registered")) {
            errors.remove("Email address has already been registered");
        }

        return errors;
    }
}
