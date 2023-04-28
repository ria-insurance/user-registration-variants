package com.ria.process.flow;

import com.ria.process.page.PageConfig;
import com.ria.process.page.PageConfigBuilder;
import com.ria.process.page.StaticPageRender;
import com.ria.process.parameter.ParameterDefinition;
import com.ria.process.parameter.ParameterSourceType;
import com.ria.process.task.TriggerType;

public class RegisterUserFlow {
    public static final PageConfig BETO_HOME_PAGE =
            new PageConfigBuilder("beto.ria.com", "/")
                    .withParameter("USER_FULL_NAME",
                            new ParameterDefinition("name", ParameterSourceType.URL, null))
                    .withParameter("USER_HBA1C",
                            new ParameterDefinition("hba1c", ParameterSourceType.POST, null))
                    .withParameter("SIGNATURE",
                            new ParameterDefinition("hash", ParameterSourceType.POST, null))
                    .withRenderer(new StaticPageRender("/home"))
                    .withTask("ria.partner.beto", "otp.generate", TriggerType.ON_LOAD,
                            "CAMUNDA", null)
                    .withTask("symphony.health", "health.data.save", TriggerType.ON_LOAD,
                            "SYMPHONY", null)
                    .build();
}
