package com.bandcat.BandCat.controller;

import com.bandcat.BandCat.model.User;
import com.bandcat.BandCat.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/login")
public class LoginController
{

    private final UserService userService;

    /**
     * Constructor -> Injects needed dependencies
     * @author Marcus
     * @param userService UserService dependency
     */
    public LoginController(UserService userService)
    {
        this.userService = userService;
    }

    /**
     * Method -> Checks a User that is logging in
     * @param user The info to be checked
     * @return The User after validation
     */
    @PostMapping
    public User checkLogin(@RequestBody User user)
    {
        if (user != null)
        {
            User dbUser = userService.findByUsername(user.getUsername());

            if (dbUser != null) // Check if User exists
            {
                if (userService.comparePassword(user, dbUser))  // Check password
                {
                    user = dbUser;  // User credentials match
                }
            }
        }

        return user;    // Returns the User, has no ID if User had wrong password or doesn't exist
    }
}
