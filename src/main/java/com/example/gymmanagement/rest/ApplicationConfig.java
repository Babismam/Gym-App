package com.example.gymmanagement.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
public class ApplicationConfig extends Application {
    // Αυτό λέει στο WildFly: "Όλα τα REST API ξεκινούν από /api"
}
