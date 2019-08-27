package com.krisunni.user.repository.impl;

import com.krisunni.user.domain.User;
import com.krisunni.user.domain.dto.MultiUserRequest;
import com.krisunni.user.repository.UserRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findUserbyColumns(MultiUserRequest multiUserRequest) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> user = query.from(User.class);

        Path<String> firstNamePath = user.get("firstName");
        Path<String> lastNamePath = user.get("lastName");
        Path<String> telephonePath = user.get("telephone");
        Path<String> emailPath = user.get("email");
        List<Predicate> predicates = new ArrayList<>();

        multiUserRequest.getColumns().forEach(column -> {
            switch (column) {
                case "firstName":
                    predicates.add(cb.isNotNull(firstNamePath));
                    break;
                case "lastName":
                    predicates.add(cb.isNotNull(lastNamePath));
                    break;
                case "telephone":
                    predicates.add(cb.isNotNull(telephonePath));
                    break;
                case "email":
                    predicates.add(cb.isNotNull(emailPath));
                    break;
            }
        });
        query.distinct(true);
        query.select(user).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        return entityManager.createQuery(query).setFirstResult(multiUserRequest.getPage()).setMaxResults(multiUserRequest.getSize()).getResultList();
    }
}