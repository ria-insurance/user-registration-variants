package com.ria.process.flow;

import com.ria.process.page.PageConfig;
import com.ria.process.page.PageConfigBuilder;
import com.ria.process.page.StaticPageRender;
import com.ria.process.parameter.ParameterDefinition;
import com.ria.process.parameter.ParameterSourceType;
import com.ria.process.task.TriggerType;

public class VerifyPhoneNumberFlow {
    public static final PageConfig SEND_OTP_PAGE =
            new PageConfigBuilder("ria.com", "phone/verify/start")
            .withParameter("USER_ID",
                    new ParameterDefinition("user_id", ParameterSourceType.CONTEXT, null))
            .withRenderer(new StaticPageRender("/otp/send"))
            .withTask("symphony.base", "aws.otp.generate", TriggerType.ON_SUBMIT,
                    "SYMPHONY", null)
            .build();

    public static final PageConfig VERIFY_OTP_PAGE =
            new PageConfigBuilder("ria.com", "phone/verify/otp")
            .withParameter("USER_ID",
                    new ParameterDefinition("user_id", ParameterSourceType.CONTEXT, null))
            .withRenderer(new StaticPageRender("/otp/verify"))
                    .withTask("symphony.base", "aws.otp.verify", TriggerType.ON_SUBMIT,
                            "SYMPHONY", null)
            .build();

    public static final PageConfig VERIFY_OTP_SUCCESS_PAGE =
            new PageConfigBuilder("ria.com", "phone/verify/success")
            .withParameter("USER_ID",
                    new ParameterDefinition("user_id", ParameterSourceType.CONTEXT, null))
            .withRenderer(new StaticPageRender("/verify/complete"))
            .build();

    public static final PageConfig VERIFY_OTP_FAILED_PAGE =
            new PageConfigBuilder("ria.com", "phone/verify/failed")
            .withParameter("USER_ID",
                    new ParameterDefinition("user_id", ParameterSourceType.CONTEXT, null))
            .withRenderer(new StaticPageRender("/verify/complete"))
            .build();
}
