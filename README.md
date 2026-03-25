# BookMyStayApp
## Use Case 10 – Booking Cancellation & Inventory Rollback

* Enables safe cancellation of confirmed bookings.
* Uses Stack<String> to track rollback history (LIFO order).
* Maintains reservation-to-room-type mapping using HashMap.
* Restores inventory immediately after cancellation.
* Prevents cancellation of non-existent reservations.
* Demonstrates controlled rollback and state recovery logic.