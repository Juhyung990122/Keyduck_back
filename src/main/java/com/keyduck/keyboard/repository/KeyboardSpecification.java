package com.keyduck.keyboard.repository;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.dto.KeyboardSearchDto;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


public class KeyboardSpecification {
    public static Specification<Keyboard> equalKey(KeyboardSearchDto params) {
        return (Specification<Keyboard>) ((root, query, builder) -> {
            List<Predicate> predicate = getPredicateWithKeywords(params,root,builder);
            return builder.and(predicate.toArray(new Predicate[0]));
        });
    }



    public static List<Predicate> getPredicateWithKeywords(KeyboardSearchDto params, Root<Keyboard> root, CriteriaBuilder builder ){
        List<Predicate> predicate = new ArrayList<>();
        if (params.getBrand() != null && !params.getBrand().equals("")) {
            predicate.add(builder.like(root.get("brand"), "%"+(String)params.getBrand()+"%"));
        }

        if (params.getConnect() != null && !params.getConnect().equals("")) {
            predicate.add(builder.like(root.get("connect"), "%"+(String)params.getConnect()+"%"));
        }


        if (params.getSwitchBrand() != null && !params.getSwitchBrand().equals("")) {
            predicate.add(builder.like(root.get("switchBrand"), "%"+(String)params.getSwitchBrand()+"%"));
        }

        if (params.getHotswap() != null && !params.getHotswap().equals("")) {
            predicate.add(builder.like(root.get("hotswap"), "%"+(String)params.getHotswap()+"%"));
        }

        if (params.getKeycapProfile() != null && !params.getKeycapProfile().equals("")) {
            predicate.add(builder.like(root.get("keycapProfile"), "%"+(String)params.getKeycapProfile()+"%"));
        }

        if (params.getArrangement() != 0) {
            predicate.add(builder.equal(root.get("arrangement"), Integer.valueOf(params.getArrangement())));
        }

        if (params.getStartPrice() != 0 && params.getEndPrice() != 0) {
            predicate.add(builder.between(root.get("price"),Integer.valueOf(params.getStartPrice()),Integer.valueOf(params.getEndPrice())));
        }

        return predicate;
    }
}
