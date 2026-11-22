# Development Rules

## Mandatory MCP Usage

- Treat the MCP servers configured in the active Codex `config.toml` as the preferred external tools for the domains listed below.
- **For every user request that substantively concerns a configured MCP domain, call at least one relevant tool from the corresponding MCP server before providing a final answer or making domain-specific code/configuration changes.** This applies to explanations, investigations, design proposals, code reviews, troubleshooting, implementations, and upgrades, not only to code generation.
- If a request spans multiple configured domains, use an appropriate MCP server for **each** relevant domain (for example, Spring Boot + AWS requires Springdocs/Context7 **and** AWS Documentation; Docker + AWS requires Docker **and** AWS Documentation).
- Merely having an MCP server configured, listing its tools, or reading `AGENTS.md` does **not** count as using that MCP server. Invoke a relevant MCP tool and inspect its result.
- Do not replace a required MCP call with model knowledge or a generic web search without first attempting the applicable MCP tool.
- An MCP server provides tools, not automatic guarantees of correctness. Validate returned information and the resulting changes against the repository and tests.
- Never claim that an MCP tool was used unless a tool invocation actually occurred. Never invent tool names, results, version matches, or documentation references.

## MCP Server Routing (from config.toml)

### `openaiDeveloperDocs` — OpenAI Developer Documentation

- **Mandatory for:** Questions and changes involving OpenAI APIs, Codex, Codex CLI/IDE behavior, MCP integration or configuration, Agents SDK, model/API capabilities, and other OpenAI developer features.
- Consult the OpenAI Developer Documentation MCP before giving instructions or making related configuration changes.
- Distinguish documented behavior from environment-specific behavior that still requires local verification.

### `aws_documentation` — AWS Documentation

- **Mandatory for:** AWS architecture, implementation, Terraform definitions involving AWS resources, configuration, troubleshooting, pricing/limits where documentation is relevant, security/IAM, deployment, and AWS service upgrades.
- This includes, as applicable: API Gateway, Lambda, DynamoDB, RDS/MySQL, ECS/Fargate, S3, Step Functions, EventBridge, SNS, CloudWatch, CodeBuild, CodePipeline, and other AWS services.
- Consult AWS Documentation MCP before answering or modifying AWS-related implementation/configuration.
- Prefer current official AWS documentation and verify region, service, runtime, engine version, and other relevant constraints.
- AWS Documentation MCP explains documented service behavior; it does **not** by itself verify the live state of the user's AWS account. Do not claim deployed resources, permissions, or account settings were checked unless an appropriate authorized tool actually checked them.

### `context7` — Library / Framework Documentation

- **Mandatory for:** Python, Django, Django REST Framework, Java libraries, MySQL libraries/drivers, and third-party framework/library APIs, version-sensitive behavior, dependencies, and migrations when documentation is needed for the request.
- For Java/Spring work, use Context7 or Springdocs as required below; use Context7 additionally when another library's APIs are involved.
- Resolve the relevant library and request documentation for the version used by the target module whenever possible.
- If exact-version documentation cannot be found, disclose that limitation rather than assuming the latest version is compatible.

### `springdocs` — Spring Documentation

- **Mandatory for:** Spring Boot, Spring Framework, Spring MVC, Spring Security, Spring Data, Spring Batch, Spring configuration, and Spring-related dependency or migration questions.
- For Spring-specific tasks, invoke Springdocs or Context7 **at minimum once**; prefer Springdocs for Spring documentation and use Context7 as a second source when useful.
- Verify Spring Boot, Spring Framework, Java, and dependency versions from the relevant module's Maven configuration before using version-dependent APIs.
- Do not confuse documentation for one Spring major/minor version with the project's version.

### `docker` — Docker / Docker Compose

- **Mandatory for:** Dockerfiles, images, containers, Docker Compose, networks, volumes, build/run behavior, and Docker troubleshooting.
- Attempt a relevant Docker MCP tool before answering or changing Docker-related configuration. Prefer read-only inspection tools first.
- If the Docker MCP exposes no tool suitable for a documentation-only question, state that limitation and verify the answer using an appropriate authoritative source; do not run unrelated or destructive Docker commands just to satisfy this rule.
- Require explicit user approval before any destructive or production-impacting action, such as removing containers/images/volumes, deleting data, pruning, or restarting critical services.

## Required Workflow for Each Relevant Request

1. **Identify domains:** Determine which of the configured MCP servers apply to the user's actual question or task. Do not call unrelated servers.
2. **Inspect project facts:** Read relevant files before assuming versions or architecture:
   - Java/Spring: `pom.xml`, parent/BOM, Maven modules, build profiles, and Java configuration.
   - Python/Django: `requirements*.txt`, `pyproject.toml`, lockfiles, Dockerfiles, CI files, and Python runtime configuration.
   - Docker: `Dockerfile*`, Compose files, environment/configuration, and relevant container state when authorized.
   - AWS: Terraform/provider/version constraints, IaC modules, deployment files, and stated region/runtime; distinguish repository definitions from actually deployed resources.
3. **Invoke MCP:** Call the appropriate MCP tool(s) for each applicable domain **before** a domain-specific answer, implementation, or configuration change. Use the tool result, not just the tool's availability, as evidence.
4. **Check compatibility:** Prefer documentation matching the actual installed/declared versions. If versions differ across modules, use the target module's versions; never silently assume or upgrade to the latest.
5. **Implement and verify:** Preserve existing dependencies and versions unless an upgrade is requested. Run relevant compile/build, tests, linting, or validation commands when possible. Explain anything that could not be tested.
6. **Report MCP usage:** Include a brief MCP verification note in the final response for each relevant domain (see Reporting).

## Failure Handling

- If a required MCP server is missing, disabled, fails to start, times out, returns an error, or offers no suitable tool, **do not silently skip it**.
- Report: (1) MCP server name, (2) what operation was attempted, (3) why it did not succeed or why no suitable tool exists, and (4) which alternative source or verification was used.
- If other trusted tools or official documentation permit safe progress, continue while clearly marking any unverified compatibility details.
- If the missing verification makes the request unsafe or materially unreliable, do not guess; explain the blocker and provide the safest supported partial result.
- Do not fabricate an MCP execution or source citation to satisfy these rules.
- If the user expressly instructs that no tools or external access be used for a particular request, honor that constraint and explain any resulting verification limits.

## Reporting

For each response involving a configured MCP domain, briefly report:

- **MCP:** server name(s) actually called; or `Unavailable` with the reason.
- **Checked:** documentation topic/version or tool result used, including any version mismatch.
- **Verification:** applicable project version(s), validation/test outcome, and any unresolved uncertainty.

Keep the report concise for simple questions; expand it for implementation, upgrades, incident investigation, or architecture decisions.
