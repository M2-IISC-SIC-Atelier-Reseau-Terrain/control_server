package fr.cyu.rt.control.persistence.event;

import fr.cyu.rt.control.business.event.Event;
import fr.cyu.rt.control.business.event.EventType;
import fr.cyu.rt.control.business.event.QEvent;
import fr.cyu.rt.control.dao.event.EventDao;
import fr.cyu.rt.control.persistence.jpa.BaseJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public boolean isSensorAlerted(String sensorId) {
        // Retrieve last alert decision and last alert on sensor id
        LocalDateTime lastDecisionTime = queryDslFactory()
                .selectFrom(EVENT)
                .where(EVENT.eventType.eq(EventType.ALERT_DECISION))
                .orderBy(EVENT.timestampReceived.desc())
                .stream()
                .findFirst()
                .map(Event::getTimestampReceived)
                .orElse(LocalDateTime.MIN);

        Optional<Event> lastAlertOnSensor = queryDslFactory()
                .selectFrom(EVENT)
                .where(EVENT.eventType.eq(EventType.ALERT),
                       EVENT.sensorId.eq(sensorId)
                )
                .orderBy(EVENT.timestampReceived.desc())
                .stream()
                .findFirst();

        return lastAlertOnSensor
                .map(Event::getTimestampReceived)
                .filter(t -> lastDecisionTime.isBefore(t))
                .isPresent();
    }

    @Override
    public Event save(Event event) {
        return saveEntity(event);
    }
}
