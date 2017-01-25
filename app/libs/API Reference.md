#API Reference

* Class 
	`Chartboost`
		* Methods:
			//SDK Initialization (Mandatory)
			`void startWithAppId(Activity activity, String appId, String appSignature)`

			//Chartboost Activity callbacks (Mandatory)
			`void onCreate(Activity activity)`			
			`void onStart(Activity activity)`			
			`void onResume(Activity activity)`			
			`void onPause(Activity activity)`			
			`void onStop(Activity activity)`			
			`void onBackPressed(Activity activity)`
			
			//Deprecated
			`void clearCache()`
			
			//AD Calls
			`boolean hasRewardedVideo(String location)`
			`void cacheRewardedVideo(String location)`
			`void showRewardedVideo(String location)`
			`boolean hasInterstitial(String location)`
			`void cacheInterstitial(String location)`
			`void showInterstitial(String location)`
			`boolean hasMoreApps(String location)`
			`void cacheMoreApps(String location)`
			`void showMoreApps(String location)`

			//Set a custom framework suffix to append to the POST headers field of every request.
			`void setFramework(CBFramework framework)`
			`void setFramework(CBFramework framework, String version)`

			//Set a custom framework version to append to the POST body of every request.
			`void setFrameworkVersion(String version)`

			//Set a mediation library to append to the POST headers field of every request.
			`void setMediation(String libraryName, String version)`

			//Set a custom identifier to send in the POST body for all Chartboost API server requests.
			`void setCustomId(String customID)`
			`String getCustomId()`

			//AbstractChartboostDelegate getDelegate()
			`setDelegate(ChartboostDelegate delegate)`

			//Auto Cache ads
			`boolean getAutoCacheAds()`
			`void setAutoCacheAds(boolean autoCacheAds)`

			//Decide if Chartboost SDK should show interstitials in the first session.
			`setShouldRequestInterstitialsInFirstSession(boolean shouldRequest)`

			// Decide if Chartboost SDK should show a loading view while preparing to display the "more applications" UI.
			`setShouldDisplayLoadingViewForMoreApps(boolean shouldDisplay)`

			//Decide if Chartboost SDKK will attempt to fetch videos from the Chartboost API servers.
			`setShouldPrefetchVideoContent(boolean shouldPrefetch)`

			`void setLoggingLevel(CBLogging.Level lvl)`
			`CBLogging.Level getLoggingLevel()`

			// Check to see if any chartboost ads are visible
			`boolean isAnyViewVisible()`

            // Close any visible impression
            `void closeImpression()`

            //Returns the current version of the Chartboost SDK
            `String getSDKVersion()`

            //Decide if Chartboost Views should hide the status and navigation bars when being shown
            `void setShouldHideSystemUI(Boolean hide)`

            //Check if Chartboost is using Web Views
            `bool isWebViewEnabled()`

            //Enable Activity Lifecycle Callbacks (Only available in Android 4.X or above and disabled by default)
            `void setActivityCallbacks(boolean enabled)`

* Class
	`CBAnalytics`
		* Methods:
			`trackInAppPurchaseEvent(EnumMap<CBIAPPurchaseInfo, String> map, CBIAPType iapType)`
			`trackInAppGooglePlayPurchaseEvent(String title, 
									 String description,
				 					 String price,
									 String currency,
									 String productID,
									 String purchaseData,
									 String purchaseSignature)`
			`trackInAppAmazonStorePurchaseEvent(String title, 
									 String description,
				 					 String price,
									 String currency,
									 String productID,
									 String userID,
									 String purchaseToken)`

* Class 
	`CBLocation`
		* Constants:
				/*! "Default" - Default location */
				public static final String LOCATION_DEFAULT = "Default";
				/*! "Startup" - Initial startup of game. */
				public static final String LOCATION_STARTUP = "Startup";
				/*! "Home Screen" - Home screen the player first sees. */
				public static final String LOCATION_HOME_SCREEN = "Home Screen";
				/*! "Main Menu" - Menu that provides game options. */
				public static final String LOCATION_MAIN_MENU = "Main Menu";
				/*! "Game Screen" - Game screen where all the magic happens. */
				public static final String LOCATION_GAME_SCREEN = "Game Screen";
				/*! "Achievements" - Screen with list of achievements in the game. */
				public static final String LOCATION_ACHIEVEMENTS = "Achievements";
				/*! "Quests" - Quest, missions or goals screen describing things for a player to do. */
				public static final String LOCATION_QUESTS = "Quests";
				/*!  "Pause" - Pause screen. */
				public static final String LOCATION_PAUSE = "Pause";
				/*! "Level Start" - Start of the level. */
				public static final String LOCATION_LEVEL_START = "Level Start";
				/*! "Level Complete" - Completion of the level */
				public static final String LOCATION_LEVEL_COMPLETE = "Level Complete";
				/*! "Turn Complete" - Finishing a turn in a game. */
				public static final String LOCATION_TURN_COMPLETE = "Turn Complete";
				/*! "IAP Store" - The store where the player pays real money for currency or items. */
				public static final String LOCATION_IAP_STORE = "IAP Store";
				/*! "Item Store" - The store where a player buys virtual goods. */
				public static final String LOCATION_ITEM_STORE = "GItem Store";
				/*! "Game Over" - The game over screen after a player is finished playing. */
				public static final String LOCATION_GAMEOVER = "Game Over";
				/*! "Leaderboard" - List of leaders in the game. */
				public static final String LOCATION_LEADERBOARD = "Leaderboard";
				/*! "Settings" - Screen where player can change settings such as sound. */
				public static final String LOCATION_SETTINGS ="Settings";
				/*! "Quit" - Screen displayed right before the player exits a game. */
				public static final String LOCATION_QUIT = "Quit";									 

* Class
	`Enum`
		* CBFramework{
	    		CBFrameworkUnity,
	    		CBFrameworkCorona,
			}

		*  CBImpressionError {
				/** An error internal to the Chartboost SDK */
				INTERNAL,
				/** No internet connection was found */
				INTERNET_UNAVAILABLE,
				/** Too many simultaneous requests (eg more than one show request, more than one cache request of the same type and location) */
				TOO_MANY_CONNECTIONS,
				/** The impression sent was not compatible with the device orientation */
				WRONG_ORIENTATION,
				/** This is the first user session and interstitials are disabled in the first session */
				FIRST_SESSION_INTERSTITIALS_DISABLED,
				/** An error occurred during network communication with the Chartboost server */
				NETWORK_FAILURE,
				/** No ad was available for the user from the Chartboost server */
				NO_AD_FOUND,
				/** The startSession() method was not called as per implementation instructions */
				SESSION_NOT_STARTED,
				/** There is already an impression visible or in the process of loading to be displayed */
				IMPRESSION_ALREADY_VISIBLE,
				/** There is no currently active activity with Chartboost properly integrated */
				NO_HOST_ACTIVITY,
				/** User cancels the alert notification pop-up */
			    USER_CANCELLATION,
			    /** Invalid location*/
			    INVALID_LOCATION
			}
		*  CBClickError {
				/** Invalid URI */
				URI_INVALID,
				/** The device does not know how to open the protocol of the URI  */
				URI_UNRECOGNIZED,
				/** User failed to pass the Age Gate */
				AGE_GATE_FAILURE,
				/** There is no currently active activity with Chartboost properly integrated */
				NO_HOST_ACTIVITY,
				/** Unknown internal error */
				INTERNAL,
			}	

		* CBIAPPurchaseInfo {
				PRODUCT_ID,
				PRODUCT_TITLE,
				PRODUCT_DESCRIPTION,
				PRODUCT_PRICE,
				PRODUCT_CURRENCY_CODE,
				GOOGLE_PURCHASE_DATA,
				GOOGLE_PURCHASE_SIGNATURE,
				AMAZON_PURCHASE_TOKEN,
				AMAZON_USER_ID,
			}
		* CBIAPType {
				GOOGLE_PLAY,
				AMAZON,
			}	


* Class 
	`ChartboostDelegate`
		* Methods:
			// Methods you can Override to get callback events before and after ever ad calls.
			shouldRequestInterstitial(String location)
			shouldDisplayInterstitial(String location)
			didCacheInterstitial(String location)
			didFailToLoadInterstitial(String location, CBImpressionError error)
			didDismissInterstitial(String location)
			didCloseInterstitial(String location)
			didClickInterstitial(String location)
			didDisplayInterstitial(String location)
			shouldRequestMoreApps(String location)
			didCacheMoreApps(String location)
			shouldDisplayMoreApps(String location)
			didFailToLoadMoreApps(String location, CBImpressionError error)
			didDismissMoreApps(String location)
			didCloseMoreApps(String location)
			didClickMoreApps(String location)
			didDisplayMoreApps(String location)
			didFailToRecordClick(String uri, CBClickError error)
			didPauseClickForConfirmation()
			shouldDisplayRewardedVideo(String location)
			didCacheRewardedVideo(String location)
			didFailToLoadRewardedVideo(String location, CBImpressionError error)
			didDismissRewardedVideo(String location)
			didCloseRewardedVideo(String location)
			didClickRewardedVideo(String location)
			didCompleteRewardedVideo(String location, int reward)
			didDisplayRewardedVideo(String location)
			didCacheInPlay(String location)
			didFailToLoadInPlay(String location, CBImpressionError error)


* Class
	`Optional but recommeded`
	/**
	 * Use any of these classes as a base class for any activities of yours in order to simplify Chartboost integration.
	 * This class handles all of the necessary lifecycle method calls into Chartboost.
	 */

	public abstract class ChartboostActivity 

