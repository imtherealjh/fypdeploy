package com.uow.FYP_23_S1_11.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/user", produces={MediaType.APPLICATION_JSON_VALUE})
public class UserController {
    
}
