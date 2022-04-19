import './signIn.scss';
import {useRef} from "react";
import {useDispatch} from "react-redux";
import {userSlice} from "../../slices/userSlice";
import jwtDecode from "jwt-decode";

export default function SignIn() {
    const userAction = userSlice.actions;
    const dispatch = useDispatch();
    const username = useRef();
    const password = useRef();

    function setUser(token) {
        var decoded = jwtDecode(token);
        console.log(decoded);
        dispatch(userAction.setSub(decoded.sub));
        dispatch(userAction.setRole(decoded.role));
        dispatch(userAction.setExp(decoded.exp));
        dispatch(userAction.setIat(decoded.iat));
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
            .then(({jwtToken}) => setUser(jwtToken));
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