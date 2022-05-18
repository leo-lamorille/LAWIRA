import './adminMessage.scss';
import {Link, useNavigate, useParams} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {useEffect, useState} from "react";
import {CircularProgress} from "@mui/material";
import {userSlice} from "../../features/slices/userSlice";
import {useCookies} from "react-cookie";

export default function () {
  const {messageId} = useParams();
  const [message, setMessage] = useState()

  const userAction = userSlice.actions;
  const dispatch = useDispatch();
  const [cookies, setCookie, removeCookie] = useCookies(['token']);
  const userToken = useSelector(state => state.user.jwt);
  const userRole = useSelector(state => state.user.role);
  const navigate = useNavigate();

  const logout = () => {
    dispatch(userAction.logout(undefined));
    removeCookie('token', {path: '/'});
    navigate('/home');
  }

  function refreshMessage() {
    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken)
    fetch(`http://localhost:8080/admin/contact/${messageId}`, {
      headers, method: 'GET'
    }).then(res => {
      if (res.status === 403) {
        logout();
        throw "You are not authorized to do this action"
      } else if (res.status === 404) {
        navigate('/admin')
      } else if (res.status === 200) {
        return res.json();
      }
    }).then(msg => setMessage(msg));
  }

  function deleteMessage() {
    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken)
    fetch(`http://localhost:8080/admin/contact/${messageId}`, {
      headers, method: 'DELETE'
    }).then(res => {
      if (res.status === 403) {
        logout();
        throw "You are not authorized to do this action"
      }else if (res.status === 200) {
        navigate('/admin');
      }
    });
  }

  useEffect(() => {
    if (userToken === '') {
      navigate('/account/signIn');
    } else if (userRole !== 'ADMIN') {
      navigate('/home');
    }
    refreshMessage()
  }, [])
  if (message === undefined) {
    return <div>
      <CircularProgress/>
    </div>
  }
  const {
    firstname,
    lastname,
    email,
    subject,
    content,
    status,
    account,
    sentAt
  } = message
  return <div className="adminMessagePage">
    <div className="sentAt">Envoyé le: {sentAt}</div>
    <h1>{subject}</h1>
    <h2>{firstname} {lastname} {(account !== undefined && account !== null)
        && `(Connecté en tant que: ${account})`}</h2>
    <h3>{email}</h3>
    <p>
      {content}
    </p>
    <div className="buttons">
      <a href={`mailto:${email}?subject=${subject} [réponse]`}>
        <button className="btn styledButton">Répondre par mail</button>
      </a>
      <button className="btn styledButton" type="button"
              onClick={deleteMessage}>Supprimer
      </button>
    </div>
  </div>
}
