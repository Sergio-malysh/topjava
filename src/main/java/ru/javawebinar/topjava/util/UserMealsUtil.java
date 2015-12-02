package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, ArrayList<UserMeal>> mapMeal = new HashMap<>();
        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();

         for (UserMeal meal : mealList) {
               LocalDate localDate = meal.getDateTime().toLocalDate();
                  if (!mapMeal.containsKey(localDate))
                       mapMeal.put(localDate, new ArrayList<>());
                  mapMeal.get(localDate).add(meal);
         }
         for (ArrayList<UserMeal> userMeals : mapMeal.values()) {
            int CaloriesPerDay = 0;
                for (UserMeal userMeal : userMeals) {
                    CaloriesPerDay += userMeal.getCalories();
            }
                boolean exceed = CaloriesPerDay < caloriesPerDay;
                            userMealWithExceeds.addAll(userMeals.stream()
                            .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                            .map(userMeal -> new UserMealWithExceed(
                            userMeal.getDateTime(),
                            userMeal.getDescription(),
                            userMeal.getCalories(),
                            exceed)).collect(Collectors.toList()));
        }
        //System.out.println(userMealWithExceeds.toString());

        return userMealWithExceeds;
    }
}