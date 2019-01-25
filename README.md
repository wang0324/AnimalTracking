# AnimalTracking

## How To Use

### Prerequisites

.dll files are incompatible with Mac OSX.

All of the libraries required to run the program are included in the lib folder. Go to (File > Project Structure > Libraries > +) and configure all the .jar files. Make sure that the jdk is 64 bit and the project language level is at 8. 

### Installing

Runnable on any IDE (I prefer IntelliJ for easy setup and cloning).

```
 $ git clone https://github.com/yyu2002/VideoImageClustering.git
```

### Running tasks
1. Run Main
2. Make sure you have the videos in the folder Assets and select one of the videos in Assets. Currently only "TARGET_MOUSE_VIDEO.mp4" works fully.
3. Once it starts playing the video, press 'f' and type in the name of the animal tracking class. The default class is "AnimalTracker".
4. This shows you the perspective after filtering the video. Press 's' to toggle between the original perspective and filtered perspective
5. If you would like to change the type of filter, press 'f' and type in the name of the class that performs the filter you would like.


## Built With

* [Java 8](https://www.oracle.com/technetwork/java/javase/downloads/index.html) - language
* [Processing](https://processing.org/) - graphics
* [gluegen](https://jogamp.org/gluegen/www/) - interface and C libraries 
* [gstreamer](https://gstreamer.freedesktop.org/) - media processing
* [jna](https://github.com/java-native-access/jna) - java native access
* [jogl](http://jogamp.org/jogl/www/) - wrapper library
* [video](https://processing.org/reference/libraries/video/index.html) - video capturing

## Authors

* **David Dobervich** - *Framework and UI* - [ddobervich](http://github.com/ddobervich)
* **Young Yu** - *Tracker and DataSet Implementation* - [yyu2002](https://github.com/yyu2002)

## TODO
- Make the tracker work for all the different mouse videos
- Return the data into a file or make another window to display it on screen
- Work for other animals, not just mice
