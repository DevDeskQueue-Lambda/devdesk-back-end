# Welcome to the DevDesk Queue Build Week BackEnd

## Documentation can be found at the following URL:

[Swagger Documentation for DevDesk](https://lambda-devdesk.herokuapp.com/swagger-ui.html)

### How to get your Access Token with React

#### Axios Config File for No Authorization

```js
import axios from 'axios';

export const axiosLogin = () => {
  return axios.create({
    baseURL: "https://lambda-devdesk.herokuapp.com",
    headers: {
      "Authorization":`Basic ${btoa('devdesk-client:devdesk-secret')}`,
      "Content-Type" : "application/x-www-form-urlencoded"
    }
  })
}
```

#### Axios Request for Login Endpoint
```js
import {axiosLogin as axios} from './path/to/axiosconfigfile'

axios().post('/login', `grant_type=password&username=${loginValues.username}&password=${loginValues.password}`)
         .then(res => console.log(res.data))
         .catch(err => console.log(err.response));
```

#### Here is the Response on Successful Login
```js
access_token: "fcf8511a-7ac8-434d-a7af-3dbc27adce28"
expires_in: 2876
scope: "read write trust"
token_type: "bearer"
```

You will want to store the access_token to localStorage.

#### Here is your Axios with Auth configuration
```js
export const axiosWithAuth = () => {
  return axios.create({
    baseURL: "https://lambda-devdesk.herokuapp.com",
    headers: {
      "Authorization":`Bearer ${localStorage.getItem('token')}`
    }
  })
}
```