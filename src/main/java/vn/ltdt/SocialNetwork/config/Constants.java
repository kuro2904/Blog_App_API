package vn.ltdt.SocialNetwork.config;

import java.util.List;

public class Constants {
    public static final List<String> ALLOWED_CORS_ORIGINS = List.of("http://localhost:3000","http://localhost:5173");
    public static final List<String> ALLOWED_CORS_METHOD = List.of("GET", "POST", "PUT", "DELETE", "OPTIONS");
    public static final String[] PUBLIC_GET_ROUTE = {"/users/**"};
    public static final String[] PUBLIC_POST_ROUTE = {"/auth/**"};
}
