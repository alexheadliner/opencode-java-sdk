package opencode.sdk.springboot;

import lombok.RequiredArgsConstructor;
import opencode.sdk.client.OpenCodeClient;
import opencode.sdk.model.ApiResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenCodeService {

    private final OpenCodeClient openCodeClient;

    public ApiResponse getData(String endpoint) {
        return openCodeClient.get(endpoint);
    }
}
