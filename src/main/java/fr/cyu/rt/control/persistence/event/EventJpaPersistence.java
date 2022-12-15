package fr.cyu.rt.control.persistence.event;

import fr.cyu.rt.control.business.event.Event;
import fr.cyu.rt.control.business.event.QEvent;
import fr.cyu.rt.control.dao.event.EventDao;
import fr.cyu.rt.control.persistence.jpa.BaseJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Aldric Vitali Silvestre
 */
@Repository
public class EventJpaPersistence extends BaseJpaRepository<Event, Long> implements EventDao {

    public EventJpaPersistence(EntityManager em) {
        super(Event.class, em);
    }

    private static QEvent EVENT = QEvent.event;

    // TODO save to log file (later)

    @Override
    public List<Event> getEventsForSensor(String sensorId, long durationS) {
        LocalDateTime minTimestamp = LocalDateTime.now().minusSeconds(durationS);
        return queryDslFactory()
                .selectFrom(EVENT)
                .where(EVENT.sensorId.eq(sensorId), EVENT.timestampReceived.after(minTimestamp))
                .orderBy(EVENT.timestampReceived.asc())
                .fetch();
    }

    @Override
    public List<Event> getAll() {
        return queryDslFactory()
                .selectFrom(EVENT)
                .orderBy(EVENT.timestampReceived.asc())
                .fetch();
    }

    @Override
    public Event save(Event event) {
        return saveEntity(event);
    }
}
