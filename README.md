# StudentStats 🌐

## What does it do? 📊

Students from any Polish university that uses USOS as their university platform can log in through USOS to view their study statistics in an appealing graphical form.

This project leverages the usos4j library for seamless integration with the USOS platform. You can learn more about the usos4j library at the [usos4j GitHub repository](https://github.com/WMS-DEV/usos4j).

Check out the deployed version here: [StudentStats Live 🌐](https://studentstats.wmsdev.pl)

## Getting started ⚡

- Provide environment variables (see [.env-sample](/.env-sample))
- Provide data for supported universities in [.supported_universities.csv](/service/src/main/resources/data) (see [supported_universities-example.csv](/service/src/main/resources/data/supported_universities-example.csv))
  - You can find links to generate API keys at [USOS API installations page](https://apps.usos.edu.pl/developers/api/definitions/installations/)
- Run with Docker ⚙️

## How to authenticate through USOS ✅

1. `POST` request to `/auth/request-token` endpoint with `universityId` in JSON body (supported universities are listed at `/universities`)
2. Request should return `requestToken`, `tokenSecret`, and `authorizationUrl`
3. Log in through `authorizationUrl`, then get redirected back to the origin page with a `verifier` as URL query param (if there is no callback, `verifier` is displayed on the USOS page)
4. `POST` request to `/auth/access-token` endpoint with `requestToken`, `tokenSecret`, `verifier`, and `universityId` in JSON body
5. Request should return `jwt`
6. Include `Authorization` Header with value: `Bearer ${jwt}` when requesting data from `/getData`

## Postman configuration for easier debugging 🔧

You can test backend functionality with Postman using our collection, pre-configured with endpoints and environments. All you need to do is to import [this folder](/service/postman) into Postman.

## Acknowledgments

Special thanks to the contributors who made this project possible.

### Backend

- [@pszumanski](https://github.com/pszumanski)
- [@MDybek](https://github.com/MDybek)
- [@tommilewski](https://github.com/tommilewski)

### Frontend

- [@Kapixar](https://github.com/Kapixar)
