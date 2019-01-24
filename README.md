# Overview

This is a coding challenge.  To complete the challenge, submit a pull request from a 
fork of this repository which satisfies all open issues.

# Usage

Once you have cloned the repository, simply run `./gradlew run` to start the web service.
The service is exposed at http://localhost:4567.

## Logging in

The default username is `admin`, with the password `admin`.  To log in, post a JSON doc
with the following shape to the `/login/` route.

```
{
  "userName": "someUser",
  "password": "somePassword"
}
```

The server will respond with a 200 status code and a 64-character authorization token.
If the server responds with a non-200 status code, your login credentials are either
invalid or malformed.

## Listing current messages

Messages are contained in threads.  To receive a list of the current thread titles and
IDs, perform a GET against the path `/threads/`.  The server will respond with a JSON
array of objects containing the title and ID of all current threads, or an empty array if
there are no current threads.

To list all messages in a thread, perform a GET against the path `/threads/:threadId`,
replacing **threadId** with a valid thread.  E.g., `http://localhost:4567/threads/5`.

## Posting a new message

To create a new message, POST a JSON document to the path `/threads/`.

```{
  "threadId": 6,
  "text":  "This is my message"
}```

Note that `threadId` may be omitted, in which case a new thread will be created.

### Authorization

You must be authorized to post a new message.  Include your 64-character auth token in
the request header `X-WEX-AuthToken`.  Omitting this header or supplying an invalid auth
token will result in a 403 status.
