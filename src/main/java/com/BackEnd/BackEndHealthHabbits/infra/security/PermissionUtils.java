package com.BackEnd.BackEndHealthHabbits.infra.security;



import com.BackEnd.BackEndHealthHabbits.entities.enums.Rule;

import java.util.HashSet;
import java.util.Set;

public class PermissionUtils {
    public static Set<Rule> expandRules(Set<Rule> userRules) {
        Set<Rule> expanded = new HashSet<>(userRules);
        boolean changed;
        do {
            changed = false;
            for (Rule rule : new HashSet<>(expanded)) {
                for (Rule implied : rule.getImpliedRules()) {
                    if (!expanded.contains(implied)) {
                        expanded.add(implied);
                        changed = true;
                    }
                }
            }
        } while (changed);
        return expanded;
    }
}
