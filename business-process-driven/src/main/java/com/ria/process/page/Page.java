package com.ria.process.page;

import com.ria.process.ExecutionContext;

public interface Page {

    String getName();

    // Initialize the parameters for the page
    void initialize(ExecutionContext context);

    // Start Loading of data for the Page
    void load(ExecutionContext context);

    // Action to be performed when the data is loaded
    void onLoad(ExecutionContext context);

    void execute(ExecutionContext context);

    PageRenderer render(ExecutionContext context);

}
