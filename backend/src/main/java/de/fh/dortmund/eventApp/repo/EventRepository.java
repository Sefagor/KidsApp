package de.fh.dortmund.eventApp.repo;

import de.fh.dortmund.eventApp.entity.Event;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends ListCrudRepository<Event, Long> {

}
