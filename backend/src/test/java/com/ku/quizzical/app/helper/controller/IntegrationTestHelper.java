package com.ku.quizzical.app.helper.controller;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import com.ku.quizzical.app.controller.user.User;
import com.ku.quizzical.app.controller.user.UserRegistrationRequest;
import com.ku.quizzical.app.util.TestRestTemplateContainer;
import com.ku.quizzical.app.util.integration.RealizedTestUserMaker;
import com.ku.quizzical.common.helper.ListHelper;
import com.ku.quizzical.common.helper.MapHelper;
import com.ku.quizzical.common.util.function.TriConsumer;

/**
 * Helper class for Integration Test Operations
 */
public class IntegrationTestHelper {
    /**
     * Tests with a List of Realized Users
     */
    public static void testWithRealizedUsers(TestRestTemplateContainer container,
            Consumer<List<User>> action) {
        List<User> realizedUsers =
                MapHelper.toValueList(RealizedTestUserMaker.newInstance().perform(container, 2));
        action.accept(realizedUsers);
        ListHelper.forEach(realizedUsers,
                (User user) -> UserTestHelper.deleteUserById(user.getId(), container));
    }

    /**
     * Tests with a List of Realized Users
     */
    public static void testWithRealizedUsers(TestRestTemplateContainer container,
            TriConsumer<TestRestTemplateContainer, Map<String, UserRegistrationRequest>, List<User>> action) {
        RealizedTestUserMaker realizedTestUserMaker = RealizedTestUserMaker.newInstance();
        List<User> realizedUsers =
                MapHelper.toValueList(realizedTestUserMaker.perform(container, 2));
        Map<String, UserRegistrationRequest> registrationRequests =
                realizedTestUserMaker.getUserRegistrationRequests();
        action.accept(container, registrationRequests, realizedUsers);
        ListHelper.forEach(realizedUsers,
                (User user) -> UserTestHelper.deleteUserById(user.getId(), container));
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private IntegrationTestHelper() {
        super();
    }
}
