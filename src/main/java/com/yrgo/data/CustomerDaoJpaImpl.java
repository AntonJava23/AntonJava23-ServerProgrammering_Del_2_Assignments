package com.yrgo.data;

import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CustomerDaoJpaImpl implements CustomerDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Customer newCustomer) {
        em.persist(newCustomer);
    }

    @Override
    public Customer getById(String customerId) throws RecordNotFoundException {
        return em.createQuery("SELECT customer FROM Customer AS customer WHERE customer.customerId=:customerId",
                        Customer.class)
                 .setParameter("customerId", customerId)
                 .getSingleResult();
    }

    @Override
    public List<Customer> getByName(String name) {
        return em.createQuery("SELECT customer FROM Customer AS customer WHERE customer.companyName=:name",
                        Customer.class)
                 .setParameter("name", name)
                 .getResultList();
    }

    @Override
    public void update(Customer customerToUpdate) throws RecordNotFoundException {
        em.merge(customerToUpdate);
    }

    @Override
    public void delete(Customer oldCustomer) throws RecordNotFoundException {
        em.remove(oldCustomer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return em.createQuery("SELECT customer FROM Customer AS customer", Customer.class)
                 .getResultList();
    }

    @Override
    public Customer getFullCustomerDetail(String customerId) throws RecordNotFoundException {
        return em.createQuery(
                "SELECT customer FROM Customer AS customer " +
                   "LEFT JOIN FETCH customer.calls " +
                   "WHERE customer.customerId=:customerId", Customer.class)
                .getSingleResult();
    }

    @Override
    public void addCall(Call newCall, String customerId) throws RecordNotFoundException {
        Customer customer = em.find(Customer.class, customerId);
        customer.addCall(newCall);
    }
}
