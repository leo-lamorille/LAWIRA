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

  useEffect(() => {
    if (userToken === '') {
      navigate('/account/signIn');
    } else {

      let headers = new Headers()
      headers.append("Authorization", 'Bearer ' + userToken)
      fetch('http://localhost:8080/user/commands?status=CREATED', {
        headers: headers
      })
      .then(res => res.json())
      .then(res => setCreatedCommands(res))
      .catch(err => console.error(err));

      fetch('http://localhost:8080/user/commands?status=PENDING', {
        headers: headers
      })
      .then(res => res.json())
      .then(res => setPendingCommands(res))
      .catch(err => console.error(err));

      fetch('http://localhost:8080/user/commands?status=DONE', {
        headers: headers
      })
      .then(res => res.json())
      .then(res => setDoneCommands(res))
      .catch(err => console.error(err));
    }
  }, []);

  function computeProductUrlByValues(values, commandId, quantity) {
    let valuesString = ''
    values.forEach(({attributeId, optionId}) => {
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
            : createdCommands.map(({id, quantity, values, status}) => {
              return <Link to={computeProductUrlByValues(values, id, quantity)} key={id}>
                <div className={"command"}>
                  <span>Quantité: {quantity}</span>
                  <div className={"values"}>
                    {values.map(({
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
              </Link>
            })
      }
    </div>
    <div className="section">
      <h1>Commandes en cours</h1>
      {
        pendingCommands === undefined ? <CircularProgress/>
            : pendingCommands.map(({id, quantity, values, status}) => {
              return <div className={"command"} key={id}>
                <span>Quantité: {quantity}</span>
                <div className={"values"}>
                  {values.map(({
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
            : doneCommands.map(({id, quantity, values, status}) => {
              return <div className={"command"} key={id}>
                <span>Quantité: {quantity}</span>
                <div className={"values"}>
                  {values.map(({
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
