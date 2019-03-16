# docsbot

A Discord bot to pull up documentation.

Powered by [KUtils](https://gitlab.com/Aberrantfox/KUtils)

## Running

1. The project uses maven for build management. Please see [here](https://maven.apache.org/guides/getting-started/index.html#How_do_I_compile_my_application_sources) or refer to your IDE documentation on using maven projects.
2. Run once to generate an configuration file.
3. Optionally edit the configuration
4. Run again, but this time passing the token as the only runtime argument.

Currently, only JavaScript docs are supported.
You can pull up js docs by doing `{prefix}js {entity}`
