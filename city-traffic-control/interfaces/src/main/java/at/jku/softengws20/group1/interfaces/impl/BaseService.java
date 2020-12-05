package at.jku.softengws20.group1.interfaces.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public abstract class BaseService {
    public static final String HOST_URL = "http://localhost:8080/";

    protected final String baseUrl;
    protected final RestTemplate restTemplate = new RestTemplate();

    protected BaseService(String url) {
        baseUrl = concatUrl(HOST_URL, url);
    }

    protected void post(String url, Object object) {
        restTemplate.postForEntity(concatUrl(baseUrl, url), object, null);
    }

    protected <T> T successBodyOrNull(String url, Class<T> type) {
        ResponseEntity<T> response = restTemplate.getForEntity(concatUrl(baseUrl, url), type);
        return successBodyOrNull(response);
    }

    protected <T> T successBodyOrNull(ResponseEntity<T> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }

    private static String concatUrl(String a, String b) {
        a = removeTrailingSlash(a);
        b = removeLeadingSlash(b);
        return a + "/" + b;
    }

    private static String removeTrailingSlash(String a) {
        if (a.endsWith("/")) {
            a = a.substring(0, a.length() - 1);
        }
        return a;
    }

    private static String removeLeadingSlash(String a) {
        if (a.startsWith("/")) {
            a = a.substring(1, a.length());
        }
        return a;
    }
}
