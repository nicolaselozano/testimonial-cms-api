package com.api.csm.services.testimonial;

public class QueryFilter {

    public static String filter(String query){

        if (query == null || query.trim().isBlank()) {
            return "";
        }

        if (query.trim().replace("%", "").isBlank()) {
            return "";
        }

        return query;
    }
}
