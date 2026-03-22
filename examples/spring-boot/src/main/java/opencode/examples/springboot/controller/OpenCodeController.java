package opencode.examples.springboot.controller;

import lombok.RequiredArgsConstructor;
import opencode.sdk.invoker.ApiException;
import opencode.sdk.model.GlobalHealth200Response;
import opencode.sdk.springboot.OpenCodeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OpenCodeController {

    private final OpenCodeService openCodeService;

    @GetMapping("/health")
    public GlobalHealth200Response getHealth() throws ApiException {
        return openCodeService.getHealth();
    }
}
