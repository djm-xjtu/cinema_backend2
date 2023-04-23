package com.fivesix.fivesixserver.recommendationCore;

import com.fivesix.fivesixserver.entity.RelatedDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

public class Algorithm {
    public static Map<String,Double> computeNeighbor(String key, Map<String,List<RelatedDTO>>  map,int type) {
        Map<String,Double> distMap = new TreeMap<>();
        List<RelatedDTO> userItems = map.get(key);
        map.forEach((k,v)->{
            //排除此用户
            if(!k.equals(key)){
                //关系系数
                double coefficient = relateDist(v,userItems,type);
                //关系距离
                //   double distance=Math.abs(coefficient);
                distMap.put(k,coefficient);
            }
        });
        return distMap;
    }

    private static double relateDist(List<RelatedDTO> xList, List<RelatedDTO> yList,int type) {
        List<Double> xs= new ArrayList<>();
        List<Double> ys= new ArrayList<>();
        xList.forEach(x->{
            yList.forEach(y->{
                if(type==0){
                    if(x.getCinema_id().equals(y.getCinema_id())){
                        xs.add(x.getRating());
                        ys.add(y.getRating());
                    }
                }else{
                    if(x.getUsername().equals(y.getUsername())){
                        xs.add(x.getRating());
                        ys.add(y.getRating());
                    }
                }
            });
        });
        return getRelate(xs,ys);
    }

    public static double getRelate(List<Double> xs, List<Double> ys){
        int n=xs.size();
        if (n<2) {
            return 0D;
        }
        double Ex = xs.stream().mapToDouble(x->x).sum();
        double Ey = ys.stream().mapToDouble(y->y).sum();
        double Ex2 = xs.stream().mapToDouble(x->Math.pow(x,2)).sum();
        double Ey2 = ys.stream().mapToDouble(y->Math.pow(y,2)).sum();
        double Exy = IntStream.range(0,n).mapToDouble(i->xs.get(i)*ys.get(i)).sum();
        double numerator = Exy-Ex*Ey/n;
        double denominator = Math.sqrt((Ex2-Math.pow(Ex,2)/n)*(Ey2-Math.pow(Ey,2)/n));
        if (denominator == 0) {
            return 0D;
        }
        return numerator/denominator;
    }

}
