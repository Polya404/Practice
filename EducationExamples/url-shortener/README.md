# URL Shortener with Base62 Encoding
A simple and efficient URL shortener service that converts long URLs into shorter, more manageable links using Base62 encoding. This approach ensures a compact representation of URLs, making them ideal for sharing on platforms with character limits.

## Features
- Base62 Encoding: Converts numeric IDs into a short string using the characters 0-9, a-z, and A-Z.

- Customizable Short URLs: Optionally allows users to specify their own short URL aliases like http scheme, host, etc.

- Persistent Storage: Stores mappings between short and long URLs using an in-memory data structure or a persistent database.
