# Authorization Requirements

---

Based on the ASVS V4.Access Control Requirements, the following requirements are defined:

+ Verify that all user and data attributes and policy information used by access controls cannot be manipulated by end users unless specifically authorized.

+ Verify that the principle of least privilege exists - users should only be able to access functions, data files, URLs, controllers, services, and other resources, for which they possess specific authorization. This implies protection against spoofing and elevation of privilege.

+ Verify that the principle of deny by default exists whereby new users/roles start with minimal or no permissions and users/roles do not receive access to new features until access is explicitly assigned.

The roles should be divided into 2 groups: administrative (Administrator) and business users (Accountant, User). a user can be either from the administrative or business group. A user with an administrative role should not have access to business functions and vice versa.

# Figuring out how Roles and Permissions work in Spring Security

---

## Core Concepts

+ **Authority**: A permission or right to perform an action, usually granted to a role. In Spring Security, authorities are granted to users who have been authenticated, and can be accessed by `UserDetailsService`.
    1. READ_AUTHORITY
    2. WRITE_AUTHORITY
    3. DELETE_AUTHORITY
    4. UPDATE_AUTHORITY

+ **Role**: A collection of authorities. In Spring Security, roles are granted to users who have been authenticated.

Spring security use the hasRole() and hasAuthority() interchangeably. With Spring security 4, it is more consistent and we should also be consistent with our approach while using the hasRole() and hasAuthority() method. Let’s keep in mind the following simple rules.

1. Always add the ROLE_ while using the hasAuthority() method (e.g hasAuthority("ROLE_CUSTOMER")).
2. While using hasRole(), do not add the ROLE_ prefix as it will be added automatically by Spring security (hasRole("CUSTOMER")).

## How is this usually get done?

There are multiple way to design the spring security roles and permissions but one of the most common and flexible way is to build and roles and privileges module around user groups.

### Database Design

+ User Groups: Users are assigned to groups according to the following rule: a user can be assigned to different groups and a group could have several users in it.

+ Entities:
  + Group: Defines group details and relations to users.
  + UserEntity: Associates users with groups. Could be interesting to make it implement `UserDetails` interface.


### Implementation

**Registration**
+ Assign groups to users during registration using DefaultUserService. Admins of the service can assign groups to users.


**Custom UserDetailsService**
+ `CustomUserDetailService` provides user authentication and authorization details, generating a GrantedAuthority list based on user groups.
+ Following, the previous advice, could be interesting to make the original `UserService` of the app implement `UserDetailsService` interface.


**Security Configuration**
+ Use `hasAnyAuthority()` in Spring Security configuration to manage access based on roles. This way we secure a resource of the API from the external world, and his internal logic, assuming that the only way to access to the app resources is through the API.



# References

---

+ [Spring Security Roles and Permissions by JavadevJournal](https://www.baeldung.com/spring-security-method-security) 
+ [Introduction to Spring Method Security by Baeldung](https://www.javadevjournal.com/spring-security/spring-security-roles-and-permissions/) to learn more about the authorization in Spring Boot.