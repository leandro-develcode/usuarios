import React from 'react'

import Routes from './routes'

import './assets/reset.css';
import './assets/main.css';

import SiteHeader from './views/site_header'
import SiteFooter from './views/site_footer'

class App extends React.Component{
    render(){
        return(
            <div id="wrapper">
                <SiteHeader />
                <Routes />
                <SiteFooter />
            </div>
        )
    }
}

export default App
