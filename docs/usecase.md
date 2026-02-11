# Calculate Change Use Case


## Main flow
1. User introduces amount and selects source and destination currency
2. System uses above data to ask Bank Service for change value in end currency
3. Bank Service provides change value to System
4. System calculates and deducts commission and shows final amount to User


## Alternative flows
2a. If amount is not introduced or incorrect or source and end currencies are the same, System shows error to User. Goto 1.


4a. If Bank Service doesn't work, System shows error to User. Goto 1.
