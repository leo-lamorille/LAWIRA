import './admin.scss';
import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {useEffect, useState} from "react";
import {
  CircularProgress,
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

export default function Admin() {
  const userAction = userSlice.actions;
  const dispatch = useDispatch();
  const [cookies, setCookie, removeCookie] = useCookies(['token']);

  const navigate = useNavigate();
  const userToken = useSelector(state => state.user.jwt);
  const userName = useSelector(state => state.user.name);
  const userRole = useSelector(state => state.user.role);
  const [pendingCommands, setPendingCommands] = useState();
  const [attributes, setAttributes] = useState();
  const [accounts, setAccounts] = useState();

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
    .then(res => {
      console.log(res);
      return res;
    })
    .then(res => setAccounts(res))
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
  }, []);

  return <div className="admin__page">
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
    <h1>Statistiques</h1>
    <div className="stats">
      <Chart jwt={userToken}/>
    </div>
  </div>
}
