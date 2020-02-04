import React, { Component } from 'react';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import { makeStyles } from '@material-ui/core/styles';
import { useScrollTrigger} from '@material-ui/core';
import Container from '@material-ui/core/Container';
import MenuAppBar from './Components/MenuAppBar'


class User extends Component{

    constructor(props) {
        super();
        this.state={
            data: [],
            isLoaded: false,
        }
    }

    componentDidMount(){
        fetch('http://localhost:8080/api/users/all')
            .then(respons => respons.json())
            .then(data => {
                this.setState({
                    isLoaded: true,
                    data: data,
                })
            });
    }

    render(){
        var {isLoaded, data} = this.state;

        if(!isLoaded){
            return <div>Loading ...</div>
        }else {

        return(     
            <React.Fragment>
            
            <MenuAppBar/>

                    <h1>User data</h1>
                
                 
            <TableContainer component={Paper}>
                <Table size="small" aria-label="a dense table">
                    <TableHead>
                        <TableRow>
                            <TableCell >#</TableCell>
                            <TableCell >User Name</TableCell>
                            <TableCell >Email</TableCell>
                            <TableCell >Balance</TableCell>
                            <TableCell >Incomes</TableCell>
                            <TableCell >Expenses</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {this.state.data.map(user =>{
                            return(
                                <TableRow key={user.id}>
                                    <TableCell component="th" > {user.id}</TableCell>
                                    <TableCell>{user.username}</TableCell>
                                    <TableCell>{user.email}</TableCell>
                                    <TableCell>{user.budget.balance}</TableCell>
                                    <TableCell>
                                    Incomes
                                    (
                                    {user.budget.incomes.map((income) => income.amount).join(', ')}
                                    )
                                    </TableCell>
                                    <TableCell>
                                        Expense
                                        (
                                        {user.budget.expense.map((expense) => expense.amount).join(', ')}
                                        )
                                    </TableCell>
                                </TableRow>
                            )})}
                    </TableBody>
                </Table>
            </TableContainer>
            </React.Fragment>    
        )}}

}

export default User
