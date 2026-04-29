package com.betacom.pr;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

import com.betacom.pr.controllers.AddressControllerTest;
import com.betacom.pr.controllers.CategoryControllerTest;
// import com.betacom.pr.controllers.ImageControllerTest;
import com.betacom.pr.controllers.ProductControllerTest;
import com.betacom.pr.controllers.ShoppingCartControllerTest;
import com.betacom.pr.controllers.SubcategoryControllerTest;
import com.betacom.pr.controllers.UserControllerTest;
import com.betacom.pr.controllers.UserOrderControllerTest;

@Suite
@SelectClasses({
    UserControllerTest.class,
    CategoryControllerTest.class,
    SubcategoryControllerTest.class,
    ProductControllerTest.class,
    AddressControllerTest.class,
    UserOrderControllerTest.class,
    ShoppingCartControllerTest.class,
    // ImageControllerTest.class
})
@SpringBootTest
class GardeniaBackendApplicationTests {

    @Test
    void contextLoads() {
    }
}