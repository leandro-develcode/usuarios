import React from 'react'
import { withRouter } from "react-router";

import Swal from 'sweetalert2'
import Inputmask from "inputmask";
import axios from 'axios'

class UserForm extends React.Component{

    state = {
        update: false,
        item: null
    }

    componentDidMount(){
        const params = this.props.match.params
        if(params.id){
            this.setState({update: true})
            axios.get(window.server_url + 'item?id='+params.id).then(response => {
                if(response.data.status){
                    this.setState({item: response.data.user})
                } else{
                    Swal.fire({
                      title: response.data.message,
                      icon: 'error',
                    })
                    this.setState({update: false})
                }
            }).catch(error => {
                console.error(error.response)
            })
        }

        var input = document.getElementById("birthday");
        var im = new Inputmask("99/99/9999");
        im.mask(input);
    }

    sendForm(e){
        e.preventDefault()

        const form = document.querySelector('.form')

        let formData = new FormData(form);

        let url = window.server_url + 'insert'

        if(this.state.update)
            url = window.server_url + 'update'

        axios.post(url, formData).then(response =>{
            Swal.fire({
              title: response.data.message,
              icon: response.data.status ? 'success' : 'error',
            })
            if(response.data.status){
                this.props.history.push('/')
            }
        }).catch(error => {
            console.error(error.response)
        })
    }

    render(){

        return(
            <section id="form">
                <h1 className="title">{!this.state.update ? 'Novo' : 'Editar'} Usuário</h1>
                <form className="form" onSubmit={(e) => this.sendForm(e)}>
                    <input type="hidden" name="id" defaultValue={this.state.item ? this.state.item.id : ''}/>
                    <label className="column">
                        <span className="name">Nome:</span>
                        <input type="text" name="name" required defaultValue={this.state.item ? this.state.item.name : ''}/>
                    </label>
                    <label className="column">
                        <span className="name">Data de Nascimento:</span>
                        <input type="text" name="birthday" id="birthday" required defaultValue={this.state.item ? this.state.item.dateFormatted : ''}/>
                    </label>
                    <label className="column column-image">
                        <span className="name">{!this.state.update ? '' : 'Alterar '}Imagem:</span>
                        <input type="file" name="image"/>
                        <input type="hidden" name="old_image" defaultValue={this.state.item ? this.state.item.image : ''}/>
                    </label>
                    <div className="buttons">
                        <button className="btn">Salvar</button>
                        <a className="btn back" href="#/">Voltar</a>
                    </div>
                </form>
            </section>
        )
    }
}

export default withRouter(UserForm)