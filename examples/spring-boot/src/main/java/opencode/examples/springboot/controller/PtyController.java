package opencode.examples.springboot.controller;

import lombok.RequiredArgsConstructor;
import opencode.sdk.invoker.ApiException;
import opencode.sdk.model.Pty;
import opencode.sdk.model.PtyCreateRequest;
import opencode.sdk.model.PtyUpdateRequest;
import opencode.sdk.springboot.OpenCodeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pty")
@RequiredArgsConstructor
public class PtyController {

    private final OpenCodeService openCodeService;

    @GetMapping
    public List<Pty> listPtys() throws ApiException {
        return openCodeService.api().ptyList(null, null);
    }

    @PostMapping
    public Pty createPty(@RequestBody PtyCreateRequest request) throws ApiException {
        return openCodeService.api().ptyCreate(null, null, request);
    }

    @GetMapping("/{id}")
    public Pty getPty(@PathVariable String id) throws ApiException {
        return openCodeService.api().ptyGet(id, null, null);
    }

    @PatchMapping("/{id}")
    public Pty updatePty(@PathVariable String id, @RequestBody PtyUpdateRequest request) throws ApiException {
        return openCodeService.api().ptyUpdate(id, null, null, request);
    }

    @DeleteMapping("/{id}")
    public Boolean deletePty(@PathVariable String id) throws ApiException {
        return openCodeService.api().ptyRemove(id, null, null);
    }
}
