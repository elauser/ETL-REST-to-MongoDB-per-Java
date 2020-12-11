# ETL-REST-to-MongoDB-per-Java

Concurrently Crawl the RiotAPI, starting with one User.
It uses the max amount of requests the API gives for the developer tier.
The data gets uploaded to a MongoDB Server, in this case I used atlas.

The request limit is enforced by Region, so if you want to crawl more Data/s just instantiate a crawler for each region.
