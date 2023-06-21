package com.orgi.obt.batchtest.application;

import com.orgi.obt.batchtest.domain.Food;
import com.orgi.obt.batchtest.domain.FoodStatus;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class FoodItemProcessor implements ItemProcessor<Food, Food> {
    private static final int 폐기일수 = 7;
    private static final int 위험일수 = 4;

    public Food process(Food food){
        //제조일자 가져옴
        LocalDateTime dateofMake = food.getMakeDateTime();

        long daysDifference = compareCurrentDateAndMakeDate(dateofMake);
        //현재상태가 정상이고 일수차이가 위험일수를 넘으면
        if(food.getStatus().equals(FoodStatus.정상) && daysDifference>위험일수){
            food.changeFoodStatus(FoodStatus.위험);
        }
        //현재상태가 위험이고 일수차이가 폐기일수를 넘기면
        if(food.getStatus().equals(FoodStatus.위험) && daysDifference>폐기일수){
           food.changeFoodStatus(FoodStatus.폐기);
        }

        return food;

    }



    private long compareCurrentDateAndMakeDate(LocalDateTime dateofMake) {
        //ChronoUnit.DAYS.between = 일수 차이 계산
        //제조일자,지금 매개변수 받아서 계산
        return ChronoUnit.DAYS.between(dateofMake.toLocalDate(), LocalDateTime.now().toLocalDate());
    }
}
