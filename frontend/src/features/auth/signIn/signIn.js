import '../sign.scss';

import {useRef, useState} from "react";
import {useDispatch} from "react-redux";
import {userSlice} from "../../slices/userSlice";
import jwtDecode from "jwt-decode";
import {useNavigate} from "react-router-dom";

export default function SignIn() {
    const userAction = userSlice.actions;
    const dispatch = useDispatch();

    const [errorLogin, setErrorLogin] = useState('');

    const navigate = useNavigate();
    const username = useRef();
    const password = useRef();

    function setUser(token) {
        const decoded = jwtDecode(token);
        dispatch(userAction.setSub(decoded.sub));
        dispatch(userAction.setRole(decoded.role));
        dispatch(userAction.setExp(decoded.exp));
        dispatch(userAction.setIat(decoded.iat));
        dispatch(userAction.setJwtToken(token));
    }

    function connection() {
        const body = JSON.stringify({
            username: username.current.value,
            password: password.current.value
        });
        const headers = new Headers();
        headers.append('Content-Type', 'application/json')

        fetch(`http://localhost:8080/public/login`, { method: 'POST', body, headers })
            .then(res => res.json())
            .then(({jwtToken}) => {
                setUser(jwtToken);
                navigate('/account');
            }).catch(error => {
                setErrorLogin('Utilisateur ou mot de passe incorrect');
        });
    }

    function signUp() {
        navigate('/account/signUp');
    }

    return (
      <div className="signContainer">
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
            {
                <p className="error">
                    {errorLogin}
                </p>
            }
            <div className="buttonContainer">
                <button className="buttonSign" onClick={connection}>Connexion</button>
                <button className="buttonSign" onClick={signUp}>Sign up</button>
            </div>
        </div>
      </div>
    );
}