# key points

## Spring

Spring offers a container often referred as Spring Application Context that creates, and manages application components.
These components are called `beans` and are wired together inside the spring application context to complete the application. 

## MVC:

- Model
    - Use JAVAX Validation for restricting objects properties
- Controller:
    - Web side GET POST PUT DELETE TRACE
- Template:
    - JSP, Thymeleaf
- Repository:
    - Interface for DataBase protocol
    - Extend CRUD from org.springframework.data.repository.CrudRepository
- Service:
    - Deal with Data fetching (Implement Interface)
- Security:
    - HttpSecurity for securing http request
    - AuthenticationManagerBuilder for securing users connection
        - InMemory
        - JDBC DataSource
        - LDAP
        - UserDetail Service with a method named loadUserByUsername(String username)
- Properties:
    - Profiles (spring.profiles)
    - Configuration Bean (`@ConfigurationProperties(prefix="taco.spring")`)
---

- Think of the domain (Model, Object, etc)
### Validate the form with javax validation tools (Java Bean Validation: JSR-303)
To apply validation in Spring MVC, you need to
- Declare validation rules on the class that is to be validated: specifically, the
Taco class.
- Specify that validation should be performed in the controller methods that
require validation: specifically, the DesignTacoController’s processDesign()
method and OrderController’s processOrder() method.
- Modify the form views to display validation errors.

## 
- Make more test (test for the API, the logic of the object, UnitTest)

## Security

Security is a key point for any application, and Spring takes it to its core by offering many security tools via Spring Security.

- Securing the user authentication:\
One can enable user authentication by creating users in memory, or by using a jdbc store.
In our case, since we have a service to fetch user information, we will use this same service and spring `AuthenticationManagerBuilder` to have such a user authentication

- When using jdbc data source for user authentication, one should make sure to have the standard schema Spring require in its database.
```java
public static final String DEF_USERS_BY_USERNAME_QUERY =
"select username,password,enabled " +
"from users " +
"where username = ?";
public static final String DEF_AUTHORITIES_BY_USERNAME_QUERY =
"select username,authority " +
"from authorities " +
"where username = ?";
public static final String DEF_GROUP_AUTHORITIES_BY_USERNAME_QUERY =
"select g.id, g.group_name, ga.authority " +
"from groups g, group_members gm, group_authorities ga " +
"where gm.username = ? " +
"and g.id = ga.group_id " +
"and g.id = gm.group_id";

```
It is possible to overwrite these queries.\
For security reasons, the user password will be store in the database as hashed password. So when spring
will read a new password, it should hash it first before making the comparison.\
Spring offers different hashing methods.
````java
auth.passwordEncoder(new StandardPasswordEncoder("53cr3t"));
````


- One could also LDAP server for the user authentication (In AWS, that's let me think of aws cognito)

To do so:
- Create a java class and `extend WebSecurityConfigurerAdapter`. This class will override the method `configure` of `WebSecurityConfigurerAdapter`.
`configure takes an AuthenticationManagerBuilder in parameter`


Spring offers a `UserDetails` Class that contains interesting methods and properties that define what is a general user.

Using the userDetailsService, one rule is that it should **never return null**


### Securing web requests
Problem: How to lock some pages to unlogged users ?
Solution: Secure some request

The same class `WebSecurityConfigurerAdapter` provide another `configure` method that takes `httpSecurity parameter`.

Things you can do with HttpSecurity
- Requiring that certain security conditions be met before allowing a request to
be served
- Configuring a custom login page 
- Enabling users to log out of the application
- Configuring cross-site request forgery protection

Example
```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/design", "/orders")
        .hasRole("ROLE_USER")
        .antMatchers(“/”, "/**").permitAll()
    ;
}
```
Through the `access method` one can use Sprin Expression Language (SpEL) to extend the security constraint.

Ecample:

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
            .antMatchers("/design", "/orders")
                .access("hasRole('ROLE_USER') && " +
                    "T(java.util.Calendar).getInstance().get("+
                    "T(java.util.Calendar).DAY_OF_WEEK) == " +
                    "T(java.util.Calendar).TUESDAY")
            .antMatchers(“/”, "/**").access("permitAll")
    ;
}

```

**Custom Login page**

By default Spring listen to `/login`, however one can customize it.
The customization still uses the http security object passed to the configuration method overwritten from the WebSecurityConfigurerAdapter
```java
.and()
        .formLogin()
          .loginPage("/login")
```
----

## Knowing your users

When we create a web app it's important at some point to have some information concerning the user that is logged in
This information can for example be used to improve the user experience in the app, or it can be used to execute more business activities.

Different ways allow us to do so.

> In fact when we deal with security, we have an interaction of objects
> A request to access B: A is a Subject and B is an Object
> In this same way there is an hierarchy for the Subject:
> Subject --> Principal --> User [to know more](https://stackoverflow.com/questions/4989063/what-is-the-meaning-and-difference-between-subject-user-and-principal)
> The information can be fetched by looking at the objects that are linked to the security context existing in the application
>
* Inject a Principal object into the controller method
* Inject an Authentication object into the controller method
* Use SecurityContextHolder to get at the security context
* Use an @AuthenticationPrincipal annotated method

We will talk about two of them

The easiest way probably is by using an @AuthenticationPrincipal annotation

```java
@PostMapping
public String processOrder(@Valid Order order, Errors errors, 
SessionStatus sessionStatus,
@AuthenticationPrincipal User user) {
    if (errors.hasErrors()) {
        return "orderForm";
    }
    order.setUser(user);
    orderRepo.save(order);
    sessionStatus.setComplete();
    return "redirect:/";
}
``` 
Here the security related code is only put at the annotation

The other way is more general that it can be used anywhere in the code, not only in a controller method
It uses the security context directly to fetch the object. Note that the returned object is of type `Object` so 
we need to cast it to the expected object, here `User`
```java
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
User user = (User) authentication.getPrincipal();
```