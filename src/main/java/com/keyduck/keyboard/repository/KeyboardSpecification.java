package com.keyduck.keyboard.repository;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.dto.KeyboardFilterDto;
import com.keyduck.keyboard.dto.KeyboardSearchDto;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class KeyboardSpecification {

    public static final int FULL_ARRANGEMENT = 1;
    public static final int MINI_ARRANGEMENT = 0;

    public static Specification<Keyboard> equalKey(KeyboardFilterDto params) {
        return (Specification<Keyboard>) (root, query, builder) -> {
            List<Predicate> predicate = getPredicateWithKeywords(params,root,builder);
            return builder.and(predicate.toArray(new Predicate[0]));
        };
    }

    private static List<Predicate> getPredicateWithKeywords(KeyboardFilterDto params, Root<Keyboard> root, CriteriaBuilder builder ){
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

        if (params.getSwitchColor() != null && !params.getSwitchColor().equals("")) {
            Predicate switchColorPredicate = builder.conjunction();
            String[] switchColorList = params.getSwitchColor();

            if(switchColorList.length < 2){
               predicate.add(builder.and(builder.like(root.get("switchColor"),"%"+(String)switchColorList[0]+"%")));
            }

            else{
            switchColorPredicate = builder.and(builder.like(root.get("switchColor"),"%"+(String)switchColorList[0]+"%"));
            for(int i = 1; i< switchColorList.length; i++ ){
                predicate.add(builder.or(builder.like(root.get("switchColor"), "%"+(String)switchColorList[i]+"%"),switchColorPredicate));
                }
            }
        }
        

        if (params.getHotswap()!= null && !params.getHotswap().equals("")) {
            predicate.add(builder.like(root.get("hotswap"), "%"+(String)params.getHotswap()+"%"));
        }

        if (params.getLed()!= null && !params.getLed().equals("")) {
            predicate.add(builder.like(root.get("led"), "%"+(String)params.getLed()+"%"));
        }

        if (params.getKeycapProfile() != null && !params.getKeycapProfile().equals("")) {
            predicate.add(builder.like(root.get("keycapProfile"), "%"+(String)params.getKeycapProfile()+"%"));
        }

        if (params.getArrangement() > -1) {
            int INF = 10000000;
            if(params.getArrangement() == FULL_ARRANGEMENT){
                predicate.add(builder.between(root.get("arrangement"),96, INF));
            }
            else if(params.getArrangement() == MINI_ARRANGEMENT){
                predicate.add(builder.between(root.get("arrangement"),0, 95));
            }
        }

        if (params.getWeight() > 0) {
            predicate.add(builder.between(root.get("weight"),Integer.valueOf(0),Integer.valueOf(params.getWeight())));
        }

        if (params.getStartPrice() >= 0 && params.getEndPrice() >= 0) {
            predicate.add(builder.between(root.get("price"),Integer.valueOf(params.getStartPrice()),Integer.valueOf(params.getEndPrice())));
        }


        return predicate;
    }
}
