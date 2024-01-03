package com.ku.quizzical.devtools.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ku.quizzical.common.helper.number.IdHelper;
import com.ku.quizzical.devtools.config.DevToolsConfiguration;
import com.ku.quizzical.devtools.util.synthetic.SyntheticDataGenerator;

/**
 * Controller class for Dev Tools related operations
 */
@CrossOrigin
@RestController
public final class DevToolsController {

    private DevToolsConfiguration configuration;

    public DevToolsController(DevToolsConfiguration configuration) {
        this.configuration = configuration;
    }

    @GetMapping("/dev")
    public String test() {
        return "\"Id >> " + IdHelper.nextMockId() + "\"";
    }

    @PostMapping("/dev/backend/generate")
    public String generateSyntheticData() {
        SyntheticDataGenerator.newInstance().generate(this.configuration.getResourcesPath());
        return "\"Id >> " + IdHelper.nextMockId() + "\"";
    }
}
