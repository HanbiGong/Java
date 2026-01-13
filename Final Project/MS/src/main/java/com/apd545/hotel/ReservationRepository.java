package com.apd545.hotel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * Repository class that handles all JPA operations for Reservation entity.
 * This separates DB logic from the service layer.
 */
public class ReservationRepository {

    // Singleton pattern
    private static final ReservationRepository instance = new ReservationRepository();
    public static ReservationRepository getInstance() { return instance; }

    private ReservationRepository() {}

    // Save new reservation
    public void save(Reservation r) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(r);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Update reservation
    public void update(Reservation r) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(r);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Find by ID
    public Reservation findById(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Reservation.class, id);
        } finally {
            em.close();
        }
    }

    // Find all reservations
    public List<Reservation> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Reservation> q =
                    em.createQuery("SELECT r FROM Reservation r ORDER BY r.id DESC", Reservation.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    // Find by phone
    public Reservation findByPhone(String phone) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Reservation> q =
                    em.createQuery("SELECT r FROM Reservation r WHERE r.phone = :p", Reservation.class);
            q.setParameter("p", phone);

            List<Reservation> list = q.getResultList();
            return list.isEmpty() ? null : list.get(0);

        } finally {
            em.close();
        }
    }
}
