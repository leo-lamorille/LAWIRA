import '../sign.scss';

import {useRef, useState} from "react";
import {useDispatch} from "react-redux";
import {userSlice} from "../../slices/userSlice";
import jwtDecode from "jwt-decode";
import {useNavigate} from "react-router-dom";
import {useCookies} from "react-cookie";

export default function SignIn() {
    const userAction = userSlice.actions;
    const dispatch = useDispatch();

    const [cookies, setCookie] = useCookies(['token']);
    const [errorLogin, setErrorLogin] = useState('');

    const navigate = useNavigate();
    const username = useRef();
    const password = useRef();

    function setUser(token) {
        const decoded = jwtDecode(token);
        dispatch(userAction.setName(decoded.sub));
        dispatch(userAction.setRole(decoded.role));
        dispatch(userAction.setExp(decoded.exp));
        dispatch(userAction.setIat(decoded.iat));
        dispatch(userAction.setJwtToken(token));
        const date = new Date(decoded.exp * 1000);
        setCookie('token', token, {path: '/', expires: date});
    }

    function connection(e) {
        e.preventDefault();
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
                navigate('/home');
            }).catch(error => {
                setErrorLogin('Utilisateur ou mot de passe incorrect');
        });
    }

    function signUp() {
        navigate('/account/signUp');
    }

    const style = {
        padding: '1%',
        border: '1px solid #ccc',
        borderRadius: '4px',
        boxSizing: 'border-box',
        fontFamily: 'montserrat, Comic sans MS',
        color: '#2C3E50',
        fontSize: '1em',
        width: '100%'
    }

    return (
      <div className="signContainer">
        <div className="modal">
            <p className="title">Sign In</p>
            <form onSubmit={connection}>
                <div className="inputContainer">
                    <p className="secondTitle">Nom d'utilisateur</p>
                    <input className="inputSign" ref={username} style={style}/>
                </div>
                <div className="inputContainer">
                    <p className="secondTitle">Mot de passe</p>
                    <input type="password" className="inputSign" ref={password} style={style}/>
                </div>
                {
                    <p className="error">
                        {errorLogin}
                    </p>
                }
                <div className="buttonContainer">
                    <button className="buttonSign" type='submit'>Connexion</button>
                    <button className="buttonSign" onClick={signUp}>Sign up</button>
                </div>
            </form>
        </div>
      </div>
    );
}
