CODING TASK FOR GOFORE

A customer working in the railway industry wants an API that returns the current location of a given train. For the location it is enough to return result that contains the status of the train (currently running or not) and in case it is running, the current location as either:

1. a station in case the train is currently on some station (e.g, Pasila)

2. from and to stations in case the train is currently moving between station that the train will stop on (e.g., from Helsinki to Pasila)

Your task is to implement a backend service with a single endpoint that returns this information by determining the location from data provided by Digitraffic service that is described below.

Some terminology and additional information:

Each train corresponds to a single trip from starting station to the end station such that no two trains have the same identifier during the same day. The identifiers of trains consists of 4-6 numbers (e.g., 8448).

It is possible to get information about a train from the following endpoint: GET https://rata.digitraffic.fi/api/v1/trains/[date]/[train number]. For example, https://rata.digitraffic.fi/api/v1/trains/2020-01-23/8448 returns information about the train 8448 for date 2020-01-23.

For testing you can find trains that have recently departed from Helsinki station by querying https://rata.digitraffic.fi/api/v1/live-trains/station/HKI?arrived_trains=0&arriving_trains=0&departed_trains=20&departing_trains=0&include_nonstopping=false

Digitraffic uses station short codes that are three character long identifier (e.g, HKI = Helsinki). Your location service can return the location using the same station short codes.
