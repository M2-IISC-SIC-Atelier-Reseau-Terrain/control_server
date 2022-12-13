package fr.cyu.rt.control.dao.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;

/**
 * @author Aldric Vitali Silvestre
 */
public abstract class BaseJpaRepository<T, ID> extends SimpleJpaRepository<T, ID> {

    private final EntityManager entityManager;
    private final JPAQueryFactory queryDslFactory;

    public BaseJpaRepository(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.entityManager = em;
        this.queryDslFactory = new JPAQueryFactory(entityManager);
    }

    public JPAQueryFactory queryDslFactory() {
        return queryDslFactory;
    }

    public T saveEntity(T entity) {
        return super.save(entity);
    }

    public void clear() {
        entityManager.clear();
    }

    public void detach(T entity) {
        entityManager.detach(entity);
    }
}
