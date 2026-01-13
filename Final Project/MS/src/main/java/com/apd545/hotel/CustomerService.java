package com.apd545.hotel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;


/**
 * Customer database service using JPA.
 */
public class CustomerService {

    private static final CustomerService instance = new CustomerService();
    public static CustomerService getInstance() { return instance; }

    private CustomerService() {}

    // Find customer by email or phone
    public Customer findCustomer(String email, String phone) {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            TypedQuery<Customer> q = em.createQuery(
                    "SELECT c FROM Customer c WHERE c.email = :e OR c.phone = :p",
                    Customer.class
            );
            q.setParameter("e", email);
            q.setParameter("p", phone);

            var list = q.getResultList();
            return list.isEmpty() ? null : list.get(0);

        } finally {
            em.close();
        }
    }

    // Create or update customer
    public Customer saveOrUpdate(Customer c) {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            Customer merged = em.merge(c);
            em.getTransaction().commit();
            return merged;

        } finally {
            em.close();
        }
    }

    // Create random loyalty number
    public String generateLoyaltyNumber() {
        int random = (int) (Math.random() * 90000) + 10000;
        return "L" + random;
    }

    public List<Customer> findAllCustomers() {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            TypedQuery<Customer> q = em.createQuery(
                    "SELECT c FROM Customer c ORDER BY c.points DESC",
                    Customer.class
            );
            return q.getResultList();

        } finally {
            em.close();
        }
    }

}
