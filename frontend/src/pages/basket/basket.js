import './basket.scss';
import {Link, useNavigate} from "react-router-dom";
import {useSelector} from "react-redux";
import {useEffect, useState} from "react";
import {CircularProgress} from "@mui/material";

export default function Basket() {
  const navigate = useNavigate();
  const userToken = useSelector(state => state.user.jwt);
  const [createdCommands, setCreatedCommands] = useState();
  const [pendingCommands, setPendingCommands] = useState();
  const [doneCommands, setDoneCommands] = useState();
  const headers = new Headers()
  headers.append("Authorization", 'Bearer ' + userToken)

  function refreshCreated() {
    fetch('http://localhost:8080/user/commands?status=CREATED', {
      headers: headers
    })
    .then(res => res.json())
    .then(res => setCreatedCommands(res))
    .catch(err => console.error(err));
  }

  function refreshPending() {
    fetch('http://localhost:8080/user/commands?status=PENDING', {
      headers: headers
    })
    .then(res => res.json())
    .then(res => setPendingCommands(res))
    .catch(err => console.error(err));

  }

  function refreshDone() {
    fetch('http://localhost:8080/user/commands?status=DONE', {
      headers: headers
    })
    .then(res => res.json())
    .then(res => setDoneCommands(res))
    .catch(err => console.error(err));
  }

  function buy(commandId) {
    fetch('http://localhost:8080/user/commands/' + commandId + '/buy', {
      method: 'PUT', headers
    }).then(() => {
      refreshCreated();
      refreshPending();
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

  return <div className="basket__page">
    <div className="section">
      <h1>Commandes non payées</h1>
      {
        createdCommands === undefined ? <CircularProgress/>
            : createdCommands.map(({id, quantity, options, status}) => {
              return <div key={id} className={"command"}>
                <Link to={computeProductUrlByValues(options, id, quantity)}>Modifier
                  commande</Link>
                <span>Quantité: {quantity}</span>
                <div className={"values"}>
                  {options.map(({
                    attributeId,
                    attributeName,
                    optionId,
                    optionValue,
                    optionType
                  }) => {
                    return <div className={"value"}
                                key={attributeId}>{attributeName}:{optionValue}</div>
                  })}
                </div>
                <button onClick={() => {
                  buy(id);
                }}>Acheter
                </button>
              </div>
            })
      }
    </div>
    <div className="section">
      <h1>Commandes en cours</h1>
      {
        pendingCommands === undefined ? <CircularProgress/>
            : pendingCommands.map(({id, quantity, options, status}) => {
              return <div className={"command"} key={id}>
                <span>Quantité: {quantity}</span>
                <div className={"values"}>
                  {options.map(({
                    attributeId,
                    attributeName,
                    optionId,
                    optionValue,
                    optionType
                  }) => {
                    return <div className={"value"}
                                key={attributeId}>{attributeName}:{optionValue}</div>
                  })}
                </div>
              </div>

            })
      }
    </div>
    <div className="section">
      <h1>Commandes livrées</h1>
      {
        doneCommands === undefined ? <CircularProgress/>
            : doneCommands.map(({id, quantity, options, status}) => {
              return <div className={"command"} key={id}>
                <span>Quantité: {quantity}</span>
                <div className={"values"}>
                  {options.map(({
                    attributeId,
                    attributeName,
                    optionId,
                    optionValue,
                    optionType
                  }) => {
                    return <div className={"value"}
                                key={attributeId}>{attributeName}:{optionValue}</div>
                  })}
                </div>
              </div>
            })
      }
    </div>
  </div>
}
