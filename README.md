# BookMyStayApp
## Use Case 9 – Error Handling & Validation

* Introduces structured validation before booking processing.
* Uses custom exception InvalidBookingException for domain errors.
* Validates guest name and room type input.
* Prevents invalid inventory access and negative availability.
* Demonstrates fail-fast validation strategy.
* Ensures graceful handling of booking failures without system crash.