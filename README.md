# qa-autotests-docker

A QA automation test suite for UI and API testing, designed to run in Docker containers using Selenium Grid.

## Tech Stack

- **Java 11**
- **Maven** — build and dependency management
- **TestNG** — test framework
- **Selenium WebDriver 4** — UI/browser automation
- **REST Assured** — API testing
- **Allure** — test reporting
- **Docker / Docker Compose** — containerized test execution
- **Selenium Grid 4** — distributed browser testing

## Project Structure

```
qa-autotests-docker/
├── src/
│   └── test/
│       └── java/
│           └── com/qa/autotests/
│               ├── api/           # API test classes
│               ├── config/        # WebDriver and test configuration
│               └── ui/            # UI test classes
├── Dockerfile                     # Test runner image
├── docker-compose.yml             # Full test environment setup
├── pom.xml                        # Maven project configuration
└── testng.xml                     # TestNG suite configuration
```

## Running Tests

### Locally (requires Chrome installed)

```bash
mvn test
```

### With Docker Compose (recommended)

Run the full test environment including Selenium Grid and Chrome nodes:

```bash
docker-compose up --build --abort-on-container-exit
```

Test results will be written to:
- `target/surefire-reports/` — XML + HTML test reports
- `target/allure-results/` — Raw Allure results

### Generate Allure Report

```bash
mvn allure:report
```

Then open `target/site/allure-maven-plugin/index.html` in your browser.

## Configuration

You can override the default settings using environment variables or Maven system properties:

| Variable / Property | Default | Description |
|---|---|---|
| `BASE_URL` / `-Dbase.url` | `https://www.example.com` | Base URL for UI tests |
| `API_BASE_URL` / `-Dapi.base.url` | `https://jsonplaceholder.typicode.com` | Base URL for API tests |
| `SELENIUM_HUB` / `-Dselenium.hub` | _(empty — local driver)_ | Selenium Grid Hub URL |

Example:

```bash
mvn test -Dbase.url=https://your-app.com -Dapi.base.url=https://api.your-app.com
```
