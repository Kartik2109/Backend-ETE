package com.example.EteBackend.repository;


import com.example.EteBackend.model.UserDetails;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class UserRepository {

    @PersistenceUnit(unitName = "Backendtest")
    private EntityManagerFactory entityManagerFactory;

    public boolean register(UserDetails newUser) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(newUser);
            transaction.commit();
            return true;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            transaction.rollback();
            return false;
        }
    }
    public UserDetails checkCredentials(String username) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            //TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
            TypedQuery<UserDetails> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", UserDetails.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    public List<UserDetails> getAllPosts(Integer userID){
        EntityManager em= entityManagerFactory.createEntityManager();
        TypedQuery<UserDetails> query= em.createQuery("SELECT * from User u join fetch u.user u where u.id= :userid", UserDetails.class);
        query.setParameter("userid",userID);
        List<UserDetails> result=query.getResultList();

        return result;
    }

}
