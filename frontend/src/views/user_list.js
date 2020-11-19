import React from 'react'

import axios from 'axios'

import Swal from 'sweetalert2'

import edit from '../assets/edit.svg';
import remove from '../assets/trash.svg';

class UserList extends React.Component{

    state = {
        itens: []
    }

    constructor(props){
        super(props)

        axios.get(window.server_url + 'list').then(response => {
            this.setState({itens: response.data.itens})
        }).catch(error => {
            console.error(error.response)
        })

    }

    remove(id){

        Swal.fire({
            title: 'Deseja exluir o usuário?',
            text: '',
            type: 'warning',
            showCancelButton: true,
            cancelButtonText: 'Cancelar',
            confirmButtonText: 'Sim'
        }).then((result) => {
            if (result.value) {
                axios.get(window.server_url + 'delete?id='+id).then(response => {
                    Swal.fire({
                      title: response.data.message,
                      icon: response.data.status ? 'success' : 'error',
                    }).then((result) =>{
                        if(response.data.status){
                            window.location.reload()
                        }
                    })
                }).catch(error => {
                    console.error(error.response)
                })
            }
        });
    }

    render(){
        var itens = this.state.itens.map((item) =>
            <div className="item">
                <span className="id">{item.id}</span>
                <span className="image"><img src={window.server_url + 'uploads/' + item.image} /></span>
                <span className="name">{item.name}</span>
                <span className="birthday">{item.dateFormatted}</span>
                <span className="actions">
                    <a href={'#/formulario/' + item.id} className="btn edit"><img src={edit} /></a>
                    <button className="btn remove" onClick={() => this.remove(item.id)}><img src={remove} /></button>
                </span>
            </div>
        )

        if(this.state.itens.length == 0){
            itens =
                <div className="no-results">Nenhum usuário adicionado.</div>
        }

        return(
            <section id="list">
                <h1 className="title">Usuários</h1>
                <a href="#/formulario" className="add">Adicionar</a>
                <div className="itens">
                    {itens}
                </div>
            </section>
        )
    }
}

export default UserList