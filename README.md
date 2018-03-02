# Thermometer

## Presentation
### What is it ?
This is a very simple application. See how it looks like:

![Alt text](thermometer1.png?raw=true "Thermometer")

At the beginning, the temperature is set to 20 degrees and the thresholds are set to 10 and 90 degrees.

At the top, there is a button "threshold". If you click there, a menu will appear:

![Alt text](menu1.png?raw=true "Menu")

If you click on "high", then a window will appear:

![Alt text](choice1.png?raw=true "Window")

If you enter "50" then the new high threshold will be set to 50 degrees.

At the bottom,there are four buttons: -1 / -auto / +auto / +1
- -1 : the temperature loses one point.
- -auto : the temperature decreases until you click again.
- +1 : the temperature gains one point.
- +auto : the temperature increments until you clik again.

When a threshold is reached, the written temperature (at the left bottom) is supposed to flash red and black :

![Alt text](thermometer2.png?raw=true "Thermometer")

### The code
This application was developed using Java. The graphic interface was created using the swing library.

There are two different classes, in the src file:
- The Indicator Class,
- The Thermometer Class, which is the principal class.

The executable file is also provided.

## To-do list
- When a threshold is reached, the written temperature (at the left bottom) is supposed to flash red and black. It only works with the "auto" mode. 
- The written temperature should be centered.
