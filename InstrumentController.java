package com.bandcat.BandCat.controller;

import com.bandcat.BandCat.model.Instrument;
import com.bandcat.BandCat.model.InstrumentOptions;
import com.bandcat.BandCat.model.User;
import com.bandcat.BandCat.service.InstrumentService;
import com.bandcat.BandCat.service.UserService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is designed to handle requests/responses for Instrument-based
 * tasks between the frontend and Service.
 * Spring, Spring Web, etc. contain the logic needed to
 * manage requests sent to the API and responses sent outward.
 */
@RestController
@CrossOrigin
@RequestMapping("/instruments")
public class InstrumentController
{
    final private InstrumentService instrumentService;
    final private UserService userService;
    final private Logger logger;


    public InstrumentController(InstrumentService instrumentService, UserService userService, Logger logger)
    {
        this.instrumentService = instrumentService;
        this.userService = userService;
        this.logger = logger;
    }

    /**
     * Method to create new Instrument from a http request
     * @author Tyler, Marcus, Elaine, Christian
     * @param instrument Instrument to persist
     * @param id User to find and update
     *
     */
    @PostMapping("/{id}")
    public Instrument createInstrument(@RequestBody Instrument instrument, @PathVariable int id)
    {
        if (instrument != null && id != 0)
        {
            try
            {
                User u = userService.findByUserID(id);
                return instrumentService.createNewInstrument(instrument, u);
            }
            catch (Exception e)
            {
                logger.warn(e.getMessage());
            }
        }
        return null;
    }

    /**
     * Method -> Gets a list of Users based on the Instrument being passed in
     * @param instrument The Instrument search criteria
     * @return The list of Users found
     */
    @PostMapping("/findUsers")
    public List<User> findByInstrument(@RequestBody Instrument instrument)
    {
        // If there is an instrument to search for
        if (instrument != null)
        {
            // Search for users based on the instrument
            return instrumentService.findListByInstrument(instrument);
        }
        else return null;
    }

    /**
     * Method to get all Instruments
     * @author Tyler, Marcus
     *
     */
    @GetMapping("/all")
    public List<Instrument> getAllInstruments()
    {
        return instrumentService.getAllInstruments();
    }

    /**
     * Method to get all Instruments by instrumentName
     * @author Tyler, Marcus
     * @param name Instrument Name to search by
     *
     */
    @GetMapping("/allByName/{name}")
    public List<Instrument> getAllInstrumentsByName(@PathVariable InstrumentOptions name)
    {
        return instrumentService.findListByInstrumentName(name);
    }

    /**
     * Method to get all Instruments by confidence
     * @author Tyler, Marcus
     * @param confidence Confidence amount to search by
     */
    @GetMapping("/allByConfidence/{confidence}")
    public List<Instrument> getAllInstruments(@PathVariable int confidence)
    {
        return instrumentService.findListByConfidence(confidence);
    }
}
