import './admin.scss';
import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {useEffect, useState} from "react";
import {
  Alert,
  CircularProgress, FormControlLabel, Input,
} from "@mui/material";
import CRUDAttribute from "../../features/crudAttribute/CRUDAttribute";
import CreateAttributeForm
  from "../../features/form/createAttribute/createAttributeForm";
import Chart from "../../features/chart/Chart";
import AdminSectionBasket from "./adminSectionBasket/adminSectionBasket";
import {userSlice} from "../../features/slices/userSlice";
import {useCookies} from "react-cookie";
import AdminSectionAccount from "./adminSectionAccount/adminSectionAccount";
import CreateAdministratorForm
  from "../../features/form/createAdministrator/createAdministratorForm";
import AdminSectionMessage from "./adminSectionMessage/adminSectionMessage";
import {CheckBox} from "@material-ui/icons";
import Snackbar from "@mui/material/Snackbar";

export default function Admin() {
  const userAction = userSlice.actions;
  const dispatch = useDispatch();
  const [cookies, setCookie, removeCookie] = useCookies(['token']);

  const [showAlert, setShowAlert] = useState(false);
  const [alertMessage, setAlertMessage] = useState("");

  const navigate = useNavigate();
  const userToken = useSelector(state => state.user.jwt);
  const userName = useSelector(state => state.user.name);
  const userRole = useSelector(state => state.user.role);
  const [pendingCommands, setPendingCommands] = useState();
  const [attributes, setAttributes] = useState();
  const [accounts, setAccounts] = useState();
  const [messages, setMessages] = useState();
  const [showSeenMessages, setShowSeenMessages] = useState(false);

  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setShowAlert(false);
  };

  const logout = () => {
    dispatch(userAction.logout(undefined));
    removeCookie('token', {path: '/'});
    navigate('/home');
  }

  function refreshPendingCommands() {
    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken)
    fetch('http://localhost:8080/admin/commands', {
      headers
    })
    .then(res => {
      if (res.status === 403) {
        logout();
        throw "You are not authorized to do this action"
      } else if (res.status === 200) {
        return res.json();
      }
    })
    .then(res => setPendingCommands(res))
  }

  function refreshAttributes() {
    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken)
    fetch('http://localhost:8080/admin/attributes', {
      headers
    })
    .then(res => {
      if (res.status === 403) {
        logout();
        throw "You are not authorized to do this action"
      } else if (res.status === 200) {
        return res.json();
      }
    })
    .then(res => setAttributes(res))
  }

  function validateCommand(id) {
    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken)
    fetch('http://localhost:8080/admin/commands/' + id + '/validate', {
      headers, method: 'PUT'
    }).then(res => {
      if (res.status === 403) {
        logout();
        throw "You are not authorized to do this action"
      } else if (res.status === 200) {
        refreshPendingCommands();
        setShowAlert(true);
        setAlertMessage("Commande validée");
      } else {
        console.error(res.json());
      }
    })
  }

  function refreshAccounts() {
    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken)
    fetch('http://localhost:8080/admin/accounts', {
      headers
    })
    .then(res => {
      if (res.status === 403) {
        logout();
        throw "You are not authorized to do this action"
      } else if (res.status === 200) {
        return res.json();
      }
    })
    .then(res => setAccounts(res))
  }

  function refreshMessages() {
    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken)
    fetch('http://localhost:8080/admin/contact', {
      headers
    })
    .then(res => {
      if (res.status === 403) {
        logout();
        throw "You are not authorized to do this action"
      } else if (res.status === 200) {
        return res.json();
      }
    })
    .then(res => setMessages(res))
  }

  useEffect(() => {
    if (userToken === '') {
      navigate('/account/signIn');
    } else if (userRole !== 'ADMIN') {
      navigate('/home');
    }
    refreshPendingCommands()
    refreshAttributes();
    refreshAccounts();
    refreshMessages();
  }, []);

  const nbSeenMessages = messages === undefined ? "-" : messages.filter(
      ({status}) => status === "SEEN").length;
  const nbNewMessages = messages === undefined ? "-" : messages.length
      - nbSeenMessages;
  return <div className="admin__page">
    <Snackbar open={showAlert} autoHideDuration={3000}
              onClose={handleClose}>
      <Alert severity="success" onClose={() => setShowAlert(false)}>
        {alertMessage}
      </Alert>
    </Snackbar>
    <h1>Commandes à traiter {pendingCommands
        && `(${pendingCommands.length})`}</h1>
    <div className="pendingCommands">
      {
          (pendingCommands && pendingCommands.map(
              ({id, username, quantity, options}) => <AdminSectionBasket id={id}
                                                                         username={username}
                                                                         quantity={quantity}
                                                                         options={options}
                                                                         validate={validateCommand}
                                                                         key={id}/>))
          || <CircularProgress/>
      }
    </div>

    <h1>Produit</h1>
    <div className="attributes">
      {
        (attributes && attributes.map(
                ({id, name, description, options}) => <CRUDAttribute key={id}
                                                                     id={id}
                                                                     name={name}
                                                                     description={description}
                                                                     options={options}
                                                                     setShowAlert={(message)=>{
                                                                       setShowAlert(true);
                                                                       setAlertMessage(message);
                                                                     }}
                                                                     refresh={refreshAttributes}/>)
            || <CircularProgress/>)
      }
      <CreateAttributeForm/>
    </div>
    <h1>Comptes {accounts && `(${accounts.length})`}</h1>
    <div className="accounts">
      {accounts && accounts.map(
              ({id, username, enabled, role, nbCommands, nbConfigurations}) =>
                  <AdminSectionAccount key={id}
                                       id={id}
                                       username={username}
                                       enabled={enabled}
                                       role={role}
                                       nbConfigurations={nbConfigurations}
                                       nbCommands={nbCommands}
                                       userToken={userToken}
                                       refresh={refreshAccounts}
                                       currentAccountUserName={userName}/>) ||
          <CircularProgress/>
      }
      <CreateAdministratorForm refresh={refreshAccounts}/>
    </div>
    <h1>Messages {messages && `(${nbNewMessages})`}</h1>
    <div className="showSeenMessages">
      <label htmlFor="showSeenMessages">Afficher {nbSeenMessages} messages
        lus:</label>
      <input name="showSeenMessages" type="checkbox" value={showSeenMessages}
             onChange={() => setShowSeenMessages(!showSeenMessages)}/>
    </div>
    <div className="messages">
      {
          messages && messages.filter(({status}) => {
            if (status === "NEW") {
              return true;
            }
            return showSeenMessages;
          }).map(({
            id,
            firstname,
            lastname,
            email,
            subject,
            content,
            status,
            account,
            sentAt
          }) => <AdminSectionMessage key={id} id={id} firstname={firstname}
                                     lastname={lastname} email={email}
                                     subject={subject} content={content}
                                     status={status} account={account}
                                     sentAt={sentAt}/>) ||
          <CircularProgress/>
      }
    </div>
    <h1>Statistiques</h1>
    <div className="stats">
      <Chart jwt={userToken}/>
    </div>
  </div>
}
