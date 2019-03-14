/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.parser.log;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.parser.log.Event;
import com.parser.log.EventRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EventRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EventRepository eventRepo;

    @Test
    public void testPersonRepo() {
        Event event = new Event( "1", "state", "1234", "type", "host");
        entityManager.persist(event);

        Iterable<Event> findPerson = eventRepo.findAll();

        assertThat(findPerson).extracting(Event::getState).containsOnly(event.getState());
        Event updatePerson = new Event( "1", "state", "1234", "type", "host");
        
        Event temp = eventRepo.save(updatePerson);
        assertThat(updatePerson.getState()).hasToString(temp.getState());
        
    }
    
    
}