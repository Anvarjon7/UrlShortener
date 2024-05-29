package de.telran.urlshortener.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;

@RestController
@RequestMapping
//localhost:8080/url/dfsdfsfsdf
public class UrlRedirectController {

    @GetMapping("/{url}")
    public ResponseEntity goToUrl(@PathVariable String url) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("google.com"));
        return new ResponseEntity(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
        //go to db , find by url original url
//        String original = "";
//        return new RedirectView(original);
    }
}
