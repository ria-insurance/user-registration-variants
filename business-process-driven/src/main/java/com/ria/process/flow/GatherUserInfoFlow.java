package com.ria.process.flow;

import com.ria.process.page.PageConfig;
import com.ria.process.page.PageConfigBuilder;
import com.ria.process.page.StaticPageRender;
import com.ria.process.parameter.ParameterDefinition;
import com.ria.process.parameter.ParameterSourceType;

public class GatherUserInfoFlow {
    public static final PageConfig GATHER_USER_INFO_PAGE =
            new PageConfigBuilder("ria.com", "profile/create")
            .withParameter("USER_ID",
                    new ParameterDefinition("name", ParameterSourceType.CONTEXT, null))
            .withRenderer(new StaticPageRender("/profile/create"))
            .build();

    public static void execute() {

    }
}
