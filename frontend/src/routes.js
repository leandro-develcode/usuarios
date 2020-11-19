import React from 'react'
import {Route, Switch, HashRouter} from 'react-router-dom'

import UserForm from './views/user_form';
import UserList from './views/user_list';

function Routes(){
    return (
        <HashRouter>
            <Switch>
                <Route path="/formulario/:id" component={UserForm} />
                <Route path="/formulario" component={UserForm} />
                <Route path="/lista" component={UserList} />
                <Route path="/" component={UserList} />
            </Switch>
        </HashRouter>
    )
}

export default Routes