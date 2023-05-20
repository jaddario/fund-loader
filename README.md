# Fund Loader Application

Fund Loader is an application to manage attempts to load funds on customer accounts in real time. 
This project was created with the intention of studying and practicing creating API's in Java with Spring Boot. 

# Table of contents
1. [Installation](#installation)
2. [Requirements](#requirements)
3. [Problem](#problem)
    1. [Sub paragraph](#subparagraph1)
4. [Another paragraph](#paragraph2)

## Requirements <a name="requirements"></a>

- Docker
- Java 17

## Installation <a name="installation"></a>

After downloading the application from [GitHub](https://github.com/jaddario/fund-loader), navigate to the root folder and run it through [docker compose](https://docs.docker.com/compose/) comand.

```bash
docker-compose up
```
## Problem <a name="problem"></a>

In finance, it's common for accounts to have so-called "velocity limits". In this task, you'll
create a Java Spring boot application that accepts or declines attempts to load funds
into customers' accounts in real-time.
Each attempt to load funds will come as a single-line JSON payload, structured as
follows:

```json
{
  "id": "1234",
  "customer_id": "1234",
  "load_amount": "$123.45",
  "time": "2018-01-01T00:00:00Z"
}
```
Each customer is subject to three limits:
- A maximum of $5,000 can be loaded per day
- A maximum of $20,000 can be loaded per week
- A maximum of 3 loads can be performed per day, regardless of amount

As such, a user attempting to load $3,000 twice in one day would be declined on the
second attempt, as would a user attempting to load $400 four times in a day.
For each load attempt, you should return a JSON response indicating whether the fund
load was accepted based on the user's activity, with the structure:
```json
{
  "id": "1234",
  "customer_id": "1234",
  "accepted": true
}
```
You can assume that the input arrives in ascending chronological order and that if a
load ID is observed more than once for a particular user, all but the first instance can be
ignored (i.e. no response given). Each day is considered to end at midnight UTC, and
weeks start on Monday (i.e. one second after 23:59:59 on Sunday).

Your program should process lines from input.txt and return output in the format
specified above, either to standard output or a file. The expected output given our input
data can be found in output.txt.

## Usage

Once ths **fund-loader** application is running, the collection indicated in the folder structure 
below can be used to perform functional tests. 

```
├── src
│   ├── main
│   │   ├── java
│   │   ├── resources
│   │   │   ├── collections      
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License

[MIT](https://choosealicense.com/licenses/mit/)