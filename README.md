# Get-A-Ride
This application operates between a driver and the user who is looking out for a ride. Main components include internet availability and google maps. The app creates a user profile based on the user type. The driver has to mention the rate charged per kilometer. Once the profile is created the user selects a route(pickup-drop off point) and time, a notification is sent to the drivers who are registered on that route. Once the driver accepts it, the user selects a driver from the list of accepted notifications. The user and driver can then coordinate among themselves via message or call(this option will be available inside the app). After completion of the ride, user pays the fare and driver acknowledges that payment has been received.

Theme:
Location-Based Application

Functional requirements and Architectural analysis
1-	Locating start and end point on google maps. Displaying the route and distance.
2-	Calculating total fare based on distance and fare per kilometer.
3-	User profile data. Driver route and CNIC number. All of the data will be stored in Databases.
4-	Text/call the driver.
5-	Notification at background for booking confirmation and available drivers.
6-	We can provide our user data through content provider.
7-	Rate customer and driver.

Activity flow
1-	Signup screen for new users. Otherwise a main homepage to view ride history or book a ride.
-	Book a ride page include one input start and endpoint of journey.
-	Pickup time.
-	Google map displaying route and the endpoints of journey.
-	Total fare, expected time of journey and distance.
2-	Notification can be accessed through the phone’s notification list.
3-	After ride completion a page to rate user experience.

