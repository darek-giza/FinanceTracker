import React, { Component } from 'react';
import './App.css';
import {BrowserRouter as Router,Route,Switch} from 'react-router-dom'
import User from './User'
import Home from './Home'
import Incomes from './Incomes'
import Expenses from './Expenses'

class App extends Component {
  state ={}
    render() {
      return(
      <Router>
        <Switch>
          <Route path='/' exact={true} component={Home}></Route>
          <Route path='/user' exact={true} component={User}></Route>
          <Route path='/incomes' exact={true} component={Incomes}></Route>
          <Route path='/expenses' exact={true} component={Expenses}></Route>
        </Switch>
      </Router>
      );
    }
}
export default App;
