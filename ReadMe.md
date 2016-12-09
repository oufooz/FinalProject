**Team Android 8.0 Oreo**

Title: Swiss Army App

Team Members: Andrew Chan , Omar Ouf

Brief Description:
	The Swiss Army App is an app that triggers user-assigned tasks/actions when in proximity of the pre-defined location.
	The app runs a GPS Service that constantly runs in the background and checks the current location against saved locations.
	The two tasks that have been added to this app are the MapDirection and Reminder tasks. The MapDirection task will bring up Google Maps
	and set the source/origin location to the current location, and the destination location to the pre-set location that was defined 
	when assigning the task. The Reminder app shows a lock-screen notification and an AlertDialog, displaying the user-defined String,
	when in proximity of the saved location.

	
Libraries:
	Used GoogleAPI's for mapfragments.
	Used FirebaseAuthentication for Login/Creation of Activity.
	