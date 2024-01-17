package com.ku.quizzical.app.helper.controller;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import com.ku.quizzical.app.controller.auth.AuthenticationRequest;
import com.ku.quizzical.app.controller.user.User;
import com.ku.quizzical.app.controller.user.UserRegistrationRequest;
import com.ku.quizzical.app.util.TestRestTemplateContainer;
import com.ku.quizzical.app.util.integration.RealizedTestUserMaker;
import com.ku.quizzical.common.helper.MapHelper;
import com.ku.quizzical.common.helper.list.ListHelper;
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
                IntegrationTestHelper.testWithRealizedUsers(container, (
                                TestRestTemplateContainer enteredContainer,
                                Map<String, UserRegistrationRequest> registrationRequests,
                                List<User> users) -> action.accept(users));
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
                ListHelper.forEach(realizedUsers, (User user) -> {
                        AuthenticationRequest request = new AuthenticationRequest(
                                        user.getUsername(),
                                        registrationRequests.get(user.getUsername()).password());
                        AuthenticationTestHelper.logIn(request, container);
                        UserTestHelper.deleteUserById(user.getId(), container);
                        AuthenticationTestHelper.logOut(container);
                });
        }

        /**
         * Private Constructor to prevent instantiation
         */
        private IntegrationTestHelper() {
                super();
        }
}
