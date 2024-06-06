package de.telran.urlshortener.serviceTest;

import de.telran.urlshortener.model.entity.binding.UrlBinding;
import de.telran.urlshortener.model.entity.subscription.Status;
import de.telran.urlshortener.model.entity.subscription.Subscription;
import de.telran.urlshortener.model.entity.user.Role;
import de.telran.urlshortener.model.entity.user.User;

public class ServiceTestData {
    public static final User USER1 = User.builder()
            .id(null)
            .email("1@1.ua")
            .password("1")
            .role(Role.USER)
            .firstName("A")
            .lastName("B")
            .build();
    public static final User USER2 = User.builder()
            .id(null)
            .email("2@2.ua")
            .password("2")
            .role(Role.USER)
            .firstName("A2")
            .lastName("B2")
            .build();
    public static final User USER3 = User.builder()
            .id(null)
            .email("3@3.ua")
            .password("3")
            .role(Role.USER)
            .firstName("A3")
            .lastName("B3")
            .build();
    public static final User USER4 = User.builder()
            .id(null)
            .email("4@4.ua")
            .password("4")
            .role(Role.USER)
            .firstName("A4")
            .lastName("B4")
            .build();

    public static final UrlBinding URLBINDING1 = UrlBinding.builder()
            .id(null)
            .user(USER1)
            .uid("11111")
            .pathPrefix("bmw")
            .baseUrl("")
            .originalUrl("https://mail.google.com")
            .count(0L)
            .build();

    public static final UrlBinding URLBINDING2 = UrlBinding.builder()
            .id(null)
            .user(USER2)
            .uid("22222")
            .pathPrefix("bmw")
            .baseUrl("")
            .originalUrl("https://maps.google.com/maps")
            .count(150L)
            .build();
    public static final UrlBinding URLBINDING3 = UrlBinding.builder()
            .id(null)
            .user(USER3)
            .uid("33333")
            .pathPrefix("bmw")
            .baseUrl("")
            .originalUrl("https://gemini.google.com")
            .count(400L)
            .build();
    public static final UrlBinding URLBINDING4 = UrlBinding.builder()
            .id(null)
            .user(USER4)
            .uid("44444")
            .pathPrefix("bmw")
            .baseUrl("")
            .originalUrl("https://news.google.com")
            .count(100L)
            .build();
    public static final Subscription SUBSCRIPTION1 = Subscription.builder()
            .id(null)
            .user(USER1)
            .status(Status.PAID)
            .build();
    public static final Subscription SUBSCRIPTION2 = Subscription.builder()
            .id(null)
            .user(USER2)
            .status(Status.PAID)
            .build();
    public static final Subscription SUBSCRIPTION3 = Subscription.builder()
            .id(null)
            .user(USER3)
            .status(Status.PAID)
            .build();
    public static final Subscription SUBSCRIPTION4 = Subscription.builder()
            .id(null)
            .user(USER4)
            .status(Status.PAID)
            .build();
}
