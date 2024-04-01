
## API Reference

#### Create short url

```http
  POST /v1/url 
```

| Body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `{"url": "YOUR LONG URL"}` | `json` | **Required**. |

*Returns short url. Ex: http://localhost:4500/v1/url/1904959641*


#### Redirect to long url (to paste in navegator)

```http
  GET /v1/url/:urlKey
```
**This endpoint generate a statistic for short url usage.*

| Path variable | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `urlKey` | `string` | **Required**. |

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
