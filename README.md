# Sahaay India
## Help, Problem, Stories Tracker for India
An **Open Source Social Improvement Platform for India**. This product lets users track Help Requests, Problems, Stories around a location. This product aims to be the bridge between People in Need to People who can help, it aims to be the guide to People with Solutions, leading towards problems faced by everyday indians. Its Mission is to solve big problems of india like hunger, medical needs of the poor, Clothing needs of the poor, infrastructure upgrade/repair requests, etc. 

One line of code at a time, One Service at a time, One Electoral Ward at a time. It is a huge unimaginable task. The first step is listing the problems/help-requests/stories so that everyone is at-least aware of them. How can this be achived? *Awareness,Transparency and with everyone's help.*    
  
In India lot of people have smart phones. Smart Phones have entered even the heart of villages. This is a stab at an open source solution for trying to create a tracker which will create awareness and bring transperency to everyday problems.  

This source code is licensed under General Public License v3.0 https://en.wikipedia.org/wiki/GNU_General_Public_License. More information about the licence can be found in [here](License.txt) 

![Sahaay-India](./src/main/resources/images/india.png)

This is the backend for the Platform. This is based on Light4J-Microservices chasis which in turn is based on Undertow Handlers. 
Since this is a free project, the most minimal footprint was of utmost importance. Coding directly to undertow hadlers for high throughput and
 low memory footprint seems like a good idea for now. Running this on cloud should cost as less as possible. Our belief is that running things on cloud thinking just about scalability would still mean that we running in someone else's computer. 
 So lower memory footprint is of utmost importance. Rest4J promises just that. Rest4J is like a chasis for creating microservices. Each Microservices will have its own vertical services built into the chasis like authentication-client. service-discovery mechanism, logging and tracing capture, rate limiting feature,
 circuit breaker at the application level,header-handler, log mask, zoo keeper client, load-balance, external config. Kindly click into this [Link](https://doc.networknt.com/concern/)
 
Following is the high level Architecture for the whole platform.
![Current Architecture](https://user-images.githubusercontent.com/5641222/91639495-a2dfdb00-ea34-11ea-8acd-82c81167118f.png)

[![Join the chat at https://gitter.im/sahaay-india/community](https://badges.gitter.im/sahaay-india/community.svg)](https://gitter.im/sahaay-india/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Currently the roadmap looks like the following:
* Create a ticket of type *Help Required* or *Problem* or *Stories*
* Android App takes the location of the device and asks for users input for what kind of help is needed or the problem faced or proof for the stories.
* The Input can also have file attachment &/ Url. A photo, pdf, (Other safe formats for consumption),Video and Audio can be taken for attachments.
* Users of the App can see the help/requests/problems/stories in their Wards,District,States,All India. This can be displayed either as a List or on the map with more information.
* Since Phone numbers are linked to the KYC of a customer in India, phone numbers can be used as a form of identification if this app is to by some bad actors to do something funny.
* Politicians, Government offices and NGO's in twitter, whatsApp can be notified automatically via the corresponding API's.
* Organizations like police, fire, Indian health ministry, Media house etc who are on twitter will also be notified via a tweet.
* If this gets popular enough that some politicians are in it then we can send them a message directly.
* People of the community can also vote on problems/help/requests/stories. Voting is for priority. We will have 3-4 priority throught the app.
* Problems are listed anonymous. No data linking a user to the problem is stored at back-end. Just in the android local. The android app can have secret URL of some sorts which can be used to edit the problem. Only the User will have that URL on the android device. 
* Action items on the tickets. Any user/groups of user can pick up the problem/request/help and work on it. After the work the users is required to provide evidence of the solution/help given to close the ticket.
* 

This will be a light4j-rest application. This will be fully Asynchornus with the use of Java Fibers. More at https://www.youtube.com/watch?v=fOEPEXTpbJA , http://jdk.java.net/loom/ and https://user-images.githubusercontent.com/5641222/97071026-c7060580-15fa-11eb-8046-d11feedb2039.png , https://user-images.githubusercontent.com/5641222/97071028-c9685f80-15fa-11eb-8a8e-15995a32ecd6.png. 

* This project follows the specification [Collective Code Construction Contract](https://rfc.zeromq.org/spec/42/)
* The corresponding android repository is present [Android App](https://github.com/sunil-kavali/sahaay-android)
* The corresponding play repository is present [IOS App](https://github.com/Ekshunya-India/sahaay-ios)
* The corresponding oAuth2 Server is present at [oAuth-Server](https://github.com/Ekshunya-India/sahaay-security)
* The corresponding cucumber tests is present at [BDD Tests](https://github.com/Ekshunya-India/sahaay-bdd)
* The corresponding sahaay coins is present at[Sahaay Coins](https://github.com/Ekshunya-India/sahaay-coins)

### Running this Project.
##### PreReq:
1. Java 16 JDK. Download from [Java JDK Download](https://jdk.java.net/16/)
1. Maven Install. [Maven Download Page](http://maven.apache.org/download.cgi#)
1. Intellij IDE 2020 EAP Install. [Intellij EAP Download](https://www.jetbrains.com/idea/nextversion/#section=mac)
1. MongoDB Install. [MongoDB Community Server](https://www.mongodb.com/try/download/community)

##### Maven Run:
exec-maven-plugin gives the ability to execute the project. In the IDE maven Window go to Plugins
directory and double click on exec : exec.
                        OR
Run *mvn install exec:exec* from Terminal. 

Shout out : 
1. https://en.wikipedia.org/wiki/Pieter_Hintjens
1. https://oauth.net/2/
1. https://www.mongodb.com/
1. https://www.networknt.com/