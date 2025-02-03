# Speedtest Network Reports

## Project author
```Vladislav Nikiforov```

```nvladislav@gmail.com```

## Overview

Speedtest Network Reports is a console application designed for periodic internet speed testing. 
The application automatically navigates to the [Speedtest](https://www.speedtest.net/) website, 
initiates a speed test, and saves the results to a file. The project is developed in Java and uses the
Gradle build system for dependency management and build processes. 
It leverages the [Selenide](https://selenide.org/) framework to automate interactions with the web page,
providing a convenient API for browser operations.

## Features

- **Periodic Internet Speed Tests**: Conducts speed tests at a specified interval.
- **Result Logging**: Saves test results, including download and upload speeds, test time, and result URL, to a text file.
- **User-Controlled Stop**: Allows users to stop the tests with a command.

The application runs in the background and can be used to monitor internet connection quality over an extended period.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Gradle
- Chrome browser installed
- Intellij IDEA is recommended

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Vlad7007/speedtest-network-reports.git
   ```

2. Navigate to the project directory:
   ```bash
   cd speedtest-network-reports
   ```

3. Build the project using Gradle (ShadowJar is needed to support user input in a regular Windows terminal):
   ```bash
   gradlew.bat shadowJar
   ```

### Usage

1. Run the application:
   ```bash
   java -jar build/libs/speedtest-network-reports-1.0-SNAPSHOT-all.jar
   ```

2. The application will start conducting speed tests every 10 minutes by default. 
3. You can stop the tests by typing 'stop' in the console.

### Configuration

- The interval for speed tests can be adjusted by modifying the `PERIOD_IN_MINUTES` constant in the `MakeSpeedtestReports.java` file.

### Logging

- Test results are logged in `src/main/resources/speedtest-report.txt`.