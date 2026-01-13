package com.apd545.hotel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Simple repository for Customer entity.
 */
public class CustomerRepository {

    private static final CustomerRepository instance = new CustomerRepository();
    public static CustomerRepository getInstance() { return instance; }

    private CustomerRepository() {}

    public List<Customer> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Customer> q =
                    em.createQuery("SELECT c FROM Customer c ORDER BY c.id DESC", Customer.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}
