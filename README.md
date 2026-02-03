# Currency Converter Project - Study Questions

## Basic Understanding
1. What is the overall purpose of this application?
2. How does the application handle different currencies? Where are they defined?
3. What design pattern is used to represent currencies in the code? Why might this be a good choice?

## Architecture & Flow
4. Describe the relationship between `CalculatorWindow`, `CalculatorController`, and `calculator.fxml`. How do they work together?
5. What is the role of the `ForexOperator` class? How does it obtain currency exchange rates?
6. How does the commission calculation work? What are the two factors that determine the final commission amount?

## Technical Implementation
7. Why does the `ForexOperator` class need to set a User-Agent header in its HTTP request?
8. How does the application parse the exchange rate from the response? What potential issues might arise from this approach?
9. What happens if a user tries to convert between the same currency? How is this handled?
10. How does the application ensure that amounts are displayed with exactly two decimal places?

## Error Handling
11. What types of exceptions might occur in this application? How are they handled?
12. What happens if:
    - The user enters an invalid amount?
    - The online service is unavailable?
    - The minimum commission makes the final amount negative?

## JavaFX Specific
13. How are the currency selection ComboBoxes populated?
14. What is the purpose of the `initialize()` method in the `CalculatorController`?
15. How does the application maintain separation between the UI (FXML) and business logic?

## Code Quality, Design & Testing
16. How does the project handle dependency management? What external libraries are used and why?
17. What types of tests would be important for this application?
18. What edge cases should be considered when testing the currency conversion functionality?
