# AnimalTracking
Tracks objects in an mp4 file or live video. Currently implemented to track mice and determine features such as its speed, distance traveled, time spent near walls, etc. Only mice of the target color will be tracked while everything else will be filtered out (Currently detects dark colored objects).

## Usage

### Prerequisites

.dll files are incompatible with Mac OSX.

All of the libraries required to run the program are included in the lib folder. Go to (File > Project Structure > Libraries > +) and configure all the .jar files. Make sure that the jdk is 64 bit and the project language level is at 8. 

### Installing

Runnable on any IDE (I prefer IntelliJ for easy setup and cloning).

```
 $ git clone https://github.com/wang0324/AnimalTracking.git
```

### Running AnimalTracker
1. Run Main
2. Make sure you have the videos in the folder Assets and select one of the videos in Assets. Currently only "TARGET_MOUSE_VIDEO.mp4" works fully.
3. Once it starts playing the video, press 'f' and type in the name of the animal tracking class. The default class is "AnimalTracker".
The other classes are there to assist with the AnimalTracker, and you can use them individually if you'd like.
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

## Contributing

This project is currently open source are free for anyone to contribute. Simply create a new branch, make a pull request, and I will verify whether or not the edits are valid.

## Authors

* **David Dobervich** - *Framework and UI* - [ddobervich](https://github.com/ddobervich)
* **Kevin Wang** - *Tracker and DataSet Implementation* - [wang0324](https://github.com/wang0324)
* **Toma Grundler** - *Tracker and DataSet Implementation* - [tgrundler760](https://github.com/tgrundler760)
* **Young Yu** - *Github README template* - [yyu2002](https://github.com/yyu2002)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
