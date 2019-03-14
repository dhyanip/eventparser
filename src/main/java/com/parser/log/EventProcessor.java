package com.parser.log;

import java.math.BigDecimal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;


public class EventProcessor implements ItemProcessor<Event, Event> {

    private static final Logger log = LoggerFactory.getLogger(EventProcessor.class);

    @Autowired
	EventRepository eventRepo;
    
    @Autowired
    AlertRepository alertRepo;
    
    private static final String  FINISHED = "FINISHED";
    
    private static final int ALERT_TIME = 4;

    @Override
	public Event process(final Event event) throws Exception {
		// TODO: cleanup the below code

		Optional<Event> oldEvent = eventRepo.findById(event.getId());
		String newTimeStamp = event.getTimestamp();
		// check if its STARTED or FINISHED
		if (oldEvent.isPresent()) {
			String oldTimeStamp = oldEvent.get().getTimestamp();
			String diffrence = null;
			if (FINISHED.equals(event.getState())) {
				diffrence = new BigDecimal(newTimeStamp).subtract(new BigDecimal(oldTimeStamp)).toString();
			} else {
				diffrence = new BigDecimal(oldTimeStamp).subtract(new BigDecimal(newTimeStamp)).toString();
			}
			event.setProcessTime(diffrence);
			eventRepo.save(event);
			if(Integer.parseInt(diffrence) >ALERT_TIME){
				//TODO: The data can be written into the flat file also at this place.
				Alert alert = new Alert();
				alert.setId(event.getId());
				alert.setHost(event.getHost());
				alert.setProcessTime(event.getProcessTime());
				alertRepo.save(alert);
				log.info("ALERT - process time for the event is more thn required : "+alert.toString());
			}
			
		} else {
			eventRepo.save(event);
		}

		return event;
	}

}
