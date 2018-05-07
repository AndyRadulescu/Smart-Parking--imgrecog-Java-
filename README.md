# Smart-Parking--imgrecog-Java-Android
`Note: In order for this application to work, a KEY needs to be provided from the Microsoft's Azure Computer Vision API, by logging in.
The free trial consists of only one week. So this is why a personal key is needed." `

````
Below I show you in detail what needs to be done in order for the app to work properly.
````
`In the frontend App there needs to be provided the exact Ip of the server (I show you bellow).`

`The frontend only works in the same wi-fi as the backend. If in anoter wi-fi or not conected, a prompt is shown.`

`Idea:` 

This is an application that simulates a smart approach to the ever lasting parking problem.
In stead of searching minutes for a parking spot, just take out your phone and see which spot is free and which is not by the color
of the parking slot (red-taken, green-free).

The mobile app shows a map of the parking place. The backend, sends images with each parking slot to the Microsoft's Azure Vision
API. From the cloud a JSON is returned. The JSON contains the `tags` element. The tags array must contain `car` to make sure that in the
image is a car. This is sent to the database for the `availability` attribute of the table, 0 = no car, 1 = car;

For using the cloud API, a key is needed and provided after the login. For a free trial, the key will only last 1 week.

`Implementation:`

The project is divided into 2 applications a Server-side and an Android Frontend. The two applications are linked via a
socket TCP connection. 
The frontend app is completely synced with the backend, this means that if the connection with the server is lost, or the
server is down, when the backend restarts, the frontend will get immediately the data, no input from the user is needed for refresh.

There are 2 databases used. There is a Mysql db on the main computer, and another one, SqlLite, on the android frontend, to make sure there
will also be something to be shown on the mobile. The table is called `parking` and contains 3 attributes: `id, availability, name`.
Obviously the 2 databases contain exactly the same thing, and are always synced to one-another.
`The sql script to create the db is provided.`
The table has only 6 entries because there are only 6 parking spots.

For scalability, in the backend, Maven has been used. This makes the project extremely easy to import. No need to look for jars online.
`Hibernate` has been used as an ORM for mapping the database.
The backend uses a TCP socket connection to communicate with the android client. This is raw, and no api was used to facilitate an easier 
implementation.

`The database must run on port 3306, user "root", password: "1q2w3e"`

The backend looks in the images folder `which has not been provided in this app. Manually an "images" folder must be created in: src/main`
ad there images with parking spots with cars and no cars must be placed.
From the images folder, 6 RANDOM images are picked and sent to the Microsoft API.

As I sad before, 6 entries are in the database. This means that 6 images must be sent to the Microsoft API. To make this possible, 
an ExecutorService has been used with "invokeAll". This creates 6 threads that send the images to the api.
`When I tested out the application, to send and receive the response it took around 20 seconds.` So the database will be refreshed every
20 seconds.

On the android frontend the Callable interface has been used, to return the message in the desired Activity.
A new CustomView has been created, to draw the scene onto it.
`At the moment only SECTION 1 works`.

`The application only works in the same wi-fi as the backend.`

`Tweaks`:
Basically there are 3 tweak that must be done on order for the application to work:

In `Parking Server2/src/main/server/recognition/RecognitionMain.java` look for the "subscriptionKey" and add your Microsoft Azure
Computer Vision Key.

Create the `images` folder in the backend `Parking Server2/src/main`. In this folder all the images must be placed. In the photo there must be 
only one car, or the empty parking spot. `The application will not work if panoramic photos of the parking lot will be taken.`

Last thing: in the frontend in the `android perspective` go to `Smart-Parking--imgrecog-Java-\ParkingApp\app\src\main\java\server\SavedItems.java` 
and search the field `IP`. Make sure to insert the backend's IP and that the emulator or mobile device is connected to the same wi-fi.

`Final considerations:`
For this application I used IntelliJ and Android Studio and I encourage you to do the same, the import process is very very easy!
For the backend click on import jpa after the import(pop up: down left hand-side).
In the android client, don't upgrade the gradle plugin.

