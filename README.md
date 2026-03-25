# BookMyStayApp
## Use Case 12 – Data Persistence & System Recovery

* Saves room inventory state to a text file.
* Restores inventory automatically during application startup.
* Uses plain-text serialization format (roomType=count).
* Handles missing or corrupted persistence files safely.
* Demonstrates file-based persistence without database usage.
* Enables recovery of system state after restart.