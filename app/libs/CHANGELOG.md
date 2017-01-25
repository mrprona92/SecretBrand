Android Change Log

Version 6.6.1 *(2016-10-27)*
----------------------------

*Fixes*
- Fixed crashes with Supersonic mediation. 
- Cached ads are no longer shared between activities. 
- Fixed crashes with Fyber mediation. 
- Fixed crashes with Corona mediation rewarded video. 

Version 6.6.0 *(2016-09-27)*
----------------------------
*Features:*
- Added support for AerServ mediation 
- Added support for HeyZap mediation 
- InPlay works again in the Example App 

*Improvements:*
- We added why we need certain app permissions in the manifest 
- Reduced DEX method count by 606 in com.chartboost and 629 overall 

*Fixes*
- More Apps page will no longer fail to show with loading bar enabled 
- didShow delegate could be called when an ad was actually not shown 
- Update device identifiers on every request 
- Fixed an issue where the close button was being misplaced 
- Optimized template parameter replacement 
- Fixed a NullPointerException when backgrounding the app 


Version 6.5.1 *(2016-08-26)*
----------------------------
*Fixes*
- Fixed an issue where invalid server responses could be treated as valid. 

Version 6.5.0 *(2016-08-10)*
----------------------------
*Features:*
- The example app CBSample has been renamed ChartboostExampleApp. 
- ChartboostExampleApp has been updated to better showcase Chartboost SDK features. 
- Chartboost SDK now uses network compression, improving performance. 

*Improvements:*
- ```void setFrameworkVersion()``` has been deprecated. Please use ```void setChartboostWrapperVersion()``` to set wrapper version. 
- Added new mediation enum value HyprMX. 
- Error codes are more accurate and descriptive. 
- Previously deprecated methods and classes now removed:
    - ```ChartboostActivity``` class
    - ```boolean getIgnoreErrors()```
    - ```void setIgnoreErrors(boolean ignoreErrors)```
    - ```void didPassAgeGate(boolean pass)```
    - ```void setShouldPauseClickForConfirmation(boolean shouldPause)```
    - ```void clearCache()```
    - ```void setFramework(final CBFramework framework)```
    - ```boolean getImpressionsUseActivities()```
    - ```void setImpressionsUseActivities(final boolean impressionsUseActivities)```
    - ```void didPauseClickForConfirmation()```
    - ```void didPauseClickForConfirmation(Activity activity)``` 

*Fixes:*
- Fixed an issue in which pressing the back button did not call the dismiss/close delegates. 
- A cached ad will no longer fail to show. 
- SDK will no longer keep 0 byte files in cache when a download fails. 
- Video ads no longer stay paused after maximizing a minimized app. 

Version 6.4.2 *(2016-06-30)*
----------------------------
*Features:*
- The Chartboost Android SDK no longer supports the age gate feature. API methods related to age gate are being deprecated and will be removed in a future release.  
- Publishers only: All Chartboost ads are now shown using CBImpressionActivity. You must add CBImpressionActivity to your AndroidManifest.xml file, like this:

<activity android:name="com.chartboost.sdk.CBImpressionActivity"
                 android:excludeFromRecents="true"
                 android:hardwareAccelerated="true"
                 android:theme="@android:style/Theme.Translucent.NoTitleBar.Fullscreen"
                 android:configChanges="keyboardHidden|orientation|screenSize"/>

*Note:* Make sure hardware acceleration is added and enabled when declaring the CBImpressionActivity in the manifest.

*Fixes:*
- Media loading is now more efficient. 
- Memory usage on device has been optimized. 
- Video download is now more efficient.  
- Fixed ConcurrentModificationException occurring in some devices. 
- Fixed issue with video playback when backgrounding.. 
- Fixed IMPRESSION_ALREADY_VISIBLE error. 

Version 6.4.1 *(2016-03-23)*
----------------------------

*Features:*

*Improvements:*

MO-1148 @sathish, Parsing exception for api/config when determining the right endpoint to make adserver calls.
MO-1156 @sathish, Minor issue in logging exception, if trying to access file directories that are prohibited access.
MO-1157 @sathish, HasXXX() calls always returns false for webview ad formats. Like for example HasInterstitial(location).

Version 6.4.0 *(2016-03-14)*
----------------------------

*Features:*
- TBD Marketing Text
- New method 'setActivityCallbacks(boolean enabled)'. Use it to enable the Activity Lifecycle Activities. Only available in Android 4.X and above versions. This behavior is disabled by default.

*Improvements:*
- Retry downloading assets that fail
- We invalidate cached impressions on soft boot ups if any shared assets have been deleted
- Fixed issues causing "Could Not Delete Cache Entry for key" log message on startup

MO-1041 @sathish, Support for registering Activity Lifecycle Callbacks for only apps that support Android API 14 and above



Version 6.3.0 *(2016-02-08)*
----------------------------
- Upgrade now to enable Chartboost's behind-the-scenes video optimization.

*Improvements:*
- Older devices in the networking library no longer crash on occasion.
- MoreApps rotation issue has been resolved.
- Removed "Could Not Delete Cache Entry for key" notification from logs.


Version 6.2.0 *(2016-01-13)*
----------------------------

Features:
- New method 'getSDKVersion()' returns the current version of the Chartboost SDK. 
- New method 'setShouldHideSystemUI(Boolean hide)' hides or displays the Navigation and Notification bars for Chartboost ads. 

Fixes:
- Improved handling of views when the app is minimized after clicking an ad. 
- Reduced network usage with more efficient handling of cached assets. 

Improvements:
- 'setShouldRequestInterstitialsInFirstSession' is no longer deprecated.

Version 6.1.0 *(2015-11-19)*
----------------------------
Fixes:
- In Play would not trigger the failure delegate on failure 
- Improved Video V2 caching 
- Support for Android API 9 - 23 
- Crash fixes for SDK Version 5.5.3 
- Proguard: Add the below rule for API 23 to ignore apache http warnings
	-dontwarn org.apache.http.**

Improvements:
- didInitialize() delegate callback added to notify the SDK is initialized and ready to use.

Version 6.0.2 *(2015-10-19)*
----------------------------

- Video experience rebuilt from the ground up, upgrade to this SDK to enable Chartboost's behind-the-scenes video optimization

- Deprecated class and methods

	*Class:*
		ChartboostActivity

- *Recommended Permission*
	Pause/Resume playback during phone calls. SDK need to know when a phone call come in and when it is finished when we show video ads.
	```<uses-permission android:name="android.permission.READ_PHONE_STATE"/>```

- *Required*
	Build SDK Version - 23
	Compile SDK Version - 23

- *Optional* (If you do support Android M its required then)
	Target SDK Version - 23

- *Required*
	All 6.0 and above SDK will be using the below activity to show ads rather use developers host activity.
	 ```<activity android:name="com.chartboost.sdk.CBImpressionActivity"
			   android:excludeFromRecents="true"
			   android:hardwareAccelerated="true"
			   android:theme="@android:style/Theme.Translucent.NoTitleBar.Fullscreen"
			   android:configChanges="keyboardHidden|orientation|screenSize"/>```

- Age Gate Delegate Callback is modified and need to use the new overloaded method didPauseClickForConfirmation(Activity activity).

- Fixes
	All bug fixes for current production build

Version 5.5.4 *(2015-08-20)*
----------------------------

Features:

- Add CBMediation enum for use with mediation networks.  

Fixes:

Improvements:

Version 5.5.3 *(2015-07-28)*
----------------------------

Features:

Fixes:

- Fixed a crash in the retry event system.  

Improvements:

Version 5.5.0 *(2015-06-28)*
----------------------------

Features:

Fixes:

- Age gate now hides the ad between the `didPauseClickForConfirmation` call and `didPassAgeGate` response.  
- Video ads stop playing when didPauseClickForConfirmation() is called. 
- Removed an unnecessary video prefetch call. 
- Fixed Chartboost activity leaking in some cases.  
- Loading screens were improperly showing for interstitial and rewarded videos.  
- Sample build file fixed to include required Google library. GAID would not be sent. 
- Typos fixed in some post install event responses. 
- Timezone format fixed for Android 4.1 and early. 

Improvements:

- Added a new method, `public void setMediation(String libraryName, String version)`, to track mediation library versions. 


Version 5.4.1 *(2015-06-03)*
----------------------------

Features:

Fixes:

- Chartboost media files will no longer appear in the phone gallery 

Improvements:


Version 5.4.0 *(2015-06-01)*
----------------------------

Features:


Fixes:

- Fixed bug causing ads to disappear on rotation while using Activities 
- Reward video pre-roll screen now properly displays after home key input. 
- Fixed null pointer exception that occurred initializing Chartboost and making calls on different threads. 
- Fixed ads disappearing on rotation when using activities.  
- Fixed rare bug that prevented video prefetching under certain circumstances. 

Improvements:

- Added error logging for all network calls. 
- Chartboost now determines which network type the device is using.  

Version 5.3.0 *(2015-04-30)*
----------------------------

Features:

- Added a new method, `public void setFrameworkVersion(String version)`, to track custom framework versions. 
- Added [Weeby](http://www.weeby.co) to available frameworks. 

Fixes:

- Ad will no longer disappear in some devices if app is backgrounded 

Improvements:

- Added a way to detect rooted devices. 

Version 5.2.0 *(2015-04-01)*
----------------------------

Features:

Fixes:

- Fix Android SDK stops showing ads if dismissing too fast when setImpressionsUseActivities set to true. 
- Fix Android 5.1.3 native SDK hasRewardedVideo can return true but show call fails with Error: VIDEO_UNAVAILABLE. 
- Fix Android SDK 5.1.1 crashes sometimes if back button pressed after interstitial is shown. 
- Fix Android 5.1.1 crashing sporadically on showInterstitial calls. 
- Fix Android 5.1.3 native SDK Crashing sometimes after viewing interstitial. 
- Remove all exception thrown from the SDK. 
- Fix /api/install not being called on every bootup for Android SDK. 
- Fix Video should no longer leave any transparent space when fullscreen 

Improvements:

- Update Amazon IAP library to v2.0. 

Version 5.1.3 *(2015-03-16)*
----------------------------

Features:

Fixes:

- Fix Seeing duplicate calls for showInterstitial on Unity Android. 
- Fix Seeing duplicate calls for showRewardedVideo on Unity Android. 

Improvements:

Version 5.1.2 *(2015-03-04)*
----------------------------

Features:

- Added new method closeImpression() that will close any impression visible on the screen. 

Fixes:

- Fix didDismissInterstitial and didDismissRewardedVideo not executing on Android. 
- Fix didCacheInterstitial not being called if an ad was already cached at the location. 

Improvements:

Version 5.1.1 *(2015-02-20)*
----------------------------

Features:

Fixes:

- Fix for devkit engine issue on video playback. 
- Fix video crash issue on crash exception. 
- Fixes for video issues. 

Improvements:

Version 5.1.0 *(2014-12-09)*
----------------------------

Features:

Fixes:

- Fix Android SDK 5.0.3 crashing on Video Interstitial due to NullPointerException. 
- Fix Android SDK: Proper error enum names. 
- Fix MoreApps Unity 5.0.2 and 5.0 for Android doesn't work. 
- Fix Unity: Parity Android error enum. 
- Fix SDK sends "custom-id" but ad server expects "custom_id" field. 
- Fix See black screen and not video when reward/get has been modified. 
- Fix Android Video: Black screen for invalid response. 
- Fix Rewarded Video request freezes app if pre-roll popup gets closed after watching at least one video. 

Improvements:

Version 5.0.4 *(2014-11-06)*
----------------------------

- Fix autocaching ads.
- New API: isAnyViewVisible() which can used to check if any chartboost ads are visible.
- Bug fixes and stability improvements for video, interstitial and inplay.

Version 5.0.3 *(2014-09-30)*
----------------------------

- Fix video playback codec support for low end android devices.
- 'willDisplayVideo' delegate callback added when the video is about to be displayed both for rewarded and interstitial video.
- Minor bug fixes and improvements.

Version 5.0.2 *(2014-09-10)*
----------------------------

- Fix for video where certain OEM devices video complete calls where not sent out properly.
- Minor bug fixes and patches.

Version 5.0.1 *(2014-09-05)*
----------------------------

- Fix for flickering issue for video on 2.3 devices on native sdk.
- Fix for unity plugin caused calling the onBackPressedEvent from a non-UI Thread.
- Fix for a race condition where concurrent ad request of same type can be sent for same location.
- Fix for a crash when using impression activities.
- Some minor fixes.

Version 5.0.0 *(2014-09-02)*
----------------------------

- API change: Chartboost methods are now called statically, and some of the method names and structures have changed. Please see the sample app or readme for updated integration instructions.
- Updated MoreApps page (graphics and functionality).
- New ad products: video interstitials and rewarded videos.
- Updated sample project.
- Many delegate methods moved to setter and getter functions.
- CBPostInstallTracking renamed to CBAnalytics.
- Added impression auto-caching feature.
- Removed unnecessary settings.
- Bugfixes and stability improvements.
- Upgrading to 5.0 Google Play Services library.
- Some major fixes and cleanups on SDK and libs being used to reduce the jar size.

Version 4.1.2 *(2014-08-14)*
----------------------------

- Fix: Random crash that happens on 4.1.1 builds.

Version 4.1.1 *(2014-06-10)*
----------------------------

- Fix: Crash while retrying request during network change.
- Fix: Crash while caching interstital before calling onStart().
- Fix: Crash when filesystem data unavailable.
- Tracking overload if network failure happens.

Version 4.1.0 *(2014-06-10)*
----------------------------

- New product - Post Install Analytics Tracking. Exposing In-App purchasing API for developers to track GooglePlay, Amazon and  ChartBoost Store in-app purchases. For more info go through ReadMe section of PostInstallTracking.
- New product - Tracking. This product is mainly used to log session events, user events, system events and debug events that is recorded across SDK and sent to server.
- New update:
	 a) Carrier information like Carrier name,MCC,MNC,ISO and phone type is sent in every API request.
 	 b) jb flag is sent on every request to determine if the device is more likely jailbroken or rooted device.
	 c) CBLocation constants are provided to developers. Its a default set of location constants which they can use during impression calls.
- Deprecated methods (Will be removed in the future releases)
	a) startSession() - SDK will be internally handling and tracking the session calls.
	b) clearImageCache() - Use clearCache() method which will perform clearImageCache() operation internally.
	c) setFramework() - It was removed in the previous release which cause some chaos, so we are just adding it as a deprecated method for now.
- SDK Improvements
	a) Ultra fast Networking library for asynchrounous networking. (Volley + OkHttp Square)
	b) All Impression classes are now more structurally organized.
	c) All Impressions are grouped into their own packages, so that it will be more helpful when we move to dependency injection structure.
	d) File Caching and network reachability is centralised across SDK use.
	e) RequestManager module integrated with the new custom networking library for more seamless and lossless chartboost api calls.
	f) Some clickable elements in impressions now have a pressed effect to improve user experience.

Version 4.0.0 *(2014-03-17)*
----------------------------

- New Feature: Added Google Play Services advertising ID tracking, please see updated integration instructions in the documentation.
- New Feature: Different close button images for each orientation.
- New Feature: Error reasons for delegate failure methods.
- Animation performance improved -- we recommend you target Android 4.0+ for best performance.
- New Feature: Age gate for impression links -- see the documentation of the new delegate method `shouldPauseClickForConfirmation()`.
- Change: We've reorganized the SDK so that all Chartboost properties and settings are accessed and changed through the new `CBPreferences` class.
- New Feature: Control over logging that can help us diagnose any issues that you are having, see `CBPreferences.setLoggingLevel()`.
- Renamed `didFailToLoadUrl` delegate method to `didFailToRecordClick` in order to better describe all possibilities.
- Fixed rare bug that caused incorrect ad sizes.
- `CBAnalytics` is no longer available.

Version 3.4.0 *(2013-11-20)*
----------------------------

- New feature: now handles high res interstitial assets!

Version 3.3.0 *(2013-09-12)*
----------------------------

- New Feature: `ChartboostActivity` and `ChartboostNativeActivity` are simple alternatives to implementing Chartboost in your app.  Simply extend your activities from either class (depending on whether you use native activities or not), provide the app ID and signature, and you're done!
- Changed documentation to suggest calling `Chartboost.startSession()` in your activity's `onStart()` method.

Version 3.2.3 *(2013-06-24)*
----------------------------

- New Feature: `ChartboostDefaultDelegate` is a new abstract class that serves as a `ChartboostDelegate`. Extending from this instead will allow you to skip overriding the methods you are not interested in, at the expense of not being able to inherit from your own base class. The default implementation returns true for any method that returns a boolean in the delegate.
- New delegate method: `didFailToLoadUrl()` is called when the result of a click in an impression fails to load.
- Added exceptions to warn you if you have forgotten to properly exclude Chartboost from your proguard using the line `-keep class com.chartboost.sdk.** { *; }`.
- Change: Simultaneous identical interestitial or more apps request attempts will fail immediately. However, simultaneous interstitial requests with different locations are fine.
- Fix allowing Chartboost servers to differentiate buggy Android 2.x devices that all share the same `ANDROID_ID`.
- Fixed an infrequent HTTP connection error during image downloads.
- Fixed bug where if the network is lost while viewing the more apps page, a click would cause a progress bar to hang forever.
- Fixed edge case crashes related to caching images.

Version 3.2.2 *(2013-06-03)*
----------------------------

- Change: Method `Chartboost.orientation()` is now called `getOrientation()`.
- Add ability to disable animations and fixed animation type none.
- Fixed memory leak that could sometimes occur with the activity impressions setting.

Version 3.2.1 *(2013-04-23)*
----------------------------

- Removed dependence of sensor listener unless orientation is overridden.
- Removed all dependencies on `AsyncTask`, ensuring full compatibility with multithreaded apps.
- Fixed bug where screen stayed dark after closing interstitials on certain problem devices (including 4.1.2 Nexus S and Nexus 10).
- Fixed interference with game-rate sensor listeners on certain problem devices (including 2.3.x Galaxy S1 and Galaxy S2).
- Fixed crash when `cacheInterstitial()` was called off the UI thread.

Version 3.2.0 *(2013-03-30)*
----------------------------

- New method: `Chartboost.showMoreAppsData()` allows you to get raw JSON information about your more apps page so that you can display it however you like.
- Change: Show impression calls will immediately fail (and call delegate method) if an impression is already visible.
- Stopped delegate method `shouldDisplayLoadingViewForMoreApps()` from being fired when showing interstitials.
- Fixed delegate method `shouldRequestMoreApps()` was not being called as specified.

Version 3.1.6 *(2013-03-21)*
----------------------------

- Fixed harmless Lint error about invalid package reference (also no longer necessary to include `-dontwarn java.lang.management.**` in your proguard configuration).
- Fixed memory leak involved in using Chartboost in multiple subsequent activities.
- Fail gracefully in low memory situations.
- Added ability to suppress off-thread method calling exceptions for certain unusual use cases.
