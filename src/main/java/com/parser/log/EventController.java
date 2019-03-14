package com.parser.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
 * CRUD and other custom method requested by the client..
 * 
 * Note: use consumes as part of reqeustMapping for a specific type of request message 
 * consumes={"application/json","application/xml"},
 * 
 * More custom methods can be written here
 */
@RestController
@RequestMapping(value="/v1/event")
public class EventController {
	
	@Autowired
	EventRepository eventRepo;
	
	@Autowired
	AlertRepository alertRepo;

    @RequestMapping(value="",method={RequestMethod.GET})
    public Iterable<Event> get() {
        return eventRepo.findAll();
    }
    
    @RequestMapping(value="",method={RequestMethod.DELETE})
    public void delete(@RequestParam String id) {
    	
    	eventRepo.deleteById(id);
    }
    @RequestMapping(value="/count",method={RequestMethod.GET})
    public Long count() {
    	
         return eventRepo.count();
    }
    
    @RequestMapping(value="/alert",method={RequestMethod.GET})
    public Iterable<Alert> getAlert() {
        return alertRepo.findAll();
    }
    
    
}
