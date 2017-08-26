# Save-Contact 
Save-Contact https://github.com/jit0434/Save-Contact
Activity-Recogination  https://github.com/jit0434/Activity-Recognition
Snoring-Detection https://github.com/jit0434/Snoring-Detection

## Question No. 1



This assignment has three parts: download a contact file, add data to Phone Contacts using
code (not by Contact software), show the people’s positions with their names on Google
Map(you can only query the Contacts to get the data in this step). (Details below) Detail

Fetch the contact file “contact.txt” from the url:
http://www.cs.columbia.edu/~coms6998-8/assignments/homework2/contacts/contacts.txt

The data will look like

Dan dan@columbia.edu 40010787 116257324

John john@gmail.com 23079732 79145508

Daniel daniel@gmail.com 37985339 23716735

Johnny johnny@gmail.com 40774042 -73959961

Makiyo makiyo@gmail.com 36155618 139746094


Add first column to Name in the Contacts.
Add second column to Email in the Contacts.
Add third column to Mobile Number in the Contacts. (the number will be used as latitude in
the Map)
Add fourth column to Home Number in the Contacts. (the number will be used as longitude
in the Map).

You have to set up API keys in order to use Google Map API. Search on Google for more
help.

Just showing markers on Google Map is not enough to show which person in what place, so I
recommend you to use AlertDialog to show the Name of the person when you tap it.
This assignment has no restriction on what features you must use and also no restriction on
what user interface you design. As long as you can implement those three functions in code,
and demo to me that the results is correct. You are fine.

Hint
In case you cannot find mistakes for certain operations. You need to add those
user-permissions into your AndroidManifest.xml file.
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_CONTACTS"/>
<uses-permission android:name="android.permission.READ_CONTACTS"/>

Basically several buttons in the main user-interface are enough for you to show me the
correct result.

You don’t need to show Contacts inside the application. You can show me it is empty first
and then after add operation, it includes the data in the contact file.
Be sure just show those five people’s positions in the Map



# Reference:
Download txt file
https://github.com/mobilesiri/JSON-Parsing-in-Android/tree/master/app/src/main/java/com/mobilesiri/jsonparsing

##Screenshot

![screenshot_20170827-002344](https://user-images.githubusercontent.com/26283082/29862837-5662c156-8d8b-11e7-96b5-680d3d811def.jpg)

![screenshot_20170827-010644](https://user-images.githubusercontent.com/26283082/29862838-5665eba6-8d8b-11e7-9659-187a5d1b33d1.jpg)

![screenshot_20170827-010655](https://user-images.githubusercontent.com/26283082/29862842-5693f7e4-8d8b-11e7-8b42-e3d992d35933.jpg)

![screenshot_20170827-010751](https://user-images.githubusercontent.com/26283082/29862841-568e67b6-8d8b-11e7-98ef-d21a907d1f63.jpg)

![screenshot_20170827-002217](https://user-images.githubusercontent.com/26283082/29862839-568bd5b4-8d8b-11e7-881b-5854315cccb3.jpg)

![screenshot_20170827-002309](https://user-images.githubusercontent.com/26283082/29862840-568d9be2-8d8b-11e7-9b54-ccc0e6860658.jpg)
