package com.fivesix.fivesixserver.recommendationCore;

import com.fivesix.fivesixserver.entity.RelatedDTO;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class UserCF {
    public static List<Integer> recommend(String username, List<RelatedDTO> list) {
        Map<String, List<RelatedDTO>> userMap=list.stream().collect(Collectors.groupingBy(RelatedDTO::getUsername));
        Map<String,Double> userDisMap = Algorithm.computeNeighbor(username, userMap,0);
        double maxValue= Collections.max(userDisMap.values());
        Set<String> userIds=userDisMap.entrySet().stream().filter(e->e.getValue()==maxValue).map(Map.Entry::getKey).collect(Collectors.toSet());
        String nearestUserId = userIds.stream().findAny().orElse(null);
        if(nearestUserId==null){
            return Collections.emptyList();
        }
        List<Integer> neighborItems = userMap.get(nearestUserId).stream().map(RelatedDTO::getCinema_id).collect(Collectors.toList());
        List<Integer> userItems  = userMap.get(username).stream().map(RelatedDTO::getCinema_id).collect(Collectors.toList());
        neighborItems.removeAll(userItems);
        return neighborItems;
    }
}
