#Sahaay Back End Architecture.
This is basically going to be a Light4J-rest chasis using the Loadbalancer and the service discovery of the inbuilt light4j chasis.

![Bare Light4J-Rest Service Chasis](https://user-images.githubusercontent.com/5641222/90927186-30f30a80-e412-11ea-9d18-1de0811f5602.png)

The following are basic requirements for this Application:
* Should have Authentication and Authorization with the use of built in Auth-Client.
* A User is able to view all the tickets in a location without loggin in.
* A User has to login for any kind of update to the ticket or create a new ticket etc.
* A ticket can be of 3 types, 1 - Problem, 2 - Help Needed and 3 - Stories.
* A ticket is always associated to the location. A location for the ticket is a must.
* Ticket of type problem is not associated to any user. In the sense no body in the system or outside can track the person who created the ticket.
* Help has mandatory person associated to it. Sometime It can also be a person who is not registered in the system. We need to come up with an "Anonymus Person Object" for them. 
* Association with the person identity is optional for Stories. So that there can be anonymus stories told.
* Auto Notification is an important part of the system which Theoretically can be its own service but for now will be part of this service.
* To start with we will integrate with just twitter and WhatsApp.
* Tickets on to itself

Following is the high level architectural diagram.
![High Level Architecture Diagram](https://user-images.githubusercontent.com/5641222/91650845-69de4f80-eaa2-11ea-8688-0287a26e6cba.png)

Following are the important Domain Driven Models in the Sahaay Backend Services
1. Ticket
    1. Id : UUID.
    1. Description : String
    1. Created Date : DataTime
    1. Expected End Date : DateTime 
    1. Actual End Date: DateTime
    1. Sahaay-Location : CustomObject
    1. Priority-Level : ENUM with 4 level
    1. Ticket-Type : ENUM with 3 Types
    1. Current-Stage : Ticket Open, Ticket Campaigning, Ticket In Progress, Ticket Vote of Confidence, Ticket Closed, Ticket Flagged Down, Ticket Not Relevant for the location
    1. Visibility : int(Will correspond to the Ticket Heirarchy config value)
    1. User : Assigned User Id.(Optional for Stories and Problem)
    1. Upvotes : int
    1. Downvotes : int
    1. Sahaay-Updates : Custom Object with Attachment
    1. List<Comments> : Comments Custom Object. 
   
2. User (Information coming from the Authentication Server)
    1. User-Id : UUID 
    1. User-Type : ADMIN("admin"),EMPLOYEE("employee"),CUSTOMER("customer"),PARTNER("partner")
    1. Avatar Url : URL
   
3. Updates :
    1. Title : String
    1. Description : String
    1. List<Attachments> : List<URL> (Audio/Video/Documents/Photos list)
    1. UserId : String (Optional)
    1. List<Comments> : List<String>
    
4. Sahaay-Location :
    1. Latitude : double
    1. Longitude : double
    1. Other information from google API which will help to determine the Association to the different hierarchy configured
        
Since we follow a Contract First approach the first thing that we will have is an open API Yml file which can be
used to create a Scaffold light4j Rest project from the corresponding yml file.