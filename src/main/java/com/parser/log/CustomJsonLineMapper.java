package com.parser.log;

import org.springframework.batch.item.file.LineMapper;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomJsonLineMapper implements LineMapper<Event> {

    private ObjectMapper mapper = new ObjectMapper();


    /**
     * Interpret the line as a Json object and create a Blub Entity from it.
     * 
     * @see LineMapper#mapLine(String, int)
     */
    @Override
    public Event mapLine(String line, int lineNumber) throws Exception {
        return mapper.readValue(line, Event.class);
    }


}
