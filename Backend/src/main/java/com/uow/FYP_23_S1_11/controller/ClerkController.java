package com.uow.FYP_23_S1_11.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(value="/api/clerk", produces = { MediaType.APPLICATION_JSON_VALUE })
@SecurityRequirement(name = "bearerAuth")
public class ClerkController {
   
}
