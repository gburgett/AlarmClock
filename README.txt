Hey Dan,

This project is something I created on the side when I got sick of the terrible computer alarm clocks on the internet.  I wanted an alarm clock to play Pandora to wake me up.  How hard is that right?  I couldn't find an alarm clock to do  it so I made my own.

Anyways, I refactored it and heavily commented it and am giving it to you because you are learning how to program in  Java for work.  I figured the best thing for you would be to have some heavily commented examples of good programming  practice, so I didn't cut any corners.  I did everything in this project in the best practices that I know, using all  my tricks and techniques and not skimping on anything.  I also wrote a buttload of comments.  I even wrote a whole  bunch of (not entirely necessary) Unit Tests for you to look over to see how it's done.  Basically I want this to be  a sort of living reference project for you to look at when you want to know, for example, "how do I setup a list of  FooObjects sorted alphabetically by FooString?"  

You can start it up in Netbeans, run it with the debugger attached, set breakpoints and step through it as much as you  want.  If you want you could even add features to it and I would critique them for you.  Please do play with this and  learn from this, because if you're getting paid to learn programming then that's a sweet gig and I want to help you do  the best you can at it.

Let me define a couple terms here.  Throughout the comments you'll see me say "this class Is Something" or "this class Does Something".  Basically what I mean is, when you think of a Class, you should ask yourself the question "does this class represent something?  Or does it Do Something?".  When you make that distinction you'll be able to see the difference between Models and Controllers.  Views are kinda special, they could be considered classes that "Do Something" because they display objects that "Are Something", but I'd prefer to distinguish them by saying the "Display Something".  Anyways, the answer to that question is the heart of the idea behind MVC.

OK so here it is, the Breakdown:

alarmclock.view package
	This package is all about displaying the data.  There's not a whole lot to display but it's kinda complicated cause it's Swing UI framework.  Basically Swing UI is lame, but it's built in to java so you use it sometimes cause you gotta.  Also, because it's Swing UI these classes are also Controllers.  You should google Model-View-Controller (MVC) because that's the way programs are done nowadays.  The old way of WinForms and Swing UI is dying out.
	All these classes Display Something, the MainFrame also Does Something.
	
	
	* MainFrame.java
		** This is the central View and Controller of the program.  It initiates all the actions by responding to button presses and junk, and either displays or has containers to display the data.  It also has forms to input new data (namely, when you want the alarm to go off and what you want to execute when it does).
		
	* AlarmPanel.java
	* FavoriteAlarmPanel.java
		** These classes are very simple because they are simply Views.  There is no real controller code associated with them, they don't control anything.  Instead, the MainFrame listens to their events and controls things when their events are fired.
		
alarmclock.models
	This package is all about representing data as structures.  All these classes Are Something.  They represent a Thing that we need to Display or Do Something with.  Because they Are Something (that is, they are Models), they don't have any code that really Does Anything.  All they have are Properties (that is, fields with getter and setter methods), and well-defined Equals and GetHashCode methods.  The Equals method is important because for classes that Are Something, we need to be able to tell when two instances of a class represent the Same Thing.  And if you ever override equals you always should override GetHashCode.
	
	* SetAlarm.java
		** This class Is Something.  It is an Alarm.  It may not yet be set, but it is defined.  It has a time when it's supposed to go off (if it ever gets set) and it has a path to a file or website it needs to launch when it goes off.
		
	* FavoriteAlarm.java
		** This class Is Something.  It is a stored favorite alarm, that can be turned into an actual Alarm.  Notice it doesn't define on itself the code to turn it into an alarm.  That's because this class Is Something, so it doesn't Do Anything.  Doing things to FavoriteAlarms is the responsibility of the FavoriteAlarmService.