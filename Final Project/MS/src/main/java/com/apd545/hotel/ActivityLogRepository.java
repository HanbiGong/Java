package com.apd545.hotel;

import jakarta.persistence.EntityManager;
import java.util.List;

/**
 * DB operations for Activity Logs
 */
public class ActivityLogRepository {

    private static final ActivityLogRepository instance = new ActivityLogRepository();
    public static ActivityLogRepository getInstance() { return instance; }

    private ActivityLogRepository() {}

    public void save(ActivityLog log) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(log);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<ActivityLog> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT l FROM ActivityLog l ORDER BY l.id DESC", ActivityLog.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
