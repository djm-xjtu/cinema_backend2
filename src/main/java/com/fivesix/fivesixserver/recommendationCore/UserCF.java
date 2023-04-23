package com.fivesix.fivesixserver.recommendationCore;

import com.fivesix.fivesixserver.entity.RelatedDTO;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class UserCF {
    public static List<Integer> recommend(String username, List<RelatedDTO> list) {
        //按用户分组
        Map<String, List<RelatedDTO>>  userMap=list.stream().collect(Collectors.groupingBy(RelatedDTO::getUsername));
        //获取其他用户与当前用户的关系值
        Map<String,Double> userDisMap = Algorithm.computeNeighbor(username, userMap,0);
        //获取关系最近的用户
        double maxValue= Collections.max(userDisMap.values());
        Set<String> userIds=userDisMap.entrySet().stream().filter(e->e.getValue()==maxValue).map(Map.Entry::getKey).collect(Collectors.toSet());
        //取关系最近的用户
        String nearestUserId = userIds.stream().findAny().orElse(null);
        if(nearestUserId==null){
            return Collections.emptyList();
        }
        //最近邻用户看过电影列表
        List<Integer>  neighborItems = userMap.get(nearestUserId).stream().map(RelatedDTO::getCinema_id).collect(Collectors.toList());
        //指定用户看过电影列表
        List<Integer>  userItems  = userMap.get(username).stream().map(RelatedDTO::getCinema_id).collect(Collectors.toList());
        //找到最近邻看过，但是该用户没看过的电影
        neighborItems.removeAll(userItems);
        return neighborItems;
    }
}
