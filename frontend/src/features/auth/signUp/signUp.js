import '../sign.scss';

import {useRef, useState} from "react";
import jwtDecode from "jwt-decode";
import {userSlice} from "../../slices/userSlice";
import {useDispatch} from "react-redux";
import {useNavigate} from "react-router-dom";

export default function SignUp() {
    const [errorMessagePass, setErrorMessagePass] = useState('');
    const [errorMessageUsername, setErrorMessageUsername] = useState('');
    const navigate = useNavigate();

    const username = useRef();
    const password = useRef();
    const passwordConfirm = useRef();

    const userAction = userSlice.actions;
    const dispatch = useDispatch();

    const setUser = (token) => {
        const decoded = jwtDecode(token);
        dispatch(userAction.setSub(decoded.sub));
        dispatch(userAction.setRole(decoded.role));
        dispatch(userAction.setExp(decoded.exp));
        dispatch(userAction.setIat(decoded.iat));
        dispatch(userAction.setJwtToken(token));
    }

    const checkPasswordBeforeSignUp = () => {
        if(!(password.current.value === passwordConfirm.current.value)) {
            setErrorMessagePass('Veuillez renseigner un mot de passe correct');
            return false;
        }
        setErrorMessagePass('')
        return true;
    }

    const checkIfUsernameBeforeSignUp = () => {
        if (username.current.value === '') {
            setErrorMessageUsername('Renseignez un nom d\'utilisateur');
            return false;
        }
        setErrorMessageUsername('');
        return true;
    }

    const checkIfPassClear = () => {
        if (password.current.value === '') {
            setErrorMessagePass('Renseignez un mot de passe');
            return false;
        }
        return true;
    }

    const connection = () => {
        const passCheck = checkPasswordBeforeSignUp()
        const usernameCheck = checkIfUsernameBeforeSignUp();
        const passClear = checkIfPassClear();
        if (usernameCheck && passCheck && passClear) {
            const body = JSON.stringify({
                username: username.current.value,
                password: password.current.value
            });
            const headers = new Headers();
            headers.append('Content-Type', 'application/json');

            fetch('http://localhost:8080/public/sign-up', {method: 'POST', headers, body})
                .then(res => res.json())
                .then(({jwtToken}) => {
                    setUser(jwtToken);
                    navigate('/account');
                });
        }
    }
    return (
        <div className="signContainer">
            <div className="modal">
                <p className="title">Sign Up</p>
                <div className="inputContainer">
                    <p className="secondTitle">Nom d'utilisateur</p>
                    <input className="inputSign" ref={username}/>
                </div>
                <p className="error">
                    {errorMessageUsername}
                </p>
                <div className="inputContainer">
                    <p className="secondTitle">Mot de passe</p>
                    <input type="password" className="inputSign" ref={password}/>
                </div>
                <div className="inputContainer">
                    <p className="secondTitle">Confirmer votre mot de passe</p>
                    <input type="password" className="inputSign" ref={passwordConfirm}/>
                </div>
                <p className="error">
                    {errorMessagePass}
                </p>
                <div className="buttonContainer">
                    <button className="buttonSign" onClick={connection}>Créer</button>
                </div>
            </div>
        </div>
    )
}