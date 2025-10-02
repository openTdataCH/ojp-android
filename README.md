# Open Journey Planner SDK for Android

## Overview
This SDK is targeting Android applications seeking to integrate [Open Journey Planner(OJP) APIs](https://github.com/openTdataCH/ojp-android/) to support distributed journey planning according to the European (CEN) Technical Specification entitled “Intelligent transport systems – Public transport – Open API for distributed journey planning”

### Features
Available APIs:
- [Location Information Request](https://opentransportdata.swiss/it/cookbook/ojplocationinformationrequest-ojp-2/)
- [Trip Request](https://opentransportdata.swiss/en/cookbook/ojptriprequest/)
- Trip Refinement Request

Coming soon:
- [Stop Event Request](https://opentransportdata.swiss/en/cookbook/ojp-stopeventservice/)
- [TripInfo Request](https://opentransportdata.swiss/en/cookbook/ojptripinforequest/)

## Requirements
Compatible with Android 6+

## Integration
To integrate the SDK you have to add following dependency:
```
dependencies {
    implementation 'io.github.opentdatach:ojp-android:1.3.2'
}
```
Additionally you may need to enable coreLibraryDesugaring to use Java8 features below API 26 (we use LocalDateTime for parsing)
```
compileOptions {
    isCoreLibraryDesugaringEnabled = true
    ...
}

dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.5")
    ...
}
```

## Usage
### Initializing
Initialize the SDK with your custom configuration:
```
OjpSdk(
    baseUrl = "Your Base URL",
    endpoint = "Your Endpoint",
    requesterReference = "Your Requester Reference",
    httpHeaders = hashMapOf(
    	"Authorization" to "Bearer myAccessToken",
    	"Custom-Header" to "CustomValue"
    ),
    defaultTimeZone = ZoneId.of("Europe/Zurich")
)   
```
### Basic Usage
#### Get a list of locations from a keyword
```
import ch.opentransportdata.ojp.OjpSdk

requestLocationsFromSearchTerm(
    languageCode = LanguageCode.EN,
    term = "Bern",
    restrictions = LocationInformationParams(
        types = listOf(PlaceTypeRestriction.STOP, PlaceTypeRestriction.ADDRESS),
        numberOfResults = 10,
        ptModeIncluded = true
    )
)                
```

#### Get a list of locations around a place with longitude and latitude
```
import ch.opentransportdata.ojp.OjpSdk

requestLocationsFromCoordinates(
    languageCode = LanguageCode.EN,
    longitude = 5.6,
    latitude = 2.3,
    restrictions = LocationInformationParams(
        types = listOf(PlaceTypeRestriction.STOP),
        numberOfResults = 10,
        ptModeIncluded = true
    )
)      
```

#### Get a list of trips including disruption messages
```
import ch.opentransportdata.ojp.OjpSdk

requestTrips(
    languageCode = LanguageCode.EN
    origin = PlaceReferenceDto(
        ref = "8507000",
        stationName = NameDto(text = "Bern") ,
        position =  null
    ),
    destination = PlaceReferenceDto(
        ref = "8500010",
        stationName = NameDto(text = "Basel SBB") ,
        position =  null
    ),
    via = null,
    time = LocalDateTime.now(),
    isSearchForDepartureTime = true,
    params = TripParams(
        numberOfResults = 10,
        includeIntermediateStops = true,
        includeAllRestrictedLines = true,
        useRealtimeData = RealtimeData.EXPLANATORY
    ),
)     
```

#### Get a list of mocked trips for testing purposes 
```
import ch.opentransportdata.ojp.OjpSdk

requestMockTrips(stream = source)
```

#### Update an existing trip
```
import ch.opentransportdata.ojp.OjpSdk

requestTripRefinement(
    languageCode = Locale.getDefault().language.toOjpLanguageCode(),
    tripResult = tripResult,
    params = TripRefineParam(
    includeIntermediateStops = true,
    includeAllRestrictedLines = true,
    includeTurnDescription = true,
    includeLegProjection = true,
    includeTrackSections = true,
    useRealtimeData = RealtimeData.FULL
    )
)
```

## Documentation
[Documentation of the Android SDK](https://opentdatach.github.io/ojp-android/)

## Contributing
Contributions are welcomed. Feel free to create an issue or a feature request, or fork the project and submit a pull request.

## License
Apache License, see [LICENSE](./LICENSE)

## Contact
Create an issue or contact [opentransportdata.swiss](https://opentransportdata.swiss/en/contact-2/)