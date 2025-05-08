package me.alenalex.rekode.abstractions.models;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.UUID;

public interface IRekodeMineUser {

    @NotNull UUID mineUserId();

    @NotNull UUID mineId();

    @NotNull UUID userId();

    @NotNull String role();

    /**
     * The Roles class defines a set of predefined roles along with a mechanism to
     * retrieve all valid roles as a collection. These roles can be used to classify
     * and manage users within a system.
     */
    public static class Roles {

        /**
         * Represents the "OWNER" role used to classify or manage entities within a system.
         * This role typically signifies the entity that holds primary ownership or control rights.
         * It is part of the predefined roles in the system.
         */
        private static final String OWNER = "OWNER";

        /**
         * Represents the role of a co-owner within a system.
         * A co-owner has shared ownership responsibilities alongside another primary owner.
         * Typically used to classify or manage users with shared rights in a given context.
         */
        private static final String CO_OWNER = "CO_OWNER";

        /**
         * Represents the "MEMBER" role within the system's predefined set of roles.
         * This role is used to classify or manage entities as members, typically denoting
         * standard participants or users in the system without ownership or administrative privileges.
         */
        private static final String MEMBER = "MEMBER";

        /**
         * Represents the "VISITOR" role within the system's predefined set of roles.
         * This role is typically assigned to users or entities with minimal access or
         * permissions, often used to denote temporary or guest users within the system.
         * It is part of the predefined roles for classification and access control.
         */
        private static final String VISITOR = "VISITOR";

        /**
         * Represents the "BLOCKED" status within the system's predefined set of roles.
         * This status is typically used to classify or manage entities that are restricted
         * or prohibited from accessing certain system functionalities or resources.
         * It is part of the predefined roles and can be used for access control and classification.
         */
        private static final String BLOCKED = "BLOCKED";

        private static final HashSet<String> VALID_ROLES = new HashSet<>() {
            {
                add(OWNER);
                add(CO_OWNER);
                add(MEMBER);
                add(VISITOR);
            }
        };

        public static HashSet<String> all() { return VALID_ROLES; }

        /**
         * Retrieves the predefined role representing the "OWNER".
         * This role signifies primary ownership or control rights within a mine.
         *
         * @return the role identifier for "OWNER"
         */
        public static String owner() { return OWNER; }

        /**
         * Retrieves the predefined role representing the "CO_OWNER".
         * This role signifies secondary ownership rights, often with shared
         * or subordinate privileges compared to the primary owner.
         *
         * @return the role identifier for "CO_OWNER"
         */
        public static String coOwner() { return CO_OWNER; }

        /**
         * Retrieves the predefined role representing the "MEMBER".
         * This role signifies standard membership within a mine with basic access
         * rights or permissions to the mine.
         *
         * @return the role identifier for "MEMBER"
         */
        public static String member() { return MEMBER; }

        /**
         * Retrieves the predefined role representing the "VISITOR".
         * This role signifies minimal or restricted access rights, typically used for
         * guest users with limited permissions or temporary access to the mine.
         *
         * @return the role identifier for "VISITOR"
         */
        public static String visitor() { return VISITOR; }

        /**
         * Retrieves the predefined role representing the "BLOCKED".
         * This role signifies the state where access or permissions
         * are explicitly denied or restricted for the respective entity.
         *
         * @return the role identifier for "BLOCKED"
         */
        public static String blocked() { return BLOCKED; }

        /**
         * Checks if the specified role is valid based on predefined valid roles.
         *
         * @param role the role to be validated; must not be null
         * @return true if the role is valid, false otherwise
         */
        public static boolean isValidRole(@NotNull String role) {
            return VALID_ROLES.contains(role);
        }
    }
}
