import React from 'react'

import logo from '../assets/logo.jpg';

function SiteHeader(){
    return(
        <header id="header">
            <a className="logo" href="#/">
                <img src={logo} />
            </a>
        </header>
    )
}

export default SiteHeader