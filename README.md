# Simple Demo of using graphql in Kotlin 

Uses Expedia's [graphql library](https://github.com/ExpediaGroup/graphql-kotlin) and is based on their spring example. I've also copied over their examples.

You can use their playground thingy or play with the kotlin-js ui that I gobbled together.

All of this was gobbled together in about 6 hours. It's not very good code but it works. The main purpose for this was demoing this in a @codersociety meetup.

- it demoes how you can do graphql stuff in kotlin
- my elasticsearch kotlin library makes a cameo
- and since I wanted some simple UI I got side tracked trying to make Kotlin-js do this job. This mainly shows off my lack of effort and how relatively easy this is with the new gradle plugin.

## Backend

Official example plus a quick hacked together demo of my elasticsearch client for kotlin. It's used here to index and query a simple data object via graphql.

```
cd backend
gradle bootRun
```

It will start on port 8081 and you should be able to open the playground at http://localhost:8081/playground

After that starts you can play with the examples or try to use my Elasticsearch demo:

Add a coder:

```
mutation {
  index(coder: {name: "jilles", level: grand_master})
}
```

Query for coders:
```
{
  coders {
    name
    level
  }
}
```

Remove the coders:

```
mutation {
  clearCoders
}
```

For this to work, you obviously need Elasticsearch running as well. You can use my [docker-compose file](https://github.com/jillesvangurp/es-kotlin-wrapper-client/tree/master/es_kibana) for this or manually install & start it.

## ui

```
cd ui
gradle run --continuous
```

It will start and open a browser tab with the UI. On page load it will attempt to access the graphql endpoint.

## License

The copied examples are licensed under [the original license](https://github.com/ExpediaGroup/graphql-kotlin/blob/master/LICENSE). 
I made some minor changes to move them to the right package

The rest of the code is [MIT licensed](LICENSE)