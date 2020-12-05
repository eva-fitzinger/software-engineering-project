# Traffic System - Meeting 1:



## Traffic Detection+Control

**Responsible: Tobias**

+ Handles the Basics of the traffic, like traffic lights and speed limits.
+ The system provides an interface for the Control system, so that the control system can “override” the traffic handling.
+ The system provides for the Control System the information how many vehicles are at every node and edge.
+ The Subsystem Participants provides for each vehicle the information on which edge and at which point (in the edge) the vehicle is.

**Input:** 

**From participants:**
Vehicle Type
Node and edges
**From Control System**:
Traffic lights Speed limits
Information for information signs
**Output:**
**For participants**:
Traffic lights
Speed limits
Blocked roads (not needed from TD)

**For Control system:**
Number of cars for each edge and nodes.

## Control System:

### **Subsystem “Control System”**

**Responsible: Werner (Control System)**

**Main task:**Collect data, analyze traffic situation, send instruction to other systems

**Input Data:**
**General data:**
Traffic map considering all type of participants
Traffic scenario (“rush hour”, “weekend”, …)

**From participants:**
Traffic load on different roads per type of participant
Time schedules of public transportation;
Actual emergency cars and its planned path (plus infos “destination reached”)

**From Road maintenance:**
Maintenance data; 
**Output Data:** 
**For Traffic Detection+Control:**
Traffic control information (Traffic lights, speed limits, electronic traffic signs); 
**For participants/visualization:**
status of roads (free, traffic load, blocked, accident, …) 

### **Subsystem “Visualization”**

**Responsible: Jakob (Visualization)**

```java
//TODO
```



## Participants:

### Subsystem "Participants"

**Responsible: Martin (Participants)**

**Functionality:** differentiate between participants and intercommunicate with the navigation system

**Input Data:** 
Navigation (next turn, traffic jam, etc.) 
Traffic Control System (traffic lights, speed limits, emergency cars?)(maintenance) vehicle generation
**Output Data:** 
Navigation (position, destination)
Traffic Control System (position via sensors)

### Subsystem "Navigation"

**Responsible: Niklas**

**Functionality:** find the fastest route for a given participant to a target destination with the current restrictions given by the control system.

**Input Data:** 
**Participants System:**
Participant Position
Target Position

**Control System:**
Roadmap
Speed Limits
Traffic Jams
Blocked Roads

**Output Data:**
**Participants System:**
Next Turn

## Maintenance

**Responsible: Eva**

**Functionalities:**

+ make a schedule for repair and closing roads … regular (work them through every defined time)

+ alarm emergency repairs & cleanup after a car accident or thunderstorm … random positions where it occurred → send to control system

+ check with participants that maintenance cars actually arrive, repair and come back.

If I need more work: predefined number of maintenance cars → work out a schedule with those and take cars away when emergency occurs

**Input Data:** 
**participants → detections control:** maintenance car is going to destination/is there/is returning
**Output:** 
**control system:** 
schedule with the regular repairs 
emergency repairs 

**participants:**
create repair vehicles (with destination)







Fragen per E-Mail (Eva):

 - [x]  Where do we get the plan of the city (OpenStreetMaps etc.)? nope :/
