# Single Business Process

This is the version of user registration where the process is driven by a single workflow, which can utilise child workflows to do things like send emails, call 3rd party APIs.

There's a central orchestrator, and Temporal takes care of retries and child workflow executions.
