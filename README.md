# StudentStats

## What does it do?

Students from any Polish university that uses USOS as their university platform can log in through USOS to view their study statistics in an appealing graphical form.

This project leverages the usos4j library for seamless integration with the USOS platform. You can learn more about the usos4j library at the [usos4j GitHub repository](https://github.com/WMS-DEV/usos4j)

## Getting started

- provide environment variables (see [.env-sample](/.env-sample))
- provide data for supported universities in [supported_universities.csv](/service/src/main/resources/data/supported_universities.csv)
  - you can find links to generate API keys at [USOS API installations page](https://apps.usos.edu.pl/developers/api/definitions/installations/)
- run with Docker

## How to authenticate through USOS

- `POST` request to `/auth/request-token` endpoint with `universityId` in JSON body (supported universities are listed at `/universities`)
- request should return `requestToken`, `tokenSecret`, and  `authorizationUrl`
- login through `authorizationUrl`, then get redirected back to origin page with a `verifier` as URL query param (if there is no callback, `verfifier` is displayed on USOS page)
- `POST` request to `/auth/access-token` endpoint with `requestToken`, `tokenSecret`, `verifier`, and `universityId` in JSON body
- request should return `jwt`
- include `Authorization` Header with value: `Bearer ${jwt}` when requesting data from `/getData`

### Postman configuration for easier debugging

You can test backend functionallity with Postman with our collection, pre-configured with endpoints and environments. All you need to do is to import [this folder](/service/postman) into Postman.
