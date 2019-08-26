import React, {useState} from 'react';
import {axiosLogin as axios} from './helpers/axiosConfig';

const App = () => {
  const [loginValues, setLoginValues] = useState({
    username: '',
    password: '',
  });

  function handleChange(e) {
    setLoginValues({ ...loginValues, [e.target.name]: e.target.value });
  }

  function handleSubmit(e) {
    e.preventDefault();
    axios().post('/login', `grant_type=password&username=${loginValues.username}&password=${loginValues.password}`)
         .then(res => console.log(res.data))
         .catch(err => console.log(err.response));
  }

  return (
      <form onSubmit={handleSubmit}>
        <input value={loginValues.username} name='username' onChange={handleChange} />
        <input value={loginValues.password} name='password' onChange={handleChange} type='password' />
        <button type='submit'>Login</button>
      </form>
  )
};

export default App;