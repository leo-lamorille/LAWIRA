import './basket.scss';
import {Link, useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {useEffect, useState} from "react";
import {Alert, CircularProgress} from "@mui/material";
import SectionBasket from "./sectionBasket/sectionBasket";
import {userSlice} from "../../features/slices/userSlice";
import {useCookies} from "react-cookie";

export default function Basket() {
  const userAction = userSlice.actions;
  const dispatch = useDispatch();
  const [cookies, setCookie, removeCookie] = useCookies(['token']);
  const navigate = useNavigate();
  const userToken = useSelector(state => state.user.jwt);
  const [createdCommands, setCreatedCommands] = useState();
  const [pendingCommands, setPendingCommands] = useState();
  const [doneCommands, setDoneCommands] = useState();
  const headers = new Headers()
  headers.append("Authorization", 'Bearer ' + userToken)

  const logout = () => {
    dispatch(userAction.logout(undefined));
    removeCookie('token', {path: '/'});
    navigate('/home');
  }

  function refreshCreated() {
    fetch('http://localhost:8080/user/commands?status=CREATED', {
      headers: headers
    })
    .then(res => {
      if (res.status === 403) {
        logout();
        throw "You are not authorized to do this action"
      } else if (res.status === 200) {
        return res.json();
      }
    })
    .then(res => setCreatedCommands(res))
    .catch(err => console.error(err));
    console.log(createdCommands);
  }

  function refreshPending() {
    fetch('http://localhost:8080/user/commands?status=PENDING', {
      headers: headers
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
    .catch(err => console.error(err));

  }

  function refreshDone() {
    fetch('http://localhost:8080/user/commands?status=DONE', {
      headers: headers
    })
    .then(res => {
      if (res.status === 403) {
        logout();
        throw "You are not authorized to do this action"
      } else if (res.status === 200) {
        return res.json();
      }
    })
    .then(res => setDoneCommands(res))
    .catch(err => console.error(err));
  }

  function buy(commandId) {
    console.log('here');
    fetch('http://localhost:8080/user/commands/' + commandId + '/buy', {
      method: 'PUT', headers
    }).then((res) => {
      if (res.status === 403) {
        logout();
        throw "You are not authorized to do this action"
      } else if (res.status === 200) {
        refreshCreated();
        refreshPending();
      }

    })
  }

  useEffect(() => {
    if (userToken === '') {
      navigate('/account/signIn');
    } else {
      refreshCreated();
      refreshPending();
      refreshDone();
    }
  }, []);

  function computeProductUrlByValues(options, commandId, quantity) {
    let valuesString = ''
    options.forEach(({attributeId, optionId}) => {
      valuesString += '&' + attributeId + "=" + optionId
    })
    return '/product?commandId=' + commandId + '&quantity=' + quantity
        + valuesString
  }

  return (
      <div className="basket__page">
        <h1>MON PANIER</h1>
        <div className="section">
          <h2>Commandes non payées</h2>
          <div className="clickable">
          </div>
          {
            createdCommands === undefined ? <CircularProgress/>
                : createdCommands.length === 0 && <Alert severity="info">Aucune
                  commande en attente de paiement</Alert> ||
                createdCommands.map(({id, quantity, options, status}) => {
                  return (
                      <SectionBasket
                          link={computeProductUrlByValues(options, id,
                              quantity)}
                          commandId={id} options={options} quantity={quantity}
                          buy={buy} isBuy={false}/>
                  );
                })
          }
        </div>
        <div className="section">
          <h2>Commandes en cours</h2>
          {
            pendingCommands === undefined ? <CircularProgress/>
                : pendingCommands.length === 0 && <Alert severity="info">Aucune
                  commande en cours</Alert> ||
                pendingCommands.map(({id, quantity, options, status}) => {
                  return <SectionBasket options={options} quantity={quantity}
                                        isBuy={true}/>
                })
          }
        </div>
        <div className="section">
          <h2>Commandes livrées</h2>
          {
            doneCommands === undefined ? <CircularProgress/>
                : doneCommands.length === 0 && <Alert severity="info">Aucune commande livrée</Alert> ||
                doneCommands.map(({id, quantity, options, status}) => {
                  return (
                      <SectionBasket options={options} quantity={quantity}
                                     isBuy={true}/>
                  );
                })
          }
        </div>
      </div>
  );
}
