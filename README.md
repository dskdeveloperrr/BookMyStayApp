# BookMyStayApp
## Use Case 6 – Reservation Confirmation & Room Allocation

* Confirms booking requests using FIFO processing order.
* Generates unique room IDs for each reservation.
* Uses Set<String> to prevent duplicate room assignments.
* Tracks allocated rooms using HashMap<String, Set<String>>.
* Updates inventory immediately after allocation.
* Prevents double-booking through uniqueness enforcement.
* Ensures consistency between booking confirmation and inventory state.