package com.stadnikov.voting.web;

import com.stadnikov.voting.web.menu.AdminFoodControllerTest;
import com.stadnikov.voting.web.menu.UserFoodControllerTest;
import com.stadnikov.voting.web.restaurant.AdminRestaurantControllerTest;
import com.stadnikov.voting.web.restaurant.UserRestaurantControllerTest;
import com.stadnikov.voting.web.user.AdminUserControllerTest;
import com.stadnikov.voting.web.user.ProfileControllerTest;
import com.stadnikov.voting.web.vote.AdminVoteControllerTest;
import com.stadnikov.voting.web.vote.UserVoteControllerTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Restaurant-voting Build Verification Test")
@SelectClasses({
        AdminFoodControllerTest.class,
        UserFoodControllerTest.class,
        UserVoteControllerTest.class,
        AdminVoteControllerTest.class,
        AdminRestaurantControllerTest.class,
        AdminUserControllerTest.class,
        UserRestaurantControllerTest.class,
        ProfileControllerTest.class
})
public class AllTestsRunner {
}
