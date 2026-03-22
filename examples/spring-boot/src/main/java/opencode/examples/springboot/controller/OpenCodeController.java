package opencode.examples.springboot.controller;

import lombok.RequiredArgsConstructor;
import opencode.sdk.model.ApiResponse;
import opencode.sdk.springboot.OpenCodeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OpenCodeController {

    private final OpenCodeService openCodeService;

    @GetMapping("/data/{endpoint}")
    public ApiResponse getData(@PathVariable String endpoint) {
        return openCodeService.getData(endpoint);
    }
}
