# Open Journey Planner SDK for Android

## Overview
This SDK is targeting Android applications seeking to integrate [Open Journey Planner(OJP) APIs](https://github.com/openTdataCH/ojp-android/) to support distributed journey planning according to the European (CEN) Technical Specification entitled “Intelligent transport systems – Public transport – Open API for distributed journey planning”

### Features
Available APIs:
- [Location Information Request](https://opentransportdata.swiss/en/cookbook/location-information-service/)

Coming soon:
- [Stop Event Request](https://opentransportdata.swiss/en/cookbook/ojp-stopeventservice/)
- [Trip Request](https://opentransportdata.swiss/en/cookbook/ojptriprequest/)
- [TripInfo Request](https://opentransportdata.swiss/en/cookbook/ojptripinforequest/)

## Requirements
Compatible with Android 6+

## Integration
- To integrate the SDK you have to add following repository in your root build.gradle:
```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
- Now you can add the OJP dependency:
```
dependencies {
    implementation 'com.github.vasile:jitpack-android-example:1.0.0'
}
```
## Usage
### Initializing
- Base URL
- Endpoint
- Access Token
- Requester Reference

```
OjpSdk.initializeSDK(
	sdkConfig = SdkConfig(
	baseUrl = "Your Base URL",
	endpoint = "Your Endpoint",
	accessToken = "Your Access Token",
	requesterReference = "Your Requester Reference"
    )
)   
```
### Basic Usage
- Get a list of locations from a keyword
```
import ch.opentransportdata.ojp.OjpSdk

requestLocationsFromSearchTerm(term = "Bern")                
```

- Get a list of Locations around a place with longitude and latitude
```
import ch.opentransportdata.ojp.OjpSdk

requestLocationsFromCoordinates(longitude = 5.6, latitude = 2.3)      
```

## Documentation
[Documentation of the Android SDK](https://github.com/openTdataCH/ojp-android/)

## Contributing
Contributions are welcomed. Feel free to create an issue or a feature request, or fork the project and submit a pull request.

## License
Apache License, see [LICENSE](./LICENSE)

## Contact
Create an issue or contact [opentransportdata.swiss](https://opentransportdata.swiss/en/contact-2/)