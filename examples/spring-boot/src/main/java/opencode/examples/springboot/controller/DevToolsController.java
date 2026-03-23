package opencode.examples.springboot.controller;

import lombok.RequiredArgsConstructor;
import opencode.sdk.invoker.ApiException;
import opencode.sdk.model.AppLogRequest;
import opencode.sdk.model.FormatterStatus;
import opencode.sdk.model.LSPStatus;
import opencode.sdk.springboot.OpenCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devtools")
@RequiredArgsConstructor
public class DevToolsController {

    private final OpenCodeService openCodeService;

    @GetMapping("/lsp")
    public List<LSPStatus> getLspStatus() throws ApiException {
        return openCodeService.api().lspStatus(null, null);
    }

    @GetMapping("/formatter")
    public List<FormatterStatus> getFormatterStatus() throws ApiException {
        return openCodeService.api().formatterStatus(null, null);
    }

    @PostMapping("/log")
    public ResponseEntity<?> appLog(@RequestBody AppLogRequest request) throws ApiException {
        if (request == null || !StringUtils.hasText(request.getService())) {
            return ResponseEntity.badRequest()
                    .body("Missing required field: service");
        }
        Boolean result = openCodeService.api().appLog(null, null, request);
        return ResponseEntity.ok(result);
    }
}
