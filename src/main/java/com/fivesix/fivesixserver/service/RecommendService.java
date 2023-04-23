package com.fivesix.fivesixserver.service;

import com.fivesix.fivesixserver.entity.Movie;
import com.fivesix.fivesixserver.entity.RelatedDTO;
import com.fivesix.fivesixserver.mapper.MovieMapper;
import com.fivesix.fivesixserver.recommendationCore.UserCF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class RecommendService {
    @Autowired
    MovieMapper movieMapper;
    public static List<Movie> userCFRecommend(String username, List<RelatedDTO> relatedDTOS){

        List<Integer> recommendations = UserCF.recommend(username, relatedDTOS);
        List<Movie> movies = new ArrayList<>();
        for(int i = 0; i < recommendations.size(); i++){
            movies.add(movieMapper.get(recommendations.get(i)));
        }
    }
}
