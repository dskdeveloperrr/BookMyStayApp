# BookMyStayApp
## Use Case 7 – Add-On Service Selection

* Introduces optional service attachment to confirmed reservations.
* Uses Map<String, List<AddOnService>> to map services to reservations.
* Supports multiple services per reservation.
* Calculates total additional service cost dynamically.
* Keeps add-on logic separate from booking and inventory logic.
* Demonstrates extensibility using composition-based design.