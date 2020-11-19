import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';


window.server_url = 'http://localhost:8080/api/';

ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('root')
);