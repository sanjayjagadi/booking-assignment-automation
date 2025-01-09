# booking-assignment-automation framework

## Overview
This project is a robust, 
scalable automation framework for testing RESTful APIs using REST Assured, 
TestNG, and Java. 
It includes features such as extent reporting, 
logging with Log4j2, 
retry mechanism for flaky tests, 
and data-driven testing capabilities.
pojo classes, enhanced serialization and deserialization using jacson and lombok libraries and hmcrest assertions.


## Prerequisites
- Java JDK 11 or higher
- Maven 3.6.3 or higher
- An IDE (e.g., IntelliJ IDEA, Eclipse)

     ##  Automation Setup Instructions
				step 1 - git clone https://github.com/sanjayjagadi/booking-assignment-automation.git
				step 2 - move to repository booking-assignment-automation
				step 3 - git fetch 
				step 4   git checkout master
				step 5 - mvn clean install
       				 step 6 - mvn test

  ##This layered approach offers several benefits:

- Separation of Concerns: Each component has a specific, well-defined responsibility.
- Modularity: Components can be easily updated or replaced without affecting the entire system.
- Reusability: Utility classes and base tests promote code reuse across different test cases.
- Maintainability: Centralized components make updates easier and reduce the risk of inconsistencies.
- Scalability: New tests or functionalities can be added with minimal changes to existing code.

