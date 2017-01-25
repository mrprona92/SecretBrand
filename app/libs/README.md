# Chartboost Android SDK

The Chartboost SDK is the cornerstone of the Chartboost network. It provides the functionality for showing interstitials, Video, MoreApps pages, and tracking in-app purchase events. 

### Usage

Integrating Chartboost takes a few easy steps:

1. Make sure your app fits the minimum requirements:
     - Minimum API level 9 (Android OS 2.3)
     - Target API level 23 (Android OS 6.0)
     
     - Manifest Settings:
        `<uses-sdk android:targetSdkVersion="23" />`
     
     - Required permission: `android.permission.INTERNET`
     - Required permission: `android.permission.ACCESS_NETWORK_STATE`
     - Required class in manifest in order to show chartboost ads
                 `<activity android:name="com.chartboost.sdk.CBImpressionActivity"
                   android:excludeFromRecents="true"
                   android:hardwareAccelerated="true"
                   android:theme="@android:style/Theme.Translucent.NoTitleBar.Fullscreen"
                   android:configChanges="keyboardHidden|orientation|screenSize"/>`

     - Optional permission: `android.permission.WRITE_EXTERNAL_STORAGE`
     - Optional permission: `android.permission.READ_PHONE_STATE`
     - Optional (recommended) permissions: `android.permission.ACCESS_WIFI_STATE`
     
     - Gradle compilation settings
        `android {
            compileSdkVersion 23
            buildToolsVersion '23.0.0'
            ....
            ....
        }`


2. Add the `chartboost.jar` file to your `libs` directory.
     - If you don't have a `libs` directory, create one and add the .jar file to it
     - Optionally add `chartboost.jar.properties` and the `doc` directory to `libs` to get javadocs in your IDE

3. Add the Google Play Services library as a dependency of your project.
     - The Google Play Services library has its own set of integration instructions, including additions to your Android Manifest and Proguard configuration
     - You can follow the setup instructions outlined by Google at the [Google Developer website](https://developer.android.com/google/play-services/setup.html)
    
4. Import the Chartboost SDK into any activity that uses Chartboost

        import com.chartboost.sdk.*;


5. Initialize the Chartboost SDK in your launcher activity class so that the Chartboost callbacks or API calls can be made. See example:
    
            public class <Your class name> extends Activity {
            
            @Override
            public void onCreate() {
                super.onCreate();
                Chartboost.startWithAppId(this, appId, appSignature);
                /* Optional: If you want to program responses to Chartboost events, supply a delegate object here and see step (8) for more information */
                //Chartboost.setDelegate( <Valid Delegate Object> );
                Chartboost.onCreate(this);
                }   
            }   

6. Add the following code into the activity's `onStart()`, `onPause()`, `onResume()` ,`onStop()`, `onDestroy()`, and `onBackPressed()` methods. if you are going to call chartboost methods do this in every activity you call them. This is to ensure we know what state the application is in, so the SDK can track the state of application change.

        @Override
        public void onStart() {
            super.onStart();
            Chartboost.onStart(this);
        }

        @Override
        public void onResume() {
            super.onResume();
            Chartboost.onResume(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            Chartboost.onPause(this);
        }
        
        @Override
        public void onStop() {
            super.onStop();
            Chartboost.onStop(this);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Chartboost.onDestroy(this);
        }

        @Override
        public void onBackPressed() {
            // If an interstitial is on screen, close it.
            if (Chartboost.onBackPressed())
                return;
            else
                super.onBackPressed();
        }
 

7. **Important**: All your interstitial, MoreApps, InPlay and Video calls are static. You *must* pass in the location string to every call, `null` is no longer available. If you don't pass in the location, the SDK won't fetch any ads. You can use the `CBLocation` interface class to get a list of default location constants as well as define your own.  Once a location is used, it will be added to your dashboard where you can customize settings for it.
    * Example Ad Calls:
    
            // Interstitials
            Chartboost.cacheInterstitial(CBLocation.LOCATION_DEFAULT);
            Chartboost.showInterstitial(CBLocation.LOCATION_DEFAULT);
            
            // Video
            Chartboost.showRewardedVideo(CBLocation.LOCATION_GAMEOVER);
            Chartboost.cacheRewardedVideo(CBLocation.LOCATION_GAMEOVER);

8. Back in step (5) you may have noticed `Chartboost.setDelegate(delegate)`.  **Note**: we do not pass '*null*' or a delegate object to an overridden `onCreate()` anymore to create delegates, since it has its own class method now.  Call the method *before* calling `Chartboost.onCreate(this)` during initialization. 
    * The parameter it takes is a *delegate object*, which allows you to respond to the events you are interested in by overriding the methods of your choice.  Simply supply an object of the abstract class `ChartboostDelegate` as the parameter:
    
            private ChartboostDelegate delegate = new ChartboostDelegate() {
            //Override the Chartboost delegate callbacks you wish to track and control
            };             
    
 
9. If you plan on using Proguard, add the following to your *proguard.cfg* file (also make sure you follow Proguard instructions for the Google Play Services library):

        -keep class com.chartboost.** { *; }


### CBAnalytics
 
 The CBAnalytics product tracks events related to In-App Purchases (IAP) made by a user once the game is installed. It provides the developer with more granular information about the IAP events. In this release it only supports `In-App Purchasing` but more event types will be added soon.
        
* **In-App Purchasing:**
      * In-App Purchase lets you sell a variety of items directly within your free or paid app, including premium content, virtual goods, and subscriptions. The Chartboost SDK provides an API to track these purchases by sending the information to the SDK, and supports events that go through `GooglePlayStore` and `Amazon`. Check out the sample app on how to retrieve the data and send it to SDK. 

      * If upgrading from a previous Chartboost SDK version and you are using `trackInAppPurchaseEvent(EnumMap<CBIAPPurchaseInfo, String> map, CBIAPType iapType)` method to send In-App Purchase details, the examples below achieve the same result.

*  **Examples:**

`Google`

        CBAnalytics.trackInAppGooglePlayPurchaseEvent("xxx-title",
                        "xxx-description",
                        "$0.99",
                        "USD",
                        "xxx-id",
                        "xxx-data",
                        "xxx-signature");

`Amazon`

        CBAnalytics.trackInAppAmazonStorePurchaseEvent("xxx-title",
                        "xxx-description",
                        "$0.99",
                        "USD",
                        "xxx-id",
                        "xxx-userId",
                        "xxx-token");

### Example Code
Check out the sample code inside the `ChartboostExampleApp` project, found in the ChartboostExampleApp folder of this download.


### Questions?
We want to your SDK integration be as smooth as possible, so that you can spend more of your time developing awesome games!  If you encounter any issues, do not hesitate to contact our support team at [support@chartboost.com](mailto:support@chartboost.com) and we will be happy to help.
