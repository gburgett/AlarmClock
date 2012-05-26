Hey Dan,

This project is something I created on the side when I got sick of the terrible computer alarm clocks on the internet.  I wanted an alarm clock to play Pandora to wake me up.  How hard is that right?  I couldn't find an alarm clock to do  it so I made my own.

Anyways, I refactored it and heavily commented it and am giving it to you because you are learning how to program in  Java for work.  I figured the best thing for you would be to have some heavily commented examples of good programming  practice, so I didn't cut any corners.  I did everything in this project in the best practices that I know, using all  my tricks and techniques and not skimping on anything.  I also wrote a buttload of comments.  I even wrote a whole  bunch of (not entirely necessary) Unit Tests for you to look over to see how it's done.  Basically I want this to be  a sort of living reference project for you to look at when you want to know, for example, "how do I setup a list of  FooObjects sorted alphabetically by FooString?"  

You can start it up in Netbeans, run it with the debugger attached, set breakpoints and step through it as much as you  want.  If you want you could even add features to it and I would critique them for you.  Please do play with this and  learn from this, because if you're getting paid to learn programming then that's a sweet gig and I want to help you do  the best you can at it.

Let me define a couple terms here.  Throughout the comments you'll see me say "this class Is Something" or "this class Does Something".  Basically what I mean is, when you think of a Class, you should ask yourself the question "does this class represent something?  Or does it Do Something?".  When you make that distinction you'll be able to see the difference between Models and Controllers.  Views are kinda special, they could be considered classes that "Do Something" because they display objects that "Are Something", but I'd prefer to distinguish them by saying the "Display Something".  Anyways, the answer to that question is the heart of the idea behind MVC.

OK so here it is, the Breakdown:

package alarmclock.view
	This package is all about displaying the data.  There's not a whole lot to display but it's kinda complicated cause it's Swing UI framework.  Basically Swing UI is lame, but it's built in to java so you use it sometimes cause you gotta.  Also, because it's Swing UI these classes are also Controllers.  You should google Model-View-Controller (MVC) because that's the way programs are done nowadays.  The old way of WinForms and Swing UI is dying out.
	All these classes Display Something, the MainFrame also Does Something.
	
		
package alarmclock.models
	This package is all about representing data as structures.  All these classes Are Something.  They represent a Thing that we need to Display or Do Something with.  Because they Are Something (that is, they are Models), they don't have any code that really Does Anything.  All they have are Properties (that is, fields with getter and setter methods), and well-defined Equals and GetHashCode methods.  The Equals method is important because for classes that Are Something, we need to be able to tell when two instances of a class represent the Same Thing.  And if you ever override equals you always should override GetHashCode.
	
	
package alarmclock.services
	This package is all about defining the contracts for our services.  It contains only interfaces.  Its purpose is to say "these are the things that need to happen in the program.  Someone needs to be responsible for doing these things."  We will implement these interfaces with classes that will be responsible for doing these things.  The controller classes (alarmclock.view.MainFrame is our only controller class in this program) and other services will rely on these interfaces when they need these things to be done.
	An example of something that needs to be done, for which there is a defined contract:
		Saving a new FavoriteAlarm to disk.  The contract for this is defined in alarmclock.services.FavoriteAlarmService with the method SaveFavorite.
		This is implemented in the class alarmclock.ServiceImplementations.FavoriteAlarmPropertiesService using a PropertiesLoader to perform the loading/saving to disk.
		
package alarmclock.ServiceImplementations
	This package is where all the actual code goes that Does Something and is not a Controller.  All the classes in here implement the interfaces in alarmclock.services.  They are actually responsible for doing the things that need to be done, as defined in the interfaces.
	
Tests!!!

Two of the ServiceImplementations have tests.  Some of the ServiceImplementations are so simple that they don't need tests, also it would be hard to test them since they do things like directly interact with the command prompt.  It would be nice if we could also test the controller, but because in Swing the controller and view are tightly coupled we can't do that.  We'll have to handle that stuff with integration testing.

We also have some test utilities, and some Stubs for a PropertiesLoader.  Stubs are a pretty simple concept.  Instead of using an actual properties loader that loads an actual file from disk, we use a fake one that just returns whatever we need it to return.  In the tests you'll see where we use it to return a programmatically-defined set of properties that the test expects to be there.

Always have tests for as many service implementations as you can.  Better to have too many tests than too few.

Thats it! have fun!
	