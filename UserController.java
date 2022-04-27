package com.bandcat.BandCat.controller;

import com.bandcat.BandCat.model.Instrument;
import com.bandcat.BandCat.model.User;
import com.bandcat.BandCat.service.InstrumentService;
import com.bandcat.BandCat.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is designed to handle requests/responses for User-based
 * tasks between the frontend and Service.
 * Spring, Spring Web, etc. contain the logic needed to
 * manage requests sent to the API and responses sent outward.
 */
@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController
{
    /**
     * Reference to Dependencies
     */
    final private UserService userService;
    final private InstrumentService instrumentService;
    final private Logger logger;

    /**
     * Constructor -> Get needed dependencies
     * @author Tyler, Marcus
     * @param userService UserService dependency
     * @param instrumentService InstrumentService dependency
     * @param logger Logger dependency
     */
    public UserController(UserService userService, InstrumentService instrumentService, Logger logger)
    {
        this.userService = userService;
        this.instrumentService = instrumentService;
        this.logger = logger;
    }

    /**
     * A method to create a user from a http request
     * @param user User to create
     * @author Tom and Tyler
     */
    @PostMapping
    public User createNewUser(@RequestBody User user)
    {
        if (user != null)
        {
            user = userService.createNewUser(user);  // returns user profile

            if (user != null)
            {
                return user;
            }
            else
            {
                logger.warn("Couldn't encrypt password upon creation!");
                return null;
            }
        }
        else
        {
            return null;    // Return null since a proper User object was not received
        }

    }

    /**
     * Gets all Users
     * @return The List of Users found
     */
    @GetMapping
    public List<User> getAllUsers()
    {
        return userService.getAllUsers();
    }

    /**
     * A method to find a user by their User ID
     * @author Tyler
     * @param id ID to search by
     */
    @GetMapping("/{id}")
    public User getByUserId(@PathVariable Integer id)
    {
        try
        {
            return userService.findByUserID(id); // returns user profile according to their ID number
        }
        catch (Exception e)
        {
            logger.warn(e.getMessage());    // Log exception
            return null;                    // Return null since no User was found
        }
    }

    /**
     * A method to find user information based off username
     * @author Tyler
     * @param username Username to search by
     */
    @GetMapping("/byUsername/{username}")
    public User findByUsername(@PathVariable String username)
    {
        try
        {
            return userService.findByUsername(username); // returns a user profile based off their username
        }
        catch (Exception e)
        {
            logger.warn(e.getMessage());    // Log exception
            return null;                    // Return null since no User was found
        }
    }

    /**
     * Method to update the User's Instrument
     * @author Tyler, Marcus
     * @param updatedInstrument New Instrument information
     * @param userID User to update
     * @return The Updated User
     */
    @PostMapping("/updateInstrument/{userID}")
    public User updateUserInstrument(@RequestBody Instrument updatedInstrument, @PathVariable int userID)
    {
        User u = userService.findByUserID(userID);                      // Find User to update
        Instrument i = instrumentService.getByInstrumentID(userID);     // Find Instrument to update

        i.setConfidence(updatedInstrument.getConfidence());             // Update with new information
        i.setInstrumentName(updatedInstrument.getInstrumentName());

        return instrumentService.createNewInstrument(i, u).getUser();   // Update persist the new Instrument info for the User
    }
}

