package com.ku.quizzical.app.helper.controller;

import java.util.List;
import java.util.function.Consumer;
import com.ku.quizzical.app.controller.user.User;
import com.ku.quizzical.app.util.TestRestTemplateContainer;
import com.ku.quizzical.app.util.integration.RealizedTestUserMaker;
import com.ku.quizzical.common.helper.ListHelper;
import com.ku.quizzical.common.helper.MapHelper;

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
     * Private Constructor to prevent instantiation
     */
    private IntegrationTestHelper() {
        super();
    }
}
