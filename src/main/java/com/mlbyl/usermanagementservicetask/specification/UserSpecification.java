package com.mlbyl.usermanagementservicetask.specification;

import com.mlbyl.usermanagementservicetask.dto.UserFilterRequest;
import com.mlbyl.usermanagementservicetask.entity.User;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public static Specification<User> build(UserFilterRequest filter) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getName() != null && !filter.getName().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")),
                        "%" + filter.getName().toLowerCase() + "%"));
            }

            if (filter.getSurname() != null && !filter.getSurname().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("surname")),
                        "%" + filter.getSurname().toLowerCase() + "%"));
            }

            if (filter.getEmail() != null && !filter.getEmail().isEmpty()) {
                predicates.add(cb.equal(root.get("email"), filter.getEmail()));
            }

            if (filter.getPhoneNumber() != null && !filter.getPhoneNumber().isEmpty()) {
                predicates.add(cb.equal(root.get("phoneNumber"), filter.getPhoneNumber()));
            }

            if (filter.getDateOfBirthFrom() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get("dateOfBirth"),
                                filter.getDateOfBirthFrom()));
            }

            if (filter.getDateOfBirthTo() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(root.get("dateOfBirth"), filter.getDateOfBirthTo()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}


