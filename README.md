
## API Reference

#### Create short url

```http
  POST /v1/url 
```

| Body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `{"url": "YOUR LONG URL"}` | `json` | **Required**. |

*Returns short url. Ex: http://localhost:4500/v1/url/1904959641*


#### Redirect to long url (use the url returned in create endpoint)

```http
  GET /v1/url/:urlKey
```
**This endpoint generate a statistic for short url usage.*

| Path variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `urlKey` | `string` | **Required**. |

**If you just use postman, remember add User-Agent header. Example:*

| Header | value     |
| :-------- | :------- |
| `User-Agent` | `Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36` |

**This data is important to generate statistics.*

#### Get existing short url by long url

```http
  GET /v1/url/short?url=***
```

| Request param | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `url` | `string` | **Required**. |


#### Get existing long url by short url key
```http
  GET /v1/url/long?urlKey=***
```

| Request param | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `urlKey` | `string` | **Required**. |

#### Delete existing short url
```http
  DELETE /v1/url/:urlKey
```
| Path variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `urlKey` | `string` | **Required**. |

#### Get list of statistics from specific short url
```http
  GET /v1/statistics/:urlKey
```

| Path variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `urlKey` | `string` | **Required**. |
