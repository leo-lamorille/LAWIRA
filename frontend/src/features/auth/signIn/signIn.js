import './signIn.scss';
import {useRef, useState} from "react";

export default function SignIn() {
    const [jwt, setJwt] = useState('');
    const username = useRef();
    const password = useRef();

    function connection() {
        const body = JSON.stringify({
            username: username.current.value,
            password: password.current.value
        });

        fetch(`http://localhost:8080/public/login`, { method: 'POST', body })
            .then(res => res.json())
            .then(token => setJwt(token));
        console.log(jwt);
    }

    return (
      <div className="signInContainer">
        <div className="modal">
            <p className="title">Sign In</p>
            <div className="inputContainer">
                <p className="secondTitle">Nom d'utilisateur</p>
                <input className="inputSign" ref={username}/>
            </div>
            <div className="inputContainer">
                <p className="secondTitle">Mot de passe</p>
                <input type="password" className="inputSign" ref={password}/>
            </div>
            <div className="buttonContainer">
                <button className="buttonSign" onClick={connection}>Connexion</button>
                <button className="buttonSign">Sign up</button>
            </div>
        </div>
      </div>
    );
}