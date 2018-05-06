# Smart-Parking--imgrecog-Java-Android
This is an application that simulates a smart approach to the ever lasting parking problem.
In stead of searching minutes for a parking spot, just take out your phone and see which spot is free and which is not by the color
of the parking slot (red-taken, green-free).

Idea: 
The mobile app shows a map of the parking place. The backend, sends images with each parking slot to the Microsoft's Azure Vision
API. From the cloud a JSON is returned. The JSON contains the `tags` element. The tags array must contain `car` to make sure that in the
image is a car. This is sent to the database for the `availability` attribute of the table, 0 = no car, 1 = car;

For using the cloud API, a key is needed and provided after the login. For a free trial, the key will only last 1 week.

Implementation:
The project is divided into 2 applications a Server-side and an Android Frontend. The two applications are linked via a
socket TCP connection. 
The frontend app is completely synced with the backend, this means that if the connection with the server is lost, or the
server is down, when the backend restarts, the frontend will get immediately the data, no input from the user is needed for refresh.

There are 2 databases used. There is a Mysql db on the main computer, and another one on the android frontend, to make sure there
will also be something to be shown on the mobile. The table is called `parking` and contains 3 attributes: `id,availability, name`.
Obviously the 2 databases contain exactly the same thing, and are always synced to one-another.


