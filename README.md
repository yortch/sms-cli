# SMS CLI 1.0

### Reference Documentation

## Setup
Run the following command to build:

`./gradlew clean build -xtest`

Ensure private key is

## Run
To launch CLI run:

`java -jar build/libs/sms-cli-1.0.0.jar`

## Available commands

* Help

`help`

* Account Balance
Gets account balance:

`balance --api-key <apiKey> --api-secret <apiSecret>`

* Messaging Setup
Setups require configuration to send SMS messages and private key path (assumes default value of ~/private.key)

`setup --application-id <guid>`
`setup --application-id <guid> --private-key <path>`

* Messaging Send SMS
Sends text SMS message (emojis are supported)

`sms --to <phone> --from <phone> --text <text message>`

